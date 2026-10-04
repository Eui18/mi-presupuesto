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
    private var _transporte = MutableStateFlow("")
    val transporte: StateFlow<String> = _transporte.asStateFlow()
    private var _servicios = MutableStateFlow("")
    val servicios: StateFlow<String> = _servicios.asStateFlow()
    private var _ropa = MutableStateFlow("")
    val ropa: StateFlow<String> = _ropa.asStateFlow()
    private var _ocio = MutableStateFlow("")
    val ocio: StateFlow<String> = _ocio.asStateFlow()
    private var _compras = MutableStateFlow("")
    val compras: StateFlow<String> = _compras.asStateFlow()
    private var _cuidadoPersonal = MutableStateFlow("")
    val cuidadoPersonal: StateFlow<String> = _cuidadoPersonal.asStateFlow()
    private var _gastoTotal = MutableStateFlow(0.0)
    val gastoTotal: StateFlow<Double> = _gastoTotal.asStateFlow()
    private var _restante = MutableStateFlow(0.0)
    val restante: StateFlow<Double> = _restante.asStateFlow()
    private var _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()
    fun cambiarIngreso (valorIngreso: String) {
        _ingreso.value = valorIngreso
    }
    fun cambiarVivienda (valorVivienda: String) {
        _vivienda.value = valorVivienda
    }
    fun cambiarComida (valorComida: String) {
        _comida.value = valorComida
    }
    fun cambiarTransporte (valorTransporte: String) {
        _transporte.value = valorTransporte
    }
    fun cambiarServicios (valorServicios: String ) {
        _servicios.value = valorServicios
    }
    fun cambiarRopa (valorRopa: String) {
        _ropa.value = valorRopa
    }
    fun cambiarOcio (valorOcio: String) {
        _ocio.value = valorOcio
    }
    fun cambiarCompras (valorCompras: String) {
        _compras.value = valorCompras
    }
    fun cambiarCuidadoPersonal (valorCuidadoPersonal: String) {
        _cuidadoPersonal.value = valorCuidadoPersonal
    }
    fun agregarIngreso () {
        val cantidad = ingreso.value.toDoubleOrNull()

        if (cantidad != null) {
            _disponible.value = disponible.value + cantidad
        }
    }

    fun calcularTotalGastos () {
        val home = vivienda.value.toDoubleOrNull() ?: 0.0
        val eat = comida.value.toDoubleOrNull() ?: 0.0
        val transport = transporte.value.toDoubleOrNull() ?: 0.0
        val server = servicios.value.toDoubleOrNull() ?: 0.0
        val clothes = ropa.value.toDoubleOrNull() ?: 0.0
        val leisure = ocio.value.toDoubleOrNull() ?: 0.0
        val buy = compras.value.toDoubleOrNull() ?: 0.0
        val personalCare = cuidadoPersonal.value.toDoubleOrNull() ?: 0.0

        _gastoTotal.value = home + eat + transport + server + clothes + leisure + buy + personalCare
    }

    fun descontarDineroDisponible () {
        _restante.value = disponible.value - gastoTotal.value
        _disponible.value = restante.value
    }

    fun validarPresupuesto () {
        calcularTotalGastos()
        if (disponible.value >= gastoTotal.value) {
            descontarDineroDisponible()
            _mensaje.value = "Presupuesto aplicado correctamente"
        } else {
            _mensaje.value = "Tus gastos superan tu dinero disponible"
        }
    }
}