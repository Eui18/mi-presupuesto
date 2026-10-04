package com.miapp.mipresupuesto.presupuestoVM.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

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
    val mayorGasto = viewModel.obtenerMayorGasto()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Mi presupuesto")
                }
            )
        }
    ) {paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
        ) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp))
            {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Disponible")
                    Text(disponible.toString())
                    OutlinedTextField(
                        value = ingreso,
                        onValueChange = {nuevoValor -> viewModel.cambiarIngreso(nuevoValor)},
                        label = {Text("Ingreso mensual")}
                    )
                    Button(
                        onClick = {viewModel.agregarIngreso()}
                    ) {
                        Text("Agregar ingreso")
                    }
                }
            }
            Text("Gastos básicos")
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
            Text("Gastos personales")
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
            Text("Porcentaje de ahorro: ${porcentajeAhorro.toInt()}%")
            Text("Dinero destinado al ahorro: $${"%.2f".format(ahorro)}")
            Slider(
                value = porcentajeAhorro,
                onValueChange = {nuevoValor ->
                    viewModel.cambiarPorcentajeAhorro(nuevoValor)
                },
                valueRange = 0f..50f,
                steps = 49
            )

            Button(onClick = {viewModel.validarPresupuesto() }
            ){
                Text("Calcular presupuesto")
            }
            Text("$${"%.2f".format(gastoTotal)}")
            Text(mensaje)

            Text("Gastos por categoría")

            GastoBar(
                text = "Vivienda",
                valor = vivienda.toDoubleOrNull() ?: 0.0,
                proporcion = ((vivienda.toDoubleOrNull() ?: 0.0) / mayorGasto).toFloat()
            )

            GastoBar(
                text = "Comida",
                valor = comida.toDoubleOrNull() ?: 0.0,
                proporcion = ((comida.toDoubleOrNull() ?: 0.0) / mayorGasto).toFloat()
            )

            GastoBar(
                text = "Transporte",
                valor = transporte.toDoubleOrNull() ?: 0.0,
                proporcion = ((transporte.toDoubleOrNull() ?: 0.0) / mayorGasto).toFloat()
            )

            GastoBar(
                text = "Servicios",
                valor = servicios.toDoubleOrNull() ?: 0.0,
                proporcion = ((servicios.toDoubleOrNull() ?: 0.0) / mayorGasto).toFloat()
            )

            GastoBar(
                text = "Ropa",
                valor = ropa.toDoubleOrNull() ?: 0.0,
                proporcion = ((ropa.toDoubleOrNull() ?: 0.0) / mayorGasto).toFloat()
            )

            GastoBar(
                text = "Ocio",
                valor = ocio.toDoubleOrNull() ?: 0.0,
                proporcion = ((ocio.toDoubleOrNull() ?: 0.0) / mayorGasto).toFloat()
            )

            GastoBar(
                text = "Compras",
                valor = compras.toDoubleOrNull() ?: 0.0,
                proporcion = ((compras.toDoubleOrNull() ?: 0.0) / mayorGasto).toFloat()
            )

            GastoBar(
                text = "Cuidado personal",
                valor = cuidadoPersonal.toDoubleOrNull() ?: 0.0,
                proporcion = ((cuidadoPersonal.toDoubleOrNull() ?: 0.0) / mayorGasto).toFloat()
            )
        }
    }
}

@Composable
fun GastoCard (modifier: Modifier = Modifier, texto: String, valor: String, onValueChange: (String) -> Unit) {

    Card(modifier = modifier.padding(16.dp)) {
        Column(Modifier.padding(5.dp)) {
            Text(texto)
            OutlinedTextField(
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
