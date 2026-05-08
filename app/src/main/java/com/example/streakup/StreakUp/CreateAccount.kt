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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
fun CreateAccount(navegante: NavHostController){

    var name by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val requiredFieldsMessage = stringResource(R.string.signup_required_fields)
    val createAccountErrorMessage = stringResource(R.string.signup_error)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StreakBG)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(40.dp))

        Text(
            stringResource(R.string.create_account_title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            stringResource(R.string.create_account_subtitle),
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 5.dp, bottom = 25.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                stringResource(R.string.name_label),
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = stringResource(R.string.email_icon_description)
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.name_placeholder))
                },
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = TextBoxColor,
                    unfocusedContainerColor = TextBoxColor,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Text(
                stringResource(R.string.email_label),
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )

            OutlinedTextField(
                value = mail,
                onValueChange = { mail = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = stringResource(R.string.email_icon_description)
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.signup_email_placeholder))
                },
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = TextBoxColor,
                    unfocusedContainerColor = TextBoxColor,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Text(
                stringResource(R.string.password_label),
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = stringResource(R.string.password_icon_description)
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
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it }
                )

                Text(
                    text = stringResource(R.string.signup_terms_prefix) + " " +
                        stringResource(R.string.terms_and_conditions) + " " +
                        stringResource(R.string.signup_privacy_connector) + " " +
                        stringResource(R.string.privacy_policy),
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Button(
            onClick = {
                if (name.isBlank() || mail.isBlank() || password.isBlank()) {
                    errorMessage = requiredFieldsMessage
                    return@Button
                }

                scope.launch {
                    isLoading = true
                    errorMessage = ""
                    runCatching {
                        StreakApi.register(name.trim(), mail.trim(), password)
                    }.onSuccess {
                        navegante.navigate(Home)
                    }.onFailure { error ->
                        errorMessage = error.message ?: createAccountErrorMessage
                    }
                    isLoading = false
                }

            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LoginPurple,
                contentColor = Color.White
            )
        ) {
            Text(if (isLoading) stringResource(R.string.signup_button_loading) else stringResource(R.string.create_account_title))
        }

        if (errorMessage.isNotBlank()) {
            Text(
                errorMessage,
                color = Color(0xFFFF8A80),
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        Text(
            stringResource(R.string.continue_with),
            fontSize = 16.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1f)
                    .height(55.dp)
                    .background(TextBoxColor),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.googlelogo),
                    contentDescription = stringResource(R.string.google_logo_description),
                    modifier = Modifier
                        .size(60.dp)
                        .padding(6.dp)
                )

                Text(
                    stringResource(R.string.google_label),
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .weight(1f)
                    .height(55.dp)
                    .background(TextBoxColor),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.githublogo),
                    contentDescription = stringResource(R.string.github_logo_description),
                    modifier = Modifier
                        .size(60.dp)
                        .padding(6.dp)
                )

                Text(
                    stringResource(R.string.github_label),
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.already_have_account),
                fontSize = 16.sp,
                color = Color.White
            )

            Text(
                stringResource(R.string.login_link),
                fontSize = 16.sp,
                color = TextPurple,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(){
                    navegante.navigate(Home)
                }
            )
        }
    }
}
