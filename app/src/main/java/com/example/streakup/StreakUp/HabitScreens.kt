package com.example.streakup.StreakUp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
fun HabitsScreen(navegante: NavHostController, currentUser: CurrentUser?) {
    var habits by remember { mutableStateOf<List<Habit>>(emptyList()) }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val loadHabitsErrorMessage = stringResource(R.string.load_habits_error)
    val completeHabitErrorMessage = stringResource(R.string.complete_habit_error)

    fun loadHabits() {
        val userId = currentUser?.id ?: return
        scope.launch {
            isLoading = true
            message = ""
            runCatching {
                StreakApi.getHabits(userId)
            }.onSuccess { loadedHabits ->
                habits = loadedHabits
            }.onFailure { error ->
                message = error.message ?: loadHabitsErrorMessage
            }
            isLoading = false
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
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.habits_title), color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(
                currentUser?.username ?: stringResource(R.string.no_user),
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            val user = currentUser
            if (user == null) {
                Text(stringResource(R.string.login_to_view_habits), color = Color.LightGray)
                Spacer(modifier = Modifier.size(12.dp))
                Button(onClick = { navegante.navigate(Home) }) {
                    Text(stringResource(R.string.go_to_login))
                }
                return@Column
            }

            Button(
                onClick = { navegante.navigate(CreateHabitRoute) },
                colors = ButtonDefaults.buttonColors(containerColor = LoginPurple),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.create_habit))
            }

            if (message.isNotBlank()) {
                Text(
                    message,
                    color = Color(0xFFFF8A80),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            if (isLoading) {
                Text(stringResource(R.string.loading), color = Color.LightGray, modifier = Modifier.padding(top = 16.dp))
            } else if (habits.isEmpty()) {
                Text(stringResource(R.string.no_habits_yet), color = Color.LightGray, modifier = Modifier.padding(top = 16.dp))
            } else {
                habits.forEach { habit ->
                    HabitRow(
                        habit = habit,
                        onComplete = {
                            scope.launch {
                                message = ""
                                runCatching {
                                    StreakApi.completeHabit(user.id, habit.id)
                                }.onSuccess {
                                    loadHabits()
                                }.onFailure { error ->
                                    message = error.message ?: completeHabitErrorMessage
                                }
                            }
                        }
                    )
                }
            }
        }

        BottomNavigation(navegante, selected = "habits", homeUserName = currentUser?.username.orEmpty())
    }
}

@Composable
fun CreateHabitScreen(navegante: NavHostController, currentUser: CurrentUser?) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val loginToCreateHabitsMessage = stringResource(R.string.login_to_create_habits)
    val habitNameRequiredMessage = stringResource(R.string.habit_name_required)
    val createHabitErrorMessage = stringResource(R.string.create_habit_error)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreakBG)
            .padding(16.dp)
    ) {
        Text(stringResource(R.string.create_habit), color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(
            currentUser?.username ?: stringResource(R.string.no_user),
            color = Color.LightGray,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(stringResource(R.string.habit_name_placeholder)) },
            modifier = Modifier.fillMaxWidth(),
            colors = streakTextFieldColors()
        )

        Spacer(modifier = Modifier.size(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text(stringResource(R.string.description_placeholder)) },
            modifier = Modifier.fillMaxWidth(),
            colors = streakTextFieldColors()
        )

        if (message.isNotBlank()) {
            Text(
                message,
                color = Color(0xFFFF8A80),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.size(18.dp))

        Button(
            onClick = {
                val userId = currentUser?.id
                if (userId == null) {
                    message = loginToCreateHabitsMessage
                    return@Button
                }
                if (name.isBlank()) {
                    message = habitNameRequiredMessage
                    return@Button
                }

                scope.launch {
                    isLoading = true
                    message = ""
                    runCatching {
                        StreakApi.createHabit(userId, name.trim(), description.trim())
                    }.onSuccess {
                        navegante.navigate(HabitsRoute)
                    }.onFailure { error ->
                        message = error.message ?: createHabitErrorMessage
                    }
                    isLoading = false
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = LoginPurple),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) stringResource(R.string.saving) else stringResource(R.string.save_habit))
        }

        Spacer(modifier = Modifier.size(10.dp))

        Button(
            onClick = { navegante.navigate(HabitsRoute) },
            colors = ButtonDefaults.buttonColors(containerColor = TextBoxColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.back))
        }
    }
}

@Composable
fun ProfileScreen(navegante: NavHostController, currentUser: CurrentUser?) {
    var profile by remember { mutableStateOf<ProfileResponse?>(null) }
    var message by remember { mutableStateOf("") }
    val loginToViewProfileMessage = stringResource(R.string.login_to_view_profile)
    val loadProfileErrorMessage = stringResource(R.string.load_profile_error)

    LaunchedEffect(currentUser?.id) {
        val userId = currentUser?.id
        if (userId == null) {
            message = loginToViewProfileMessage
            return@LaunchedEffect
        }

        runCatching {
            StreakApi.getProfile(userId)
        }.onSuccess { loadedProfile ->
            profile = loadedProfile
            message = ""
        }.onFailure { error ->
            message = error.message ?: loadProfileErrorMessage
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreakBG)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.profile_title), color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)

            if (message.isNotBlank()) {
                Text(
                    message,
                    color = Color(0xFFFF8A80),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            profile?.let { data ->
                ProfileLine(stringResource(R.string.profile_username), data.username)
                ProfileLine(stringResource(R.string.profile_email), data.email)
                ProfileLine(stringResource(R.string.profile_password), data.password)
                ProfileLine(stringResource(R.string.profile_habits_created), data.habitsCreated.toString())
                ProfileLine(stringResource(R.string.profile_habits_completed), data.habitsCompleted.toString())
            }
        }

        BottomNavigation(navegante, selected = "profile", homeUserName = currentUser?.username.orEmpty())
    }
}

@Composable
private fun HabitRow(habit: Habit, onComplete: () -> Unit) {
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
            Text(habit.description, color = Color.LightGray, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
        }
        Text(
            stringResource(R.string.habit_completed_count, habit.completedCount),
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
            Text(stringResource(R.string.mark_completed))
        }
    }
}

@Composable
private fun ProfileLine(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(TextBoxColor)
            .padding(14.dp)
    ) {
        Text(label, color = Color.LightGray, fontSize = 14.sp)
        Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun BottomNavigation(navegante: NavHostController, selected: String, homeUserName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(TextBoxColor),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { navegante.navigate(StreakUser(userName = homeUserName)) }
        ) {
            Text("🏠")
            Text(stringResource(R.string.home_tab), color = if (selected == "home") TextPurple else Color.LightGray, fontSize = 12.sp)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { navegante.navigate(HabitsRoute) }
        ) {
            Text("📋")
            Text(stringResource(R.string.habits_tab), color = if (selected == "habits") TextPurple else Color.LightGray, fontSize = 12.sp)
        }



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { navegante.navigate(ProfileRoute) }
        ) {
            Text("👤")
            Text(stringResource(R.string.profile_tab), color = if (selected == "profile") TextPurple else Color.LightGray, fontSize = 12.sp)
        }
    }
}

@Composable
private fun streakTextFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = TextBoxColor,
    unfocusedContainerColor = TextBoxColor,
    focusedIndicatorColor = Color.Gray,
    unfocusedIndicatorColor = Color.Gray,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White
)
