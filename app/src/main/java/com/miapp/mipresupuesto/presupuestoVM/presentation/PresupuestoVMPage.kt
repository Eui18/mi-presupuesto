package com.miapp.mipresupuesto.presupuestoVM.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miapp.mipresupuesto.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresupuestoVMPage(viewModel: PresupuestoViewModel = viewModel()) {

    val ingreso by viewModel.ingreso.collectAsStateWithLifecycle()
    val vivienda by viewModel.vivienda.collectAsStateWithLifecycle()
    val disponible by viewModel.disponible.collectAsStateWithLifecycle()
    val comida by viewModel.comida.collectAsStateWithLifecycle()
    val ocio by viewModel.ocio.collectAsStateWithLifecycle()
    val compras by viewModel.compras.collectAsStateWithLifecycle()
    val gastoTotal by viewModel.gastoTotal.collectAsStateWithLifecycle()
    val mensaje by viewModel.mensaje.collectAsStateWithLifecycle()
    val porcentajeAhorro by viewModel.porcentajeAhorro.collectAsStateWithLifecycle()
    val ahorro by viewModel.ahorro.collectAsStateWithLifecycle()
    val restante by viewModel.restante.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = RosaFondoSuave,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi presupuesto", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = RosaPrincipal,
                    titleContentColor = FondoBlanco
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 20.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(RosaTarjeta)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("Disponible", style = MaterialTheme.typography.labelMedium, color = TextoSecundario)
                    Text(
                        "$${"%.2f".format(disponible)}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = RosaPrincipal
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = ingreso,
                        onValueChange = { viewModel.cambiarIngreso(it) },
                        label = { Text("Ingreso mensual") }
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.agregarIngreso() },
                        colors = ButtonDefaults.buttonColors(RosaPrincipal)
                    ) {
                        Text("Agregar ingreso")
                    }
                }
            }

            Text(
                "Mis gastos",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Row(
                Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                GastoCard(
                    Modifier.weight(1f), "Vivienda", vivienda, ColorVivienda
                ) { viewModel.cambiarVivienda(it) }

                GastoCard(
                    Modifier.weight(1f), "Comida", comida, ColorComida
                ) { viewModel.cambiarComida(it) }
            }

            Row(
                Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                GastoCard(
                    Modifier.weight(1f), "Compras", compras, ColorCompras
                ) { viewModel.cambiarCompras(it) }

                GastoCard(
                    Modifier.weight(1f), "Ocio", ocio, ColorOcio
                ) { viewModel.cambiarOcio(it) }
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(RosaAhorro)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Ahorro", style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold, color = RosaPrincipal)
                        Text("${porcentajeAhorro.toInt()}%",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold, color = RosaPrincipal)
                    }

                    Text(
                        "Apartarás $${"%.2f".format(disponible * porcentajeAhorro / 100)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoSecundario
                    )

                    Slider(
                        value = porcentajeAhorro,
                        onValueChange = { viewModel.cambiarPorcentajeAhorro(it) },
                        valueRange = 0f..50f,
                        steps = 49,
                        colors = SliderDefaults.colors(
                            thumbColor = RosaPrincipal,
                            activeTickColor = RosaPrincipal,
                            inactiveTickColor = RosaBarra
                        )
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = { viewModel.calcularPresupuesto() },
                colors = ButtonDefaults.buttonColors(RosaPrincipal)
            ) {
                Text("Calcular presupuesto", fontWeight = FontWeight.Bold)
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(FondoBlanco)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text(
                        "Resumen",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )

                    FilaResumen("Gastos totales", gastoTotal, TextoPrincipal)
                    FilaResumen("Ahorro", ahorro, RosaPrincipal)
                    FilaResumen("Dinero restante", restante, VerdeExito)

                    Card(colors = CardDefaults.cardColors(VerdeSuave)) {
                        Column(Modifier.fillMaxWidth().padding(14.dp)) {
                            Text(
                                "Tu gasto diario aproximado",
                                style = MaterialTheme.typography.bodyMedium,
                                color = VerdeExito
                            )
                            Text(
                                "$${"%.2f".format(restante / 30.0)} al día",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = VerdeExito
                            )
                        }
                    }

                    Text(
                        mensaje,
                        modifier = Modifier.padding(top = 10.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoPrincipal
                    )
                }
            }

            Button(
                onClick = { viewModel.aplicarPresupuesto() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Text("Aplicar presupuesto")
            }

            Text(
                "Gastos por categoría",
                style = MaterialTheme.typography.titleLarge,
                color = TextoPrincipal,
                modifier = Modifier.padding(16.dp)
            )

            GastoBar("Vivienda", vivienda.toDoubleOrNull() ?: 0.0,
                viewModel.obtenerProporcion(vivienda), ColorVivienda)
            GastoBar("Comida", comida.toDoubleOrNull() ?: 0.0,
                viewModel.obtenerProporcion(comida), ColorComida)
            GastoBar("Ocio", ocio.toDoubleOrNull() ?: 0.0,
                viewModel.obtenerProporcion(ocio), ColorOcio)
            GastoBar("Compras", compras.toDoubleOrNull() ?: 0.0,
                viewModel.obtenerProporcion(compras), ColorCompras)
        }
    }
}

@Composable
fun GastoCard(
    modifier: Modifier = Modifier,
    texto: String,
    valor: String,
    color: Color,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(FondoBlanco)
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(texto, style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold, color = color)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = valor,
                onValueChange = onValueChange,
                label = { Text("Cantidad") }
            )
        }
    }
}

@Composable
fun GastoBar(text: String, valor: Double, proporcion: Float, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(FondoBlanco)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text, style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold, color = color)
                Text("$${"%.2f".format(valor)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, color = TextoPrincipal)
            }

            Spacer(Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { proporcion },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = color
            )
        }
    }
}

@Composable
fun FilaResumen(titulo: String, cantidad: Double, color: Color) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(titulo, color = TextoSecundario,
            style = MaterialTheme.typography.bodyLarge)
        Text("$${"%.2f".format(cantidad)}", color = color,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold)
    }
}