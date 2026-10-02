package com.miapp.mipresupuesto.presupuestoVM.presentation
data class BudgetUiState(
    val totalGastos: Double = 0.0,
    val montoAhorro: Double = 0.0,
    val restante: Double = 0.0,
    val gastoDiario: Double = 0.0,
    val porcentaje: Double = 0.0,
    val mensaje: String = "Ingresa tus datos para empezar",
    val calculado: Boolean = false,
    val pocoDinero: Boolean = false,
    val excedido: Boolean = false,
)