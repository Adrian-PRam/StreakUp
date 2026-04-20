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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun LoginStreak(navegante: NavHostController){
    var mail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(StreakBG), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(30.dp))
        Text("\uD83D\uDD25",
            fontSize = 50.sp)

        Text("StreakUp",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(7.dp))

        Text("Bienvenido de vuelta",
            fontSize = 15.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 7.dp, bottom = 20.dp))

        Column(modifier = Modifier.fillMaxWidth().height(300.dp).padding(5.dp).background(StreakBG), horizontalAlignment = Alignment.Start) {

            Text("Correo",
                fontSize = 17.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = mail,
                onValueChange = { mail = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email"
                    )
                },
                placeholder = {
                    Text("usuario@email.com")
                },
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = TextBoxColor,
                    unfocusedContainerColor = TextBoxColor,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                )
            )

            Text("Contraseña",
                fontSize = 17.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password"
                    )
                },
                placeholder = {
                    Text("*********")
                },
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = TextBoxColor,
                    unfocusedContainerColor = TextBoxColor,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Text("¿Olvidaste tu contraseña?",
                    fontSize = 17.sp,
                    color = TextPurple,
                    modifier = Modifier.padding(10.dp))
            }
        }
        Button(onClick = {
            navegante.navigate(StreakUser(userName = mail))
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = LoginPurple,
            contentColor = Color.White
        )) {
        Text(text = "Iniciar Sesion", modifier = Modifier.padding(4.dp))
    }

        Text("o continua con",
            fontSize = 18.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(12.dp))

        Row(modifier = Modifier.fillMaxWidth().height(100.dp).padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.clip(RoundedCornerShape(10.dp)).fillMaxWidth(0.45f).height(55.dp).background(TextBoxColor), verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.googlelogo),
                    contentDescription = "My image",
                    modifier = Modifier.size(78.dp).padding(4.dp)
                )

                Text("Google",
                    fontSize = 18.sp,
                    color = Color.LightGray)

            }
            Row(modifier = Modifier.clip(RoundedCornerShape(10.dp)).fillMaxWidth(0.9f).height(55.dp).background(TextBoxColor), verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.githublogo),
                    contentDescription = "My image",
                    modifier = Modifier.size(78.dp).padding(4.dp)
                )

                Text("Github",
                    fontSize = 18.sp,
                    color = Color.LightGray)

            }

        }

        Spacer(modifier = Modifier.size(15.dp))

        Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text("¿No tienes cuenta?",
                fontSize = 17.sp,
                color = Color.White,)

            Text("Crear cuenta",
                fontSize = 17.sp,
                color = TextPurple,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp).clickable(){
                    navegante.navigate(CreateAccountObject)
                })
        }


    }
}