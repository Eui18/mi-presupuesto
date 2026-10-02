package com.MyApp.mipresupuesto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.MyApp.mipresupuesto.presupuestoVM.presentation.PresupuestoVMPage
import com.MyApp.mipresupuesto.ui.theme.MiPresupuestoTheme

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