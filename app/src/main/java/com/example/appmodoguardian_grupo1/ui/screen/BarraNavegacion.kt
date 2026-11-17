package com.example.appmodoguardian_grupo1.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val NavCardBg = Color(0xFF131D31)
private val NavPrimaryBlue = Color(0xFF2563EB)
private val NavTextMuted = Color(0xFF94A3B8)
private val NavStatusRed = Color(0xFFEF4444)

private data class ItemNavGuardian(
    val ruta: String,
    val titulo: String,
    val iconoActivo: ImageVector,
    val iconoInactivo: ImageVector,
    val badge: Int = 0
)

fun NavController.navegarPestanaGuardian(ruta: String) {
    navigate(ruta) {
        popUpTo("home") { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun GuardianBottomBar(
    navController: NavController,
    rutaActual: String
) {
    val items = listOf(
        ItemNavGuardian("home", "Inicio", Icons.Filled.Home, Icons.Outlined.Home),
        ItemNavGuardian("camaras", "Cámaras", Icons.Filled.Videocam, Icons.Outlined.Videocam),
        ItemNavGuardian("eventos", "Eventos", Icons.Filled.FormatListBulleted, Icons.Outlined.FormatListBulleted),
        ItemNavGuardian("alertas", "Alertas", Icons.Filled.Notifications, Icons.Outlined.Notifications, badge = 3),
        ItemNavGuardian("perfil", "Perfil", Icons.Filled.Person, Icons.Outlined.Person)
    )

    NavigationBar(
        containerColor = NavCardBg,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val seleccionado = rutaActual == item.ruta
            NavigationBarItem(
                selected = seleccionado,
                onClick = {
                    if (!seleccionado) {
                        navController.navegarPestanaGuardian(item.ruta)
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badge > 0) {
                                Badge(
                                    containerColor = NavStatusRed,
                                    contentColor = Color.White
                                ) {
                                    Text(item.badge.toString(), fontSize = 10.sp)
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (seleccionado) item.iconoActivo else item.iconoInactivo,
                            contentDescription = item.titulo
                        )
                    }
                },
                label = {
                    Text(
                        text = item.titulo,
                        fontSize = 11.sp,
                        fontWeight = if (seleccionado) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = NavPrimaryBlue,
                    unselectedIconColor = NavTextMuted,
                    unselectedTextColor = NavTextMuted
                )
            )
        }
    }
}