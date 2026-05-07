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
fun HomeStreak(
    navegante: NavHostController,
    userName: StreakUser,
    currentUser: CurrentUser?
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreakBG)
    ) {

        Column(
            modifier = Modifier
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


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
                .padding(10.dp)
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
                .padding(15.dp),
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {


        }

        Spacer(modifier = Modifier.weight(1f))

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
