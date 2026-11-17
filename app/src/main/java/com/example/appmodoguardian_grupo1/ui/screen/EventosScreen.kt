package com.example.appmodoguardian_grupo1.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val EvDarkBg = Color(0xFF0B1220)
private val EvCardBg = Color(0xFF131D31)
private val EvCardInnerBg = Color(0xFF19253D)
private val EvPrimaryBlue = Color(0xFF2563EB)
private val EvLightBlue = Color(0xFF60A5FA)
private val EvAccentBorder = Color(0xFF2B3C5A)
private val EvTextMuted = Color(0xFF94A3B8)
private val EvStatusGreen = Color(0xFF10B981)
private val EvStatusAmber = Color(0xFFF59E0B)
private val EvStatusRed = Color(0xFFEF4444)

private data class RegistroEventoItem(
    val tramoHorario: String,
    val categoria: String, // "Alertas", "Eventos", "Accesos"
    val titulo: String,
    val ubicacion: String,
    val horaYDetalle: String,
    val camara: String,
    val camaraOnline: Boolean,
    val estadoTexto: String,
    val colorAcento: Color,
    val colorEstado: Color,
    val icono: ImageVector,
    val notaInferior: String? = null,
    val esCritica: Boolean = false
)

@Composable
fun EventosScreen(navController: NavController) {
    val context = LocalContext.current
    var filtroSeleccionado by remember { mutableStateOf("Todos") }
    var textoBusqueda by remember { mutableStateOf("") }

    val categorias = listOf("Todos", "Alertas", "Eventos", "Accesos")

    val listaEventos = remember {
        listOf(
            RegistroEventoItem(
                tramoHorario = "14:00 - 15:00",
                categoria = "Alertas",
                titulo = "Movimiento no autorizado",
                ubicacion = "Perímetro Norte",
                horaYDetalle = "14:32 • Zona C • Sensor PIR-04",
                camara = "CAM-03",
                camaraOnline = false,
                estadoTexto = "Confirmada",
                colorAcento = EvStatusRed,
                colorEstado = EvStatusGreen,
                icono = Icons.Outlined.Shield,
                notaInferior = "CONFIRMADA • EVIDENCIA GUARDADA",
                esCritica = true
            ),
            RegistroEventoItem(
                tramoHorario = "14:00 - 15:00",
                categoria = "Accesos",
                titulo = "Acceso autorizado",
                ubicacion = "Entrada Principal",
                horaYDetalle = "14:10 • Credencial #4582 • A. Rivera",
                camara = "CAM-01",
                camaraOnline = true,
                estadoTexto = "Autorizado",
                colorAcento = EvStatusGreen,
                colorEstado = EvStatusGreen,
                icono = Icons.Outlined.Login
            ),
            RegistroEventoItem(
                tramoHorario = "14:00 - 15:00",
                categoria = "Eventos",
                titulo = "Cámara desconectada",
                ubicacion = "Bodega A",
                horaYDetalle = "14:05 • Offline 3m 12s • Auto-reconexión",
                camara = "CAM-07",
                camaraOnline = true,
                estadoTexto = "Resuelto",
                colorAcento = EvStatusAmber,
                colorEstado = EvLightBlue,
                icono = Icons.Outlined.VideocamOff
            ),
            RegistroEventoItem(
                tramoHorario = "13:00 - 14:00",
                categoria = "Alertas",
                titulo = "Puerta abierta fuera de horario",
                ubicacion = "Almacén Central",
                horaYDetalle = "13:48 • Puerta D-02 • 4m abierta",
                camara = "CAM-05",
                camaraOnline = false,
                estadoTexto = "Pendiente",
                colorAcento = EvStatusRed,
                colorEstado = EvStatusAmber,
                icono = Icons.Outlined.Shield,
                esCritica = true
            ),
            RegistroEventoItem(
                tramoHorario = "13:00 - 14:00",
                categoria = "Eventos",
                titulo = "Ronda completada",
                ubicacion = "Perímetro Sur",
                horaYDetalle = "13:22 • Guardián • Ruta 2 • 18 checkpoints",
                camara = "CAM-04",
                camaraOnline = true,
                estadoTexto = "Confirmada",
                colorAcento = EvPrimaryBlue,
                colorEstado = EvStatusGreen,
                icono = Icons.Outlined.DirectionsWalk
            ),
            RegistroEventoItem(
                tramoHorario = "13:00 - 14:00",
                categoria = "Accesos",
                titulo = "Salida autorizada",
                ubicacion = "Portón Vehicular",
                horaYDetalle = "13:05 • Placa JKL-892 • Logística",
                camara = "CAM-02",
                camaraOnline = true,
                estadoTexto = "Autorizado",
                colorAcento = EvStatusGreen,
                colorEstado = EvStatusGreen,
                icono = Icons.Outlined.Login
            )
        )
    }

    val eventosFiltrados = listaEventos.filter { item ->
        val coincideCategoria = filtroSeleccionado == "Todos" || item.categoria == filtroSeleccionado
        val query = textoBusqueda.trim().lowercase()
        val coincideTexto = query.isEmpty() ||
                item.titulo.lowercase().contains(query) ||
                item.ubicacion.lowercase().contains(query) ||
                item.camara.lowercase().contains(query)
        coincideCategoria && coincideTexto
    }

    val gruposPorHora = eventosFiltrados.groupBy { it.tramoHorario }

    Scaffold(
        containerColor = EvDarkBg,
        bottomBar = {
            GuardianBottomBar(
                navController = navController,
                rutaActual = "eventos"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(EvDarkBg)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Encabezado
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Historial",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(EvStatusGreen)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Turno actual • 17 Nov • 14:00 - 22:00",
                        color = EvTextMuted,
                        fontSize = 13.sp
                    )
                }
            }

            // 2. Tarjeta de Resumen y Progreso de Turno
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = EvCardBg),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, EvAccentBorder)
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF192642), EvCardBg)
                            )
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ColumnaResumenTurno(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Outlined.CalendarToday,
                            colorIcono = EvLightBlue,
                            etiqueta = "HOY",
                            numero = "24",
                            descripcion = "eventos\nregistrados"
                        )
                        ColumnaResumenTurno(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Outlined.WarningAmber,
                            colorIcono = EvStatusRed,
                            etiqueta = "CRÍTICAS",
                            numero = "3",
                            descripcion = "alertas\nconfirmadas"
                        )
                        ColumnaResumenTurno(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Outlined.Schedule,
                            colorIcono = EvStatusAmber,
                            etiqueta = "PENDIENTE",
                            numero = "1",
                            descripcion = "requiere\natención"
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "PROGRESO DE TURNO",
                                color = EvTextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.6.sp
                            )
                            Text(
                                text = "62%  •  5h 12m restantes",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(50))
                                .background(EvCardInnerBg)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.62f)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(50))
                                    .background(EvPrimaryBlue)
                            )
                        }
                    }
                }
            }

            // 3. Pestañas de Categoría
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categorias.forEach { categoria ->
                    val activo = filtroSeleccionado == categoria
                    Surface(
                        onClick = { filtroSeleccionado = categoria },
                        shape = RoundedCornerShape(50),
                        color = if (activo) EvPrimaryBlue else EvCardBg,
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (activo) EvPrimaryBlue else EvAccentBorder
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 9.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = categoria,
                                color = if (activo) Color.White else EvTextMuted,
                                fontSize = 12.sp,
                                fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // 4. Barra de Búsqueda Esencial
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = {
                    Text(
                        text = "Buscar por cámara, zona o tipo...",
                        color = EvTextMuted,
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = EvTextMuted,
                        modifier = Modifier.size(19.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = EvCardBg,
                    unfocusedContainerColor = EvCardBg,
                    focusedBorderColor = EvPrimaryBlue,
                    unfocusedBorderColor = EvAccentBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = EvLightBlue
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // 5. Listado Cronológico por Tramo Horario
            if (gruposPorHora.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = EvCardBg),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, EvAccentBorder)
                ) {
                    Text(
                        text = "No se encontraron eventos con ese filtro.",
                        color = EvTextMuted,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            } else {
                gruposPorHora.forEach { (tramo, eventosDelTramo) ->
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Encabezado del tramo horario
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Schedule,
                                    contentDescription = null,
                                    tint = EvTextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = tramo,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = "${eventosDelTramo.size} eventos",
                                color = EvTextMuted,
                                fontSize = 12.sp
                            )
                        }

                        // Tarjetas de cada evento
                        eventosDelTramo.forEach { evento ->
                            TarjetaEventoHistorial(
                                evento = evento,
                                onClick = {
                                    if (evento.esCritica) {
                                        navController.navigate("alertas")
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // 6. Separador de cierre y Exportar Reporte PDF
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = EvAccentBorder.copy(alpha = 0.6f)
                )
                Text(
                    text = "FIN DEL TURNO ANTERIOR",
                    color = EvTextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = EvAccentBorder.copy(alpha = 0.6f)
                )
            }

            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Generando reporte de turno en PDF...",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = EvPrimaryBlue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Download,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Exportar reporte de turno",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "PDF",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun ColumnaResumenTurno(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    colorIcono: Color,
    etiqueta: String,
    numero: String,
    descripcion: String
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(EvCardInnerBg.copy(alpha = 0.7f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = colorIcono,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = etiqueta,
                color = colorIcono,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = numero,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = descripcion,
            color = EvTextMuted,
            fontSize = 11.sp,
            lineHeight = 14.sp
        )
    }
}

@Composable
private fun TarjetaEventoHistorial(
    evento: RegistroEventoItem,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = EvCardBg,
        border = BorderStroke(1.dp, EvAccentBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // Barra lateral de color según severidad
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(evento.colorAcento)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    // Ícono de categoría
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(evento.colorAcento.copy(alpha = 0.14f))
                            .border(1.dp, evento.colorAcento.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = evento.icono,
                            contentDescription = null,
                            tint = evento.colorAcento,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = evento.titulo,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (evento.esCritica) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .clip(CircleShape)
                                            .background(EvStatusRed)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Badge de estado
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(evento.colorEstado.copy(alpha = 0.15f))
                                    .border(1.dp, evento.colorEstado.copy(alpha = 0.35f), RoundedCornerShape(50))
                                    .padding(horizontal = 9.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (evento.estadoTexto == "Confirmada") {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = evento.colorEstado,
                                        modifier = Modifier.size(11.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                }
                                Text(
                                    text = evento.estadoTexto,
                                    color = evento.colorEstado,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = evento.ubicacion,
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = evento.horaYDetalle,
                                color = EvTextMuted,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Etiqueta compacta de cámara
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(EvCardInnerBg)
                                    .border(1.dp, EvAccentBorder, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Videocam,
                                    contentDescription = null,
                                    tint = EvTextMuted,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = evento.camara,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(if (evento.camaraOnline) EvStatusGreen else EvStatusRed)
                                )
                            }
                        }
                    }
                }

                if (evento.notaInferior != null) {
                    HorizontalDivider(color = EvAccentBorder.copy(alpha = 0.5f))
                    Text(
                        text = evento.notaInferior,
                        color = EvStatusRed.copy(alpha = 0.9f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}