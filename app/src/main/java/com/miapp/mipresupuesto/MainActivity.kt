package com.miapp.mipresupuesto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.miapp.mipresupuesto.presupuestoVM.presentation.PresupuestoVMPage
import com.miapp.mipresupuesto.ui.theme.MiPresupuestoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiPresupuestoTheme {
                PresupuestoVMPage()
            }
        }
    }
}