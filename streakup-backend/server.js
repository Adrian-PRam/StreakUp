const express = require("express");
const cors = require("cors");
const db = require("./database");

const app = express();
const PORT = 3000;

app.use(cors());
app.use(express.json());

app.get("/health", (req, res) => {
  res.json({ status: "ok" });
});

app.post("/register", (req, res) => {
  const { username, email, password } = req.body;

  if (!username || !email || !password) {
    return res.status(400).json({ error: "username, email y password son requeridos" });
  }

  db.get("SELECT id FROM users WHERE email = ?", [email], (error, existingUser) => {
    if (error) return res.status(500).json({ error: "Error al buscar usuario" });
    if (existingUser) return res.status(400).json({ error: "El email ya esta registrado" });

    db.run(
      "INSERT INTO users (username, email, password) VALUES (?, ?, ?)",
      [username, email, password],
      function insertUser(insertError) {
        if (insertError) return res.status(500).json({ error: "Error al crear usuario" });

        res.status(201).json({
          message: "Cuenta creada",
          user: {
            id: this.lastID,
            username,
            email
          }
        });
      }
    );
  });
});

app.post("/login", (req, res) => {
  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({ error: "email y password son requeridos" });
  }

  db.get(
    "SELECT id, username, email, password FROM users WHERE email = ? AND password = ?",
    [email, password],
    (error, user) => {
      if (error) return res.status(500).json({ error: "Error al iniciar sesion" });
      if (!user) return res.status(401).json({ error: "Credenciales incorrectas" });

      res.json({
        message: "Login correcto",
        user
      });
    }
  );
});

app.get("/users/:userId/habits", (req, res) => {
  db.all(
    "SELECT id, user_id, name, description, completed_count FROM habits WHERE user_id = ?",
    [req.params.userId],
    (error, habits) => {
      if (error) return res.status(500).json({ error: "Error al obtener habitos" });
      res.json(habits);
    }
  );
});

app.post("/users/:userId/habits", (req, res) => {
  const { name, description } = req.body;
  const userId = req.params.userId;

  if (!name) {
    return res.status(400).json({ error: "name es requerido" });
  }

  db.run(
    "INSERT INTO habits (user_id, name, description) VALUES (?, ?, ?)",
    [userId, name, description || ""],
    function insertHabit(error) {
      if (error) return res.status(500).json({ error: "Error al crear habito" });

      res.status(201).json({
        id: this.lastID,
        user_id: Number(userId),
        name,
        description: description || "",
        completed_count: 0
      });
    }
  );
});

app.post("/users/:userId/habits/:habitId/complete", (req, res) => {
  const { userId, habitId } = req.params;

  db.get(
    "SELECT id FROM habits WHERE id = ? AND user_id = ?",
    [habitId, userId],
    (error, habit) => {
      if (error) return res.status(500).json({ error: "Error al buscar habito" });
      if (!habit) return res.status(404).json({ error: "Habito no encontrado para este usuario" });

      const completedAt = new Date().toISOString();
      db.run(
        "INSERT INTO habit_completions (user_id, habit_id, completed_at) VALUES (?, ?, ?)",
        [userId, habitId, completedAt],
        (insertError) => {
          if (insertError) return res.status(500).json({ error: "Error al completar habito" });

          db.run(
            "UPDATE habits SET completed_count = completed_count + 1 WHERE id = ? AND user_id = ?",
            [habitId, userId],
            (updateError) => {
              if (updateError) return res.status(500).json({ error: "Error al actualizar habito" });

              res.json({ message: "Habito completado" });
            }
          );
        }
      );
    }
  );
});

app.get("/users/:userId/profile", (req, res) => {
  const userId = req.params.userId;

  db.get(
    "SELECT username, email, password FROM users WHERE id = ?",
    [userId],
    (error, user) => {
      if (error) return res.status(500).json({ error: "Error al obtener perfil" });
      if (!user) return res.status(404).json({ error: "Usuario no encontrado" });

      db.get(
        `SELECT
          (SELECT COUNT(*) FROM habits WHERE user_id = ?) AS habits_created,
          (SELECT COUNT(*) FROM habit_completions WHERE user_id = ?) AS habits_completed`,
        [userId, userId],
        (countError, counts) => {
          if (countError) return res.status(500).json({ error: "Error al contar habitos" });

          res.json({
            username: user.username,
            email: user.email,
            password: user.password,
            habits_created: counts.habits_created,
            habits_completed: counts.habits_completed
          });
        }
      );
    }
  );
});

app.listen(PORT, () => {
  console.log(`StreakUP backend corriendo en http://localhost:${PORT}`);
});
