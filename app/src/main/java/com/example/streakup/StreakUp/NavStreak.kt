package com.example.streakup.StreakUp
import android.app.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object CreateAccountObject



@Serializable
data class StreakUser(
    val userName: String,
)


@Preview(showBackground = true)
@Composable

fun NavManager() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Home){

        composable<Home> {
            LoginStreak(navController)
        }

        composable<CreateAccountObject> {
            CreateAccount(navController)
        }

        composable<StreakUser> {
            val datosStreak: StreakUser = it.toRoute()
            HomeStreak(datosStreak)
        }



    }
}