package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
// import androidx.compose.foundation.layout.width // No se usa directamente, pero es bueno tenerlo cerca de Row
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember // IMPORTANTE para recordar el timestamp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Función helper para formatear el timestamp
@Composable
private fun formatTimestamp(timestamp: Long?): String {
    if (timestamp == null) return "N/A" // Aunque aquí siempre pasaremos un Long no nulo
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val netDate = Date(timestamp)
        sdf.format(netDate)
    } catch (e: Exception) {
        "Fecha inválida"
    }
}

// Composable reutilizable para mostrar un tipo de cambio
@Composable
fun ExchangeRateRow(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value ?: "N/A",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun DollarScreen(viewModelDollar: DollarViewModel = koinViewModel()) {
    val state = viewModelDollar.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (val stateValue = state.value) {
            is DollarViewModel.DollarUIState.Error -> Text(
                text = stateValue.message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )

            DollarViewModel.DollarUIState.Loading -> CircularProgressIndicator()

            is DollarViewModel.DollarUIState.Success -> {
                // El timestamp se recordará y solo se recalculará si stateValue.data (o cualquier parte de Success) cambia.
                val displayTimestamp = remember(stateValue) { System.currentTimeMillis() }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        ExchangeRateRow(
                            label = "Oficial:",
                            value = stateValue.data.dollarOfficial
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))

                        ExchangeRateRow(
                            label = "Paralelo:",
                            value = stateValue.data.dollarParallel
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))

                        ExchangeRateRow(
                            label = "USDT:",
                            value = stateValue.data.dollarUSDT
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))

                        ExchangeRateRow(
                            label = "USDC:",
                            value = stateValue.data.dollarUSDC
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Consultado a las: ${formatTimestamp(displayTimestamp)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}