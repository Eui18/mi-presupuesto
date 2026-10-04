package com.miapp.mipresupuesto.presupuestoVM.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PresupuestoViewModel : ViewModel() {
    private var _ingreso = MutableStateFlow("");
    val ingreso: StateFlow<String> = _ingreso.asStateFlow()

    fun cambiarIngreso (valor: String) {
        _ingreso.value = valor
    }
}