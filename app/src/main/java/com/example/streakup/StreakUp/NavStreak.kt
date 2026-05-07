package com.example.streakup.StreakUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
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
object HabitsRoute

@Serializable
object CreateHabitRoute

@Serializable
object ProfileRoute



@Serializable
data class StreakUser(
    val userName: String,
)

data class CurrentUser(
    val id: Int,
    val username: String,
    val email: String,
    val password: String
)


@Preview(showBackground = true)
@Composable

fun NavManager() {
    val navController = rememberNavController()
    var currentUser by remember { mutableStateOf<CurrentUser?>(null) }

    NavHost(navController, startDestination = Home){

        composable<Home> {
            LoginStreak(
                navegante = navController,
                onLoginSuccess = { user ->
                    currentUser = CurrentUser(
                        id = user.id,
                        username = user.username,
                        email = user.email,
                        password = user.password.orEmpty()
                    )
                }
            )
        }

        composable<CreateAccountObject> {
            CreateAccount(navController)
        }

        composable<StreakUser> {
            val datosStreak: StreakUser = it.toRoute()
            HomeStreak(navController, datosStreak, currentUser)
        }

        composable<HabitsRoute> {
            HabitsScreen(navController, currentUser)
        }

        composable<CreateHabitRoute> {
            CreateHabitScreen(navController, currentUser)
        }

        composable<ProfileRoute> {
            ProfileScreen(navController, currentUser)
        }


    }
}
