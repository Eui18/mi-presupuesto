package com.miapp.mipresupuesto.presupuestoVM.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PresupuestoViewModel : ViewModel() {
    private var _ingreso = MutableStateFlow("")
    val ingreso: StateFlow<String> = _ingreso.asStateFlow()
    private var _vivienda = MutableStateFlow("")
    val vivienda: StateFlow<String> = _vivienda.asStateFlow()
    private var _disponible = MutableStateFlow(0.0)
    val disponible: StateFlow<Double> = _disponible.asStateFlow()
    private var _comida = MutableStateFlow("")
    val comida: StateFlow<String> = _comida.asStateFlow()
    private var _ocio = MutableStateFlow("")
    val ocio: StateFlow<String> = _ocio.asStateFlow()
    private var _compras = MutableStateFlow("")
    val compras: StateFlow<String> = _compras.asStateFlow()
    private var _gastoTotal = MutableStateFlow(0.0)
    val gastoTotal: StateFlow<Double> = _gastoTotal.asStateFlow()
    private var _restante = MutableStateFlow(0.0)
    val restante: StateFlow<Double> = _restante.asStateFlow()
    private var _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()
    private var _porcentajeAhorro = MutableStateFlow(0f)
    val porcentajeAhorro: StateFlow<Float> = _porcentajeAhorro.asStateFlow()
    private var _ahorro = MutableStateFlow(0.0)
    val ahorro: StateFlow<Double> = _ahorro.asStateFlow()
    fun cambiarIngreso (valorIngreso: String) {
        _ingreso.value = valorIngreso
    }
    fun cambiarVivienda (valorVivienda: String) {
        _vivienda.value = valorVivienda
    }
    fun cambiarComida (valorComida: String) {
        _comida.value = valorComida
    }
    fun cambiarOcio (valorOcio: String) {
        _ocio.value = valorOcio
    }
    fun cambiarCompras (valorCompras: String) {
        _compras.value = valorCompras
    }
    fun cambiarPorcentajeAhorro (valorPorcentajeAhorro: Float) {
        _porcentajeAhorro.value = valorPorcentajeAhorro
    }
    fun agregarIngreso () {
        val cantidad = ingreso.value.toDoubleOrNull()

        if (cantidad != null && cantidad > 0) {
            _disponible.value = disponible.value + cantidad
            _ingreso.value = ""
            _mensaje.value = "Ingresado correctamente"
        } else {
            _mensaje.value = "Ingresa una cantidad válida"
        }
    }
    fun calcularTotalGastos () {
        val home = vivienda.value.toDoubleOrNull() ?: 0.0
        val eat = comida.value.toDoubleOrNull() ?: 0.0
        val leisure = ocio.value.toDoubleOrNull() ?: 0.0
        val buy = compras.value.toDoubleOrNull() ?: 0.0

        _gastoTotal.value = home + eat + leisure + buy
    }
    fun calcularPresupuesto (): Boolean {
        calcularTotalGastos()
        calcularAhorro()
        _restante.value = disponible.value - gastoTotal.value - ahorro.value

        if (disponible.value > 0 && restante.value >= 0) {
            _mensaje.value = "Tu presupuesto es posible"
            return true
        } else {
            _mensaje.value = "Revisa tus gastos o tu dinero disponible"
            return false
        }
    }
    fun calcularAhorro () {
        _ahorro.value = disponible.value * porcentajeAhorro.value.toDouble() / 100
    }

    fun aplicarPresupuesto () {
        if (calcularPresupuesto()) {
            _disponible.value = restante.value
            _mensaje.value = "Presupuesto aplicado correctamente"
        }
    }
    fun obtenerMayorGasto(): Double{

        val gastos = listOf(
            vivienda.value.toDoubleOrNull() ?: 0.0,
            comida.value.toDoubleOrNull() ?: 0.0,
            ocio.value.toDoubleOrNull() ?: 0.0,
            compras.value.toDoubleOrNull() ?: 0.0
        )
        return gastos.maxOrNull() ?: 0.0
    }
    fun obtenerProporcion (gasto: String): Float {
        val valor = gasto.toDoubleOrNull() ?: 0.0
        val mayorGasto = obtenerMayorGasto()

        if (mayorGasto == 0.0) {
            return 0f
        }
        return (valor / mayorGasto).toFloat()
    }
}