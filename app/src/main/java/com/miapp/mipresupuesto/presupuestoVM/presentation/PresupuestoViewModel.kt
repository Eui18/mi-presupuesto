package com.miapp.mipresupuesto.presupuestoVM.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PresupuestoViewModel : ViewModel() {
    private var _ingreso = MutableStateFlow("");
    val ingreso: StateFlow<String> = _ingreso.asStateFlow()
    private var _vivienda = MutableStateFlow("")
    val vivienda: StateFlow<String> = _vivienda.asStateFlow()
    private var _disponible = MutableStateFlow(0.0)
    val disponible: StateFlow<Double> = _disponible.asStateFlow()

    fun cambiarIngreso (valorIngreso: String) {
        _ingreso.value = valorIngreso
    }

    fun cambiarVivienda (valorVivienda: String) {
        _vivienda.value = valorVivienda
    }

    fun agregarIngreso () {
        val cantidad = ingreso.value.toDoubleOrNull()

        if (cantidad != null) {
            _disponible.value = disponible.value + cantidad
        }
    }
}