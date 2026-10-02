package com.MyApp.mipresupuesto.presupuestoVM.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PresupuestoViewModel: ViewModel() {

    private var _totalGastos = MutableStateFlow(0.0)
    val totalGastos : StateFlow<Double> = _totalGastos.asStateFlow()
    private var _restante = MutableStateFlow(0.0)
    val restante : StateFlow<Double> = _restante.asStateFlow()
    private var _mensaje = MutableStateFlow("Escribe tus datos: ")
    val mensaje : StateFlow<String> = _mensaje.asStateFlow()


    fun calcular (ingreso: String, basicos: String, personales: String, ahorro: String) {

        val ingresoValor = ingreso.toDoubleOrNull()
        val basicosValor = basicos.toDoubleOrNull()
        val personalesValor = personales.toDoubleOrNull()
        val ahorroValor = ahorro.toDoubleOrNull()

        if (ingresoValor == null || basicosValor == null || personalesValor == null || ahorroValor == null) {
            _mensaje.value = "Ingresa numeros"
        } else {
            val total = basicosValor + personalesValor
            val resto = ingresoValor - total - ahorroValor

            _totalGastos.value = total
            _restante.value = resto

            if (resto < 0) {
                _mensaje.value = "Te pasaste de tu ingreso"
            } else if (resto < ingresoValor * 0.1) {
                _mensaje.value = "Te queda poco dinero"
            } else {
                _mensaje.value = "Presupuesto equilibrado :)"
            }
        }
    }
}