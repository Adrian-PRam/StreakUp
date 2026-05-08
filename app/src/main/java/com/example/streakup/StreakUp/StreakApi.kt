package com.example.streakup.StreakUp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

private const val BASE_URL = "http://10.0.2.2:3000"

private val apiJson = Json {
    ignoreUnknownKeys = true
}

@Serializable
data class ApiUser(
    val id: Int,
    val username: String,
    val email: String,
    val password: String? = null
)

@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val message: String,
    val user: ApiUser
)

@Serializable
data class HabitRequest(
    val name: String,
    val description: String
)

@Serializable
data class Habit(
    val id: Int,
    @SerialName("user_id") val userId: Int,
    val name: String,
    val description: String? = "",
    @SerialName("completed_count") val completedCount: Int = 0
)

@Serializable
data class MessageResponse(
    val message: String
)

@Serializable
data class ProfileResponse(
    val username: String,
    val email: String,
    val password: String,
    @SerialName("habits_created") val habitsCreated: Int,
    @SerialName("habits_completed") val habitsCompleted: Int
)

object StreakApi {
    suspend fun register(username: String, email: String, password: String): AuthResponse {
        return request(
            path = "/register",
            method = "POST",
            body = apiJson.encodeToString(RegisterRequest(username, email, password))
        )
    }

    suspend fun login(email: String, password: String): AuthResponse {
        return request(
            path = "/login",
            method = "POST",
            body = apiJson.encodeToString(LoginRequest(email, password))
        )
    }

    suspend fun getHabits(userId: Int): List<Habit> {
        return request(path = "/users/$userId/habits", method = "GET")
    }

    suspend fun createHabit(userId: Int, name: String, description: String): Habit {
        return request(
            path = "/users/$userId/habits",
            method = "POST",
            body = apiJson.encodeToString(HabitRequest(name, description))
        )
    }

    suspend fun completeHabit(userId: Int, habitId: Int): MessageResponse {
        return request(path = "/users/$userId/habits/$habitId/complete", method = "POST")
    }

    suspend fun getProfile(userId: Int): ProfileResponse {
        return request(path = "/users/$userId/profile", method = "GET")
    }

    private suspend inline fun <reified T> request(
        path: String,
        method: String,
        body: String? = null
    ): T = withContext(Dispatchers.IO) {
        val connection = (URL("$BASE_URL$path").openConnection() as HttpURLConnection).apply {
            requestMethod = method
            connectTimeout = 10000
            readTimeout = 10000
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            if (body != null) {
                doOutput = true
            }
        }

        try {
            if (body != null) {
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(body)
                }
            }

            val responseCode = connection.responseCode
            val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
            val responseBody = stream?.bufferedReader()?.use { it.readText() }.orEmpty()

            if (responseCode !in 200..299) {
                val apiError = readApiError(responseBody)
                throw if (apiError == null) Exception() else Exception(apiError)
            }

            apiJson.decodeFromString<T>(responseBody)
        } finally {
            connection.disconnect()
        }
    }

    fun currentBaseUrl(): String = BASE_URL

    private fun readApiError(body: String): String? {
        if (body.isBlank()) return null

        return runCatching {
            apiJson.parseToJsonElement(body)
                .jsonObject["error"]
                ?.jsonPrimitive
                ?.content
        }.getOrNull() ?: body
    }
}
