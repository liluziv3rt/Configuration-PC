package com.example.myapplication.Navigation

import SecondActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.SplashScreen.SplashScreen
import com.example.myapplication.login.LoginScreen
import com.example.myapplication.Main.MainScreen

@Composable
fun NavHost()
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash")
    {
        composable("main")
        {
            MainScreen(navController)
        }
        composable("login")
        {
            LoginScreen(navController)
        }
        composable("splash")
        {
            SplashScreen(navController)
        }
        composable("registration")
        {
            SecondActivity(navController)
        }
    }

}

