package com.MyApp.mipresupuesto.presupuestoVM.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresupuestoVMPage(viewModel: PresupuestoViewModel = viewModel()) {

    var ingreso by rememberSaveable { mutableStateOf("") }
    var basicos by rememberSaveable { mutableStateOf("") }
    var personales by rememberSaveable { mutableStateOf("") }
    var porcentajeAhorro by rememberSaveable { mutableFloatStateOf(10f) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var colorTarjeta = Color(0xFFC8E6C9)
    var colorBarra = Color(0xFF43A047)

    if (!uiState.calculado) {
        colorTarjeta = Color(0xFFEDE7F6)
        colorBarra = Color(0xFF9575CD)
    } else if (uiState.porcentaje > 100) {
        colorTarjeta = Color(0xFFFFCDD2)
        colorBarra = Color(0xFFE53935)
    } else if (uiState.porcentaje > 90) {
        colorTarjeta = Color(0xFFFFF9C4)
        colorBarra = Color(0xFFF9A825)
    }

    Scaffold(
        containerColor = Color(0xFFF6FBF8),
        topBar = {
            TopAppBar(
                title = { Text("Mi presupuesto") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD1C4E9),
                    titleContentColor = Color(0xFF4527A0)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "¡Vamos a organizar tu dinero!",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF4527A0)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CampoDinero(
                        etiqueta = "Ingreso mensual",
                        valor = ingreso,
                        onValueChange = {
                            ingreso = it
                            viewModel.calcular(ingreso, basicos, personales, porcentajeAhorro)
                        }
                    )
                    CampoDinero(
                        etiqueta = "Gastos básicos",
                        valor = basicos,
                        onValueChange = {
                            basicos = it
                            viewModel.calcular(ingreso, basicos, personales, porcentajeAhorro)
                        }
                    )
                    CampoDinero(
                        etiqueta = "Gastos personales",
                        valor = personales,
                        onValueChange = {
                            personales = it
                            viewModel.calcular(ingreso, basicos, personales, porcentajeAhorro)
                        }
                    )
                    Text("Ahorro: " + String.format("%.0f", porcentajeAhorro) + "% de tu ingreso")
                    Slider(
                        value = porcentajeAhorro,
                        onValueChange = {
                            porcentajeAhorro = it
                            viewModel.calcular(ingreso, basicos, personales, porcentajeAhorro)
                        },
                        valueRange = 0f..50f
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colorTarjeta,
                    contentColor = Color.Black
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Dinero restante", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "$" + String.format("%.2f", uiState.restante),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(uiState.mensaje)
                    Text("Puedes gastar al día: $" + String.format("%.2f", uiState.gastoDiario))
                    Text("Ingreso comprometido: " + String.format("%.0f", uiState.porcentaje) + "%")
                    LinearProgressIndicator(
                        progress = { (uiState.porcentaje / 100).toFloat().coerceIn(0f, 1f) },
                        color = colorBarra,
                        trackColor = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun CampoDinero(etiqueta: String, valor: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        label = { Text(etiqueta) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}