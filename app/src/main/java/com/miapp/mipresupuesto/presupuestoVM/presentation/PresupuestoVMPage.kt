package com.miapp.mipresupuesto.presupuestoVM.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miapp.mipresupuesto.ui.theme.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresupuestoVMPage(viewModel: PresupuestoViewModel = viewModel()) {

    var ingreso by rememberSaveable { mutableStateOf("") }
    var basicos by rememberSaveable { mutableStateOf("") }
    var personales by rememberSaveable { mutableStateOf("") }
    var porcentajeAhorro by rememberSaveable { mutableFloatStateOf(10f) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Colores del estado inicial
    var colorTarjeta = MaterialTheme.colorScheme.primaryContainer
    var colorBarra = MaterialTheme.colorScheme.primary

    if (uiState.excedido) {
        colorTarjeta = MaterialTheme.colorScheme.errorContainer
        colorBarra = MaterialTheme.colorScheme.error
    } else if (uiState.pocoDinero) {
        colorTarjeta = TarjetaAtencion
        colorBarra = BarraAtencion
    } else if (uiState.calculado) {
        colorTarjeta = TarjetaEquilibrio
        colorBarra = BarraEquilibrio
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Mi presupuesto") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CampoDinero("Ingreso mensual", ingreso) {
                        ingreso = it
                        viewModel.calcular(ingreso, basicos, personales, porcentajeAhorro)
                    }
                    CampoDinero("Gastos básicos", basicos) {
                        basicos = it
                        viewModel.calcular(ingreso, basicos, personales, porcentajeAhorro)
                    }
                    CampoDinero("Gastos personales", personales) {
                        personales = it
                        viewModel.calcular(ingreso, basicos, personales, porcentajeAhorro)
                    }
                    Text("Ahorro: " + String.format(Locale.getDefault(), "%.0f", porcentajeAhorro) + "% de tu ingreso")
                    Slider(
                        value = porcentajeAhorro,
                        onValueChange = {
                            porcentajeAhorro = it
                            viewModel.calcular(ingreso, basicos, personales, porcentajeAhorro)
                        },
                        valueRange = 0f..50f,
                        steps = 49
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colorTarjeta,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (uiState.calculado) {
                        Text("Dinero restante", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "$" + String.format(Locale.getDefault(), "%.2f", uiState.restante),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(uiState.mensaje)
                        Text("Puedes gastar al día: $" + String.format(Locale.getDefault(), "%.2f", uiState.gastoDiario))
                        Text("Ingreso comprometido: " + String.format(Locale.getDefault(), "%.0f", uiState.porcentaje) + "%")
                        LinearProgressIndicator(
                            progress = { (uiState.porcentaje / 100).toFloat().coerceIn(0f, 1f) },
                            color = colorBarra,
                            trackColor = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(uiState.mensaje, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun CampoDinero(etiqueta: String, valor: String, alCambiar: (String) -> Unit) {
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}