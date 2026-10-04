package com.miapp.mipresupuesto.presupuestoVM.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
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

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Mi presupuesto")
                }
            )
        }
    ) {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
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
                    })
                GastoCard(modifier = Modifier.weight(1f),
                    texto = "Comida",
                    valor = comida,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarComida(nuevoValor)
                    })
            }
            Row() {
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Transporte",
                    valor =transporte,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarTransporte(nuevoValor)
                    })
                GastoCard(
                    modifier = Modifier.weight(1f),
                    texto = "Servicios",
                    valor = servicios,
                    onValueChange = {nuevoValor ->
                        viewModel.cambiarServicios(nuevoValor)
                    })
            }

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
