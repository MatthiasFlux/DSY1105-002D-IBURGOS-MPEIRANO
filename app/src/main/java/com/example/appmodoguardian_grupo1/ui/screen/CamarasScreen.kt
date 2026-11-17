package com.example.appmodoguardian_grupo1.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.CloudDone
import androidx.compose.material.icons.outlined.FlashlightOn
import androidx.compose.material.icons.outlined.MicOff
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val CamDarkBg = Color(0xFF0B1220)
private val CamCardBg = Color(0xFF131D31)
private val CamCardInnerBg = Color(0xFF19253D)
private val CamLightBlue = Color(0xFF60A5FA)
private val CamAccentBorder = Color(0xFF2B3C5A)
private val CamTextMuted = Color(0xFF94A3B8)
private val CamStatusGreen = Color(0xFF10B981)
private val CamStatusRed = Color(0xFFEF4444)

@Composable
fun CamarasScreen(navController: NavController) {
    val visorVerdeOscuro = Color(0xFF0E1F1A)
    val visorVerdeMedio = Color(0xFF162E26)
    val verdeTelemetria = Color(0xFF34D399)

    Scaffold(
        containerColor = CamDarkBg,
        bottomBar = {
            GuardianBottomBar(
                navController = navController,
                rutaActual = "camaras"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CamDarkBg)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. Encabezado superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        onClick = {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        shape = CircleShape,
                        color = CamCardBg,
                        border = BorderStroke(1.dp, CamAccentBorder),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver a Inicio",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Entrada Principal",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(CamStatusRed)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "EN VIVO • HD 1080P",
                                color = CamStatusRed,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.6.sp
                            )
                        }
                    }
                }

                // Badge REC
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(CamStatusRed.copy(alpha = 0.16f))
                        .border(1.dp, CamStatusRed.copy(alpha = 0.45f), RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(CamStatusRed)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "REC",
                        color = CamStatusRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // 2. Visor de Cámara en Vivo (Visión Nocturna)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(visorVerdeMedio, visorVerdeOscuro)
                        )
                    )
                    .border(1.dp, CamAccentBorder, RoundedCornerShape(20.dp))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val margen = 22.dp.toPx()
                    val largoGuia = 20.dp.toPx()
                    val grosorGuia = 2.dp.toPx()
                    val colorGuia = Color.White.copy(alpha = 0.25f)

                    // Esquina superior izquierda
                    drawLine(colorGuia, Offset(margen, margen), Offset(margen + largoGuia, margen), grosorGuia)
                    drawLine(colorGuia, Offset(margen, margen), Offset(margen, margen + largoGuia), grosorGuia)

                    // Esquina superior derecha
                    drawLine(colorGuia, Offset(size.width - margen, margen), Offset(size.width - margen - largoGuia, margen), grosorGuia)
                    drawLine(colorGuia, Offset(size.width - margen, margen), Offset(size.width - margen, margen + largoGuia), grosorGuia)

                    // Esquina inferior izquierda
                    val yInf = size.height - 56.dp.toPx()
                    drawLine(colorGuia, Offset(margen, yInf), Offset(margen + largoGuia, yInf), grosorGuia)
                    drawLine(colorGuia, Offset(margen, yInf), Offset(margen, yInf - largoGuia), grosorGuia)

                    // Esquina inferior derecha
                    drawLine(colorGuia, Offset(size.width - margen, yInf), Offset(size.width - margen - largoGuia, yInf), grosorGuia)
                    drawLine(colorGuia, Offset(size.width - margen, yInf), Offset(size.width - margen, yInf - largoGuia), grosorGuia)

                    // Silueta sutil de acceso monitoreado
                    val anchoPuerta = size.width * 0.28f
                    val altoPuerta = size.height * 0.44f
                    val leftPuerta = (size.width - anchoPuerta) / 2f
                    val topPuerta = (size.height - altoPuerta) / 2f - 10.dp.toPx()

                    drawRect(
                        color = Color(0xFF1F3D33).copy(alpha = 0.55f),
                        topLeft = Offset(leftPuerta, topPuerta),
                        size = Size(anchoPuerta, altoPuerta)
                    )
                    drawRect(
                        color = Color(0xFF2B5245).copy(alpha = 0.45f),
                        topLeft = Offset(leftPuerta, topPuerta),
                        size = Size(anchoPuerta, altoPuerta),
                        style = Stroke(width = 1.5.dp.toPx())
                    )
                }

                // Telemetría superior
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "17-11-2026 12:32:15",
                            color = verdeTelemetria,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "GUARDIÁN • CAM-01",
                            color = verdeTelemetria.copy(alpha = 0.8f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.55f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "IR • AUTO",
                            color = verdeTelemetria,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Barra inferior de grabación dentro del visor
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(Color(0xFF070C14).copy(alpha = 0.88f))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(CamStatusRed)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "REC  00:14:37",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "H.265 • 25FPS",
                            color = CamTextMuted,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Outlined.Wifi,
                            contentDescription = null,
                            tint = verdeTelemetria,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // 3. Controles Rápidos Esenciales (4 acciones)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonControlCamara(
                    modifier = Modifier.weight(1f),
                    icono = Icons.Default.CameraAlt,
                    etiqueta = "CAPTURA",
                    activo = false
                )
                BotonControlCamara(
                    modifier = Modifier.weight(1f),
                    icono = Icons.Outlined.Sensors,
                    etiqueta = "GRABAR",
                    activo = true
                )
                BotonControlCamara(
                    modifier = Modifier.weight(1f),
                    icono = Icons.Outlined.MicOff,
                    etiqueta = "AUDIO",
                    activo = false
                )
                BotonControlCamara(
                    modifier = Modifier.weight(1f),
                    icono = Icons.Outlined.FlashlightOn,
                    etiqueta = "LUZ",
                    activo = false
                )
            }

            // 4. Tarjeta Estado del Dispositivo
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CamCardBg),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, CamAccentBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ESTADO DEL DISPOSITIVO",
                            color = CamTextMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(CamStatusGreen)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TarjetaMetricaDispositivo(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Outlined.Sensors,
                            colorIcono = CamStatusGreen,
                            titulo = "ESTADO",
                            valor = "En línea",
                            colorValor = CamStatusGreen
                        )
                        TarjetaMetricaDispositivo(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Outlined.Wifi,
                            colorIcono = CamLightBlue,
                            titulo = "SEÑAL",
                            valor = "Excelente",
                            colorValor = Color.White
                        )
                        TarjetaMetricaDispositivo(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Outlined.CloudDone,
                            colorIcono = Color(0xFFA78BFA),
                            titulo = "GRABACIÓN",
                            valor = "Nube + Local",
                            colorValor = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BotonControlCamara(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    etiqueta: String,
    activo: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            onClick = { /* Solo muestra visual */ },
            shape = RoundedCornerShape(18.dp),
            color = if (activo) CamStatusRed else CamCardBg,
            border = BorderStroke(
                width = 1.dp,
                color = if (activo) CamStatusRed else CamAccentBorder
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icono,
                    contentDescription = etiqueta,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            text = etiqueta,
            color = if (activo) Color.White else CamTextMuted,
            fontSize = 11.sp,
            fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun TarjetaMetricaDispositivo(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    colorIcono: Color,
    titulo: String,
    valor: String,
    colorValor: Color
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(CamCardInnerBg)
            .border(1.dp, CamAccentBorder.copy(alpha = 0.7f), RoundedCornerShape(14.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(colorIcono.copy(alpha = 0.16f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = colorIcono,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = titulo,
            color = CamTextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = valor,
            color = colorValor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}