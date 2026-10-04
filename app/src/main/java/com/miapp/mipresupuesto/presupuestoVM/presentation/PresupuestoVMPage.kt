package com.miapp.mipresupuesto.presupuestoVM.presentation

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miapp.mipresupuesto.ui.theme.FondoBlanco
import com.miapp.mipresupuesto.ui.theme.RosaPrincipal
import com.miapp.mipresupuesto.ui.theme.TextoPrincipal
import com.miapp.mipresupuesto.ui.theme.TextoSecundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresupuestoVMPage(viewModel: PresupuestoViewModel = viewModel()) {

    val ingreso by viewModel.ingreso.collectAsStateWithLifecycle()
    val vivienda by viewModel.vivienda.collectAsStateWithLifecycle()
    val disponible by viewModel.disponible.collectAsStateWithLifecycle()
    val comida by viewModel.comida.collectAsStateWithLifecycle()
    val transporte by viewModel.transporte.collectAsStateWithLifecycle()
    val servicios by viewModel.servicios.collectAsStateWithLifecycle()
    val ropa by viewModel.ropa.collectAsStateWithLifecycle()
    val ocio by viewModel.ocio.collectAsStateWithLifecycle()
    val compras by viewModel.compras.collectAsStateWithLifecycle()
    val cuidadoPersonal by viewModel.cuidadoPersonal.collectAsStateWithLifecycle()
    val gastoTotal by viewModel.gastoTotal.collectAsStateWithLifecycle()
    val mensaje by viewModel.mensaje.collectAsStateWithLifecycle()
    val porcentajeAhorro by viewModel.porcentajeAhorro.collectAsStateWithLifecycle()
    val ahorro by viewModel.ahorro.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Mi presupuesto")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = RosaPrincipal,
                    titleContentColor = FondoBlanco
                )
            )
        }
    ) {paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
        ) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp))
            {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Disponible",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextoSecundario
                    )
                    Text(
                        text = "$${"%.2f".format(disponible)}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextoPrincipal
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = ingreso,
                        onValueChange = {nuevoValor ->
                            viewModel.cambiarIngreso(nuevoValor)},
                        label = {Text("Ingreso mensual")}
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {viewModel.agregarIngreso()}
                    ) {
                        Text("Agregar ingreso")
                    }
                }
            }

            Text(
                text = "Gastos básicos",
                style = MaterialTheme.typography.titleLarge,
                color = TextoPrincipal,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Row() {
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Vivienda",
                    valor = vivienda,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarVivienda(nuevoValor)
                    }
                )
                GastoCard(modifier = Modifier.weight(1f),
                    texto = "Comida",
                    valor = comida,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarComida(nuevoValor)
                    }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row() {
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Transporte",
                    valor =transporte,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarTransporte(nuevoValor)
                    }
                )
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Servicios",
                    valor = servicios,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarServicios(nuevoValor)
                    }
                )
            }
            Text(
                text = "Gastos personales",
                style = MaterialTheme.typography.titleLarge,
                color = TextoPrincipal,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

            )
            Row() {
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Ropa",
                    valor = ropa,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarRopa(nuevoValor)
                    }
                )
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Ocio",
                    valor = ocio,
                    onValueChange = { nuevoValor ->
                        viewModel.cambiarOcio(nuevoValor)
                    }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row() {
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Compras",
                    valor = compras,
                    onValueChange = { nuevoValor ->
                        viewModel.cambiarCompras(nuevoValor)
                    }
                )
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Cuidado personal",
                    valor = cuidadoPersonal,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarCuidadoPersonal(nuevoValor)
                    }
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Ahorro",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextoPrincipal
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${porcentajeAhorro.toInt()}% de mi dinero",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoSecundario
                    )
                    Text(
                        text = "$${"%.2f".format(ahorro)}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = RosaPrincipal
                    )
                    Slider(
                        value = porcentajeAhorro,
                        onValueChange = {nuevoValor ->
                            viewModel.cambiarPorcentajeAhorro(nuevoValor)
                        },
                        valueRange = 0f..50f,
                        steps = 49
                    )
                }
            }

            Button(
                onClick = {viewModel.validarPresupuesto() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ){
                Text("Calcular presupuesto")
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Resumen",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextoPrincipal
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Gastos totales",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoSecundario
                    )

                    Text(
                        text = "$${"%.2f".format(gastoTotal)}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = RosaPrincipal
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = mensaje,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoPrincipal
                    )
                }
            }

            Text(
                text = "Gastos por categoría",
                style = MaterialTheme.typography.titleLarge,
                color = TextoPrincipal,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            GastoBar(
                text = "Vivienda",
                valor = vivienda.toDoubleOrNull() ?: 0.0,
                proporcion = viewModel.obtenerProporcion(vivienda)
            )

            GastoBar(
                text = "Comida",
                valor = comida.toDoubleOrNull() ?: 0.0,
                proporcion = viewModel.obtenerProporcion(comida)
            )

            GastoBar(
                text = "Transporte",
                valor = transporte.toDoubleOrNull() ?: 0.0,
                proporcion = viewModel.obtenerProporcion(transporte)
            )

            GastoBar(
                text = "Servicios",
                valor = servicios.toDoubleOrNull() ?: 0.0,
                proporcion = viewModel.obtenerProporcion(servicios)
            )

            GastoBar(
                text = "Ropa",
                valor = ropa.toDoubleOrNull() ?: 0.0,
                proporcion = viewModel.obtenerProporcion(ropa)
            )

            GastoBar(
                text = "Ocio",
                valor = ocio.toDoubleOrNull() ?: 0.0,
                proporcion = viewModel.obtenerProporcion(ocio)
            )

            GastoBar(
                text = "Compras",
                valor = compras.toDoubleOrNull() ?: 0.0,
                proporcion = viewModel.obtenerProporcion(compras)
            )

            GastoBar(
                text = "Cuidado personal",
                valor = cuidadoPersonal.toDoubleOrNull() ?: 0.0,
                proporcion = viewModel.obtenerProporcion(cuidadoPersonal)
            )
        }
    }
}

@Composable
fun GastoCard (modifier: Modifier = Modifier, texto: String, valor: String, onValueChange: (String) -> Unit) {

    Card(
        modifier = modifier.padding(12.dp),
    ) {
        Column(
            Modifier.padding(12.dp)
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.titleMedium,
                color = TextoPrincipal
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = valor,
                onValueChange = {nuevoValor -> onValueChange (nuevoValor) },
                label = {Text("Ingresa el gasto")}
            )
        }
    }
}

@Composable
fun GastoBar (text: String, valor: Double, proporcion: Float) {
    Column {
        Row {
            Text(text)
            Text("$${"%.2f".format(valor)}")
        }
        LinearProgressIndicator(
            progress = { proporcion }
        )
    }
}
