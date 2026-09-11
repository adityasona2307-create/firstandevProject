package com.example.whatsapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object SplashScreen : Routes()

    @Serializable
    data object WelcomeScreen : Routes()

    @Serializable
    data object UserRegistration : Routes()

    @Serializable
    data class OtpVerification(val phoneNumber: String) : Routes()

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object UpdateScreen : Routes()

    @Serializable
    data object CommunityScreen : Routes()

    @Serializable
    data object CallScreen : Routes()

    @Serializable
    data object ProfileScreen : Routes()
}
