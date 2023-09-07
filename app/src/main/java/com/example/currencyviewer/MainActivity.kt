package com.example.currencyviewer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyviewer.network.data.Rate
import com.example.currencyviewer.ui.theme.CurrencyViewerTheme
import com.example.currencyviewer.viewmodel.CurrencyListViewModel
import com.example.currencyviewer.viewmodel.RateApiState

class MainActivity : ComponentActivity() {

    private val viewModel: CurrencyListViewModel by viewModels { CurrencyListViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (val uiState = viewModel.rateList.observeAsState().value) {
                        is RateApiState.Success -> {
                            RateListView(rateList = uiState.rateList)
                        }

                        is RateApiState.Error -> {
                            Toast.makeText(
                                LocalContext.current,
                                "No data received from api",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun RateListView(rateList: List<Rate>, modifier: Modifier = Modifier) {
    print(rateList)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RateListView(emptyList())
}