package com.MyApp.mipresupuesto.presupuestoVM.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresupuestoVMPage (viewModel: PresupuestoViewModel = viewModel()) {

    var ingreso by rememberSaveable {mutableStateOf("")}
    var basicos by rememberSaveable { mutableStateOf("") }
    var personales by rememberSaveable { mutableStateOf("")}
    var ahorro by rememberSaveable {mutableStateOf("") }
    val restante by viewModel.restante.collectAsStateWithLifecycle()
    val mensaje by viewModel.mensaje.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text ("Mi presupuesto")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            OutlinedTextField(
                value = ingreso,
                onValueChange = { ingreso = it },
                label = { Text("Ingreso mensual") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = basicos,
                onValueChange = { basicos = it },
                label = { Text("Gastos basicos") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = personales,
                onValueChange = { personales = it },
                label = { Text("Gastos personales") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ahorro,
                onValueChange = { ahorro = it },
                label = { Text("Meta de ahorro") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {viewModel.calcular(ingreso, basicos, personales, ahorro)}) {
                Text("Calcular presupuesto")
            }
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Dinero restante")
                    Text("$" + String.format("%.2f", restante))
                    Text(mensaje)
                }
            }
        }
    }
}