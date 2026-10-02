package com.MyApp.mipresupuesto.presupuestoVM.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PresupuestoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    fun calcular(ingreso: String, basicos: String, personales: String, porcentajeAhorro: Float) {
        val ingresoNum = if (ingreso.isBlank()) 0.0 else ingreso.toDoubleOrNull()
        val basicosNum = if (basicos.isBlank()) 0.0 else basicos.toDoubleOrNull()
        val personalesNum = if (personales.isBlank()) 0.0 else personales.toDoubleOrNull()

        if (ingresoNum == null || basicosNum == null || personalesNum == null) {
            _uiState.value = BudgetUiState(mensaje = "Ingresa solo números")
        } else if (ingresoNum < 0 || basicosNum < 0 || personalesNum < 0) {
            _uiState.value = BudgetUiState(mensaje = "Ingresa solo números positivos")
        } else if (ingresoNum == 0.0) {
            _uiState.value = BudgetUiState()
        } else {
            val total = basicosNum + personalesNum
            val ahorro = ingresoNum * porcentajeAhorro / 100
            val resto = ingresoNum - total - ahorro
            val porcentaje = (total + ahorro) / ingresoNum * 100
            var gastoDiario = 0.0
            if (resto > 0) {
                gastoDiario = resto / 30
            }
            var mensaje = "Tu presupuesto está en equilibrio"
            if (resto < 0) {
                mensaje = "Te pasaste de tu ingreso"
            } else if (resto < ingresoNum * 0.1) {
                mensaje = "Te queda poco dinero"
            }
            _uiState.value = BudgetUiState(total, ahorro, resto, gastoDiario, porcentaje, mensaje, true)
        }
    }
}