package com.example.appmodoguardian_grupo1.ui.screen

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val AlertDarkBg = Color(0xFF0B1220)
private val AlertCardBg = Color(0xFF131D31)
private val AlertCardInnerBg = Color(0xFF19253D)
private val AlertPrimaryBlue = Color(0xFF2563EB)
private val AlertLightBlue = Color(0xFF60A5FA)
private val AlertAccentBorder = Color(0xFF2B3C5A)
private val AlertTextMuted = Color(0xFF94A3B8)
private val AlertStatusGreen = Color(0xFF10B981)
private val AlertStatusAmber = Color(0xFFF59E0B)
private val AlertStatusRed = Color(0xFFEF4444)

@Composable
fun AlertasScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        containerColor = AlertDarkBg,
        bottomBar = {
            GuardianBottomBar(
                navController = navController,
                rutaActual = "alertas"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AlertDarkBg)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                        color = AlertCardBg,
                        border = BorderStroke(1.dp, AlertAccentBorder),
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

                    Text(
                        text = "Alerta Crítica",
                        color = Color.White,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(AlertStatusRed)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "CRÍTICO",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Hace 2 min",
                        color = AlertTextMuted,
                        fontSize = 11.sp
                    )
                }
            }

            // Línea de acento superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                AlertStatusRed,
                                AlertStatusRed.copy(alpha = 0.25f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // 2. Tarjeta Principal de Alerta Detectada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF281725),
                                AlertCardBg
                            )
                        )
                    )
                    .border(1.dp, AlertStatusRed.copy(alpha = 0.45f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(AlertStatusRed.copy(alpha = 0.16f))
                                .border(1.dp, AlertStatusRed.copy(alpha = 0.35f), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.WarningAmber,
                                contentDescription = null,
                                tint = AlertStatusRed,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Movimiento no autorizado detectado",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 22.sp
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = null,
                                    tint = AlertStatusRed,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Perímetro Norte - Cámara 04",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 13.sp
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Schedule,
                                    contentDescription = null,
                                    tint = AlertTextMuted,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "17-11-2026 12:38:18",
                                    color = AlertTextMuted,
                                    fontSize = 12.sp
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(AlertStatusGreen.copy(alpha = 0.18f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Visibility,
                                        contentDescription = null,
                                        tint = AlertStatusGreen,
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Confianza IA: 92% persona detectada",
                                    color = AlertStatusGreen,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFF1F2A3E))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.92f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(50))
                                .background(AlertStatusGreen)
                        )
                    }
                }
            }

            // 3. Evidencia Visual (Clip de video + Ubicación en mapa)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    onClick = { /* Solo muestra visual */ },
                    shape = RoundedCornerShape(18.dp),
                    color = AlertCardBg,
                    border = BorderStroke(1.dp, AlertAccentBorder),
                    modifier = Modifier
                        .weight(1f)
                        .height(154.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(AlertCardInnerBg, Color(0xFF0E1525))
                                )
                            )
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.align(Alignment.TopStart),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(AlertStatusRed)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CAM-04 • REC",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.55f))
                                    .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Reproducir clip",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .padding(horizontal = 10.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "Ver video de 10s",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "00:10 • IR • 1080p",
                                color = AlertTextMuted,
                                fontSize = 9.sp
                            )
                            Icon(
                                imageVector = Icons.Outlined.Videocam,
                                contentDescription = null,
                                tint = AlertTextMuted,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(154.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(AlertCardBg)
                        .border(1.dp, AlertAccentBorder, RoundedCornerShape(18.dp))
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val step = 22.dp.toPx()
                        val gridColor = AlertAccentBorder.copy(alpha = 0.45f)

                        var x = step
                        while (x < size.width) {
                            drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), 1f)
                            x += step
                        }
                        var y = step
                        while (y < size.height) {
                            drawLine(gridColor, Offset(0f, y), Offset(size.width, y), 1f)
                            y += step
                        }

                        val p1 = Offset(size.width * 0.22f, size.height * 0.32f)
                        val p2 = Offset(size.width * 0.78f, size.height * 0.32f)
                        val p3 = Offset(size.width * 0.74f, size.height * 0.72f)
                        val p4 = Offset(size.width * 0.26f, size.height * 0.72f)

                        val path = Path().apply {
                            moveTo(p1.x, p1.y)
                            lineTo(p2.x, p2.y)
                            lineTo(p3.x, p3.y)
                            lineTo(p4.x, p4.y)
                            close()
                        }
                        drawPath(
                            path = path,
                            color = AlertLightBlue.copy(alpha = 0.35f),
                            style = Stroke(width = 1.5.dp.toPx())
                        )

                        val camPos = Offset(size.width * 0.58f, size.height * 0.36f)
                        drawCircle(
                            color = AlertStatusRed.copy(alpha = 0.24f),
                            radius = 18.dp.toPx(),
                            center = camPos
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 6.dp.toPx(),
                            center = camPos
                        )
                        drawCircle(
                            color = AlertStatusRed,
                            radius = 4.dp.toPx(),
                            center = camPos
                        )
                    }

                    Text(
                        text = "NORTE",
                        color = AlertTextMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = 12.dp, y = 4.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(AlertStatusRed)
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "CAM-04",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "PERÍMETRO NORTE",
                        color = AlertTextMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp)
                    )
                }
            }

            // 4. Tarjeta Detalles del Evento
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AlertCardBg),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, AlertAccentBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = null,
                            tint = AlertTextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "DETALLES DEL EVENTO",
                            color = AlertTextMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    }

                    HorizontalDivider(color = AlertAccentBorder.copy(alpha = 0.6f))

                    FilaDetalleAlerta(
                        icono = Icons.Outlined.Shield,
                        colorIcono = AlertStatusRed,
                        etiqueta = "Tipo",
                        valor = "Intrusión perimetral"
                    )
                    FilaDetalleAlerta(
                        icono = Icons.Outlined.LocationOn,
                        colorIcono = AlertLightBlue,
                        etiqueta = "Zona",
                        valor = "Perímetro Norte - Sector B"
                    )
                    FilaDetalleAlerta(
                        icono = Icons.Outlined.Videocam,
                        colorIcono = AlertLightBlue,
                        etiqueta = "Cámara",
                        valor = "CAM-04"
                    )
                    FilaDetalleAlerta(
                        icono = Icons.Outlined.Timer,
                        colorIcono = AlertTextMuted,
                        etiqueta = "Duración",
                        valor = "12 segundos"
                    )
                }
            }

            // 5. Acciones Requeridas (Segunda captura)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(14.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(AlertStatusRed)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ACCIONES REQUERIDAS",
                        color = AlertTextMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                }

                // Botón principal: Confirmar Alerta
                Button(
                    onClick = {
                        Toast.makeText(context, "Alerta confirmada y enviada a Central", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AlertStatusRed,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Confirmar Alerta - Enviar a Central",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Botón secundario: Falsa Alarma
                OutlinedButton(
                    onClick = {
                        Toast.makeText(context, "Marcada como falsa alarma", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, AlertStatusAmber.copy(alpha = 0.55f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = AlertStatusAmber.copy(alpha = 0.12f),
                        contentColor = AlertStatusAmber
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Falsa Alarma",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Fila compacta con las 2 acciones complementarias
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* Solo muestra visual */ },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, AlertAccentBorder),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = AlertCardBg,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = AlertLightBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Reportar foto",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    OutlinedButton(
                        onClick = { /* Solo muestra visual */ },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, AlertAccentBorder),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = AlertCardBg,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = AlertStatusGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Llamar Central",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // 6. Aviso de Protocolo Guardián
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(AlertStatusRed.copy(alpha = 0.08f))
                    .border(1.dp, AlertStatusRed.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.WarningAmber,
                    contentDescription = null,
                    tint = AlertStatusRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFFCA5A5),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Protocolo Guardián: ")
                        }
                        withStyle(style = SpanStyle(color = AlertTextMuted)) {
                            append("Confirmar en menos de 2 minutos o se escala automáticamente a Central y Jefe de Seguridad.")
                        }
                    },
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }

            // 7. Barra de Tiempo de Respuesta
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AlertCardBg),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, AlertAccentBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(AlertStatusAmber)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "TIEMPO DE RESPUESTA",
                                color = AlertTextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.6.sp
                            )
                        }

                        Text(
                            text = "01:46 restante",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(AlertCardInnerBg)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.86f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(50))
                                .background(AlertPrimaryBlue)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "00:00",
                            color = AlertTextMuted,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "02:00 AUTO-ESCALADO",
                            color = AlertTextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun FilaDetalleAlerta(
    icono: ImageVector,
    colorIcono: Color,
    etiqueta: String,
    valor: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(AlertCardInnerBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = colorIcono,
                    modifier = Modifier.size(17.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = etiqueta,
                color = AlertTextMuted,
                fontSize = 13.sp
            )
        }

        Text(
            text = valor,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}