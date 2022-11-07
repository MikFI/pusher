package ru.netology.pusher

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import java.io.FileInputStream


fun main() {

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
        .build()


    FirebaseApp.initializeApp(options)

    val message1 = Message.builder()
        .putData("action", "LIKE")
        .putData(
            "content", """{
          "userId": 1,
          "userName": "Vasiliy",
          "postId": 2,
          "postAuthor": "Netology"
        }""".trimIndent()
        )
        .setToken(token)
        .build()

    val message2 = Message.builder()
        .putData("action", "NEWPOST")
        .putData(
            "content", """{
          "postAuthor": "Netology",
          "postContent": "Рассказываем, как команда направления адаптирует программы под меняющиеся условия рынка, помогает студентам находить друзей в онлайне, организует стажировки, практики и даже хакатоны."
        }""".trimIndent()
        )
        .setToken(token)
        .build()

    try {
        FirebaseMessaging.getInstance().send(message2)
    } catch (e: Exception) {
        println("Firebase Notification Failed: ${e.message}")
        if (e is FirebaseMessagingException) {
            println("ErrorCodeName: ${e.errorCode.name}, messagingErrorCodeName: ${e.messagingErrorCode.name}")
        }
    }


}
