package com.example.streakup.StreakUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.streakup.R
import com.example.streakup.ui.theme.LoginPurple
import com.example.streakup.ui.theme.StreakBG
import com.example.streakup.ui.theme.TextBoxColor
import com.example.streakup.ui.theme.TextPurple
import kotlinx.coroutines.launch


@Composable
fun HomeStreak(
    navegante: NavHostController,
    userName: StreakUser,
    currentUser: CurrentUser?
){
    var habits by remember { mutableStateOf<List<Habit>>(emptyList()) }
    var habitsMessage by remember { mutableStateOf("") }
    var isLoadingHabits by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun loadHabits() {
        val userId = currentUser?.id ?: return
        scope.launch {
            isLoadingHabits = true
            habitsMessage = ""
            runCatching {
                StreakApi.getHabits(userId)
            }.onSuccess { loadedHabits ->
                habits = loadedHabits
            }.onFailure { error ->
                habitsMessage = error.message ?: "No se pudieron cargar los hábitos"
            }
            isLoadingHabits = false
        }
    }

    LaunchedEffect(currentUser?.id) {
        loadHabits()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreakBG)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(15.dp)
        ) {

            Text(
                "Streak Up",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                "lunes, 16 de febrero",
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                "Nombre de usuario: ${currentUser?.username ?: userName.userName}",
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 4.dp)
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {



                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(TextBoxColor)
                        .padding(15.dp)
                        .fillMaxWidth(0.45f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("🎯", fontSize = 20.sp)
                    Text("Hoy", color = Color.LightGray, fontSize = 14.sp)
                    Text("3/4", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(TextBoxColor)
                        .padding(15.dp)
                        .fillMaxWidth(0.7f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("🏆", fontSize = 20.sp)
                    Text("Tasa", color = Color.LightGray, fontSize = 14.sp)
                    Text("75%", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(TextBoxColor)
                    .padding(12.dp)
            ) {

                Text(
                    "Modo Pomodoro",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1A0F0F))
                            .padding(20.dp)
                            .fillMaxWidth(0.45f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🔥", fontSize = 22.sp)
                        Text("Fuego", color = Color(0xFFFF7A00))
                    }

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF2A1626))
                            .padding(20.dp)
                            .fillMaxWidth(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🕯️", fontSize = 22.sp)
                        Text("Vela", color = Color(0xFFFF4DA6))
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF13202B))
                            .padding(20.dp)
                            .fillMaxWidth(0.45f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🧊", fontSize = 22.sp)
                        Text("Hielo", color = Color(0xFF4FC3F7))
                    }

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF2B1F12))
                            .padding(20.dp)
                            .fillMaxWidth(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("⏳", fontSize = 22.sp)
                        Text("Reloj", color = Color(0xFFFFB74D))
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "Hoy",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF1F1F2E))
                        .clickable { navegante.navigate(CreateHabitRoute) },
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", color = Color.White, fontSize = 18.sp)
                }
            }

            HomeHabitsList(
                habits = habits,
                message = habitsMessage,
                isLoading = isLoadingHabits,
                currentUser = currentUser,
                onComplete = { habit ->
                    val user = currentUser
                    if (user != null) {
                        scope.launch {
                            habitsMessage = ""
                            runCatching {
                                StreakApi.completeHabit(user.id, habit.id)
                            }.onSuccess {
                                loadHabits()
                            }.onFailure { error ->
                                habitsMessage = error.message ?: "No se pudo completar el hábito"
                            }
                        }
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(TextBoxColor),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🏠")
                Text("Inicio", color = TextPurple, fontSize = 12.sp)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { navegante.navigate(HabitsRoute) }
            ) {
                Text("📋")
                Text("Hábitos", color = Color.LightGray, fontSize = 12.sp)
            }



            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { navegante.navigate(ProfileRoute) }
            ) {
                Text("👤")
                Text("Perfil", color = Color.LightGray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun HomeHabitsList(
    habits: List<Habit>,
    message: String,
    isLoading: Boolean,
    currentUser: CurrentUser?,
    onComplete: (Habit) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        when {
            currentUser == null -> {
                Text(
                    "Inicia sesión para ver tus hábitos",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            message.isNotBlank() -> {
                Text(
                    message,
                    color = Color(0xFFFF8A80),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            isLoading -> {
                Text(
                    "Cargando...",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            habits.isEmpty() -> {
                Text(
                    "No hay hábitos todavía",
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            else -> {
                habits.forEach { habit ->
                    HomeHabitRow(
                        habit = habit,
                        onComplete = { onComplete(habit) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeHabitRow(habit: Habit, onComplete: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(TextBoxColor)
            .padding(14.dp)
    ) {
        Text(habit.name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        if (!habit.description.isNullOrBlank()) {
            Text(
                habit.description,
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            "Completado ${habit.completedCount} veces",
            color = Color.LightGray,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 6.dp)
        )
        Button(
            onClick = onComplete,
            colors = ButtonDefaults.buttonColors(containerColor = LoginPurple),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Text("Marcar completado")
        }
    }
}
