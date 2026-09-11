package com.example.whatsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.whatsapp.callUI.CallUi
import com.example.whatsapp.homescreen.HomeScreen
import com.example.whatsapp.presentation.splashscreen.SplashScreen
import com.example.whatsapp.profile.UserProfileScreen
import com.example.whatsapp.updatesAndui.CommunityUi
import com.example.whatsapp.updatesAndui.UpdatesAndUi
import com.example.whatsapp.userregistration.OtpVerification
import com.example.whatsapp.userregistration.UserRegistration
import com.example.whatsapp.viewModels.PhoneAuthViewModel
import com.example.whatsapp.welcomescreen.WelcomeScreen

@Composable
fun WhatsappNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SplashScreen) {
        composable<Routes.SplashScreen> {
            SplashScreen(navController = navController)
        }
        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController = navController)
        }
        composable<Routes.UserRegistration> {
            UserRegistration(navController = navController)
        }
        composable<Routes.OtpVerification> { backStackEntry ->
            val otpRoute: Routes.OtpVerification = backStackEntry.toRoute()
            // Sharing ViewModel by using the UserRegistration backstack entry
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<Routes.UserRegistration>()
            }
            val viewModel: PhoneAuthViewModel = hiltViewModel(parentEntry)
            OtpVerification(
                navController = navController,
                phoneNumber = otpRoute.phoneNumber,
                viewModel = viewModel
            )
        }
        composable<Routes.HomeScreen> {
            HomeScreen(navController = navController)
        }
        composable<Routes.UpdateScreen> {
            UpdatesAndUi(navController = navController)
        }
        composable<Routes.CommunityScreen> {
            CommunityUi(navController = navController)
        }
        composable<Routes.CallScreen> {
            CallUi(navController = navController)
        }
        composable<Routes.ProfileScreen> {
            UserProfileScreen(navController = navController)
        }
    }
}
