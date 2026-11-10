package com.example.appmodoguardian_grupo1.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmodoguardian_grupo1.ui.screen.HomeScreen
import com.example.appmodoguardian_grupo1.ui.screen.LogScreen
import com.example.appmodoguardian_grupo1.ui.screen.LoginScreen
import com.example.appmodoguardian_grupo1.ui.screen.ProfileScreen
import com.example.appmodoguardian_grupo1.ui.screen.RegistroScreen
import com.example.appmodoguardian_grupo1.ui.screen.ResumenScreen
import com.example.appmodoguardian_grupo1.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "log"
    ) {
        composable(route = "log") {
            LogScreen(
                navController = navController
            )
        }

        composable(route = "login") {
            LoginScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable(route = "registro") {
            RegistroScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable(route = "resumen") {
            ResumenScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable(route = "home") {
            HomeScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable(route = "perfil") {
            ProfileScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }
    }
}