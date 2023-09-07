package com.example.currencyviewer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.currencyviewer.domain.RateApiState
import com.example.currencyviewer.network.RatesApiClient
import com.example.currencyviewer.network.data.Rate
import com.example.currencyviewer.ui.theme.CurrencyViewerTheme
import com.example.currencyviewer.viewmodel.CurrencyListViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CurrencyNavHost()
                }
            }
        }
    }
}

@Composable
fun CurrencyNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "listView"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            "listview"
        ) {
            val viewModel = CurrencyListViewModel(apiClient = RatesApiClient())
            CurrencyListView(navController = navController, viewModel = viewModel)
        }
        composable(
            "detailview?id={id},symbol={symbol},currencySymbol={currencySymbol},rateUsd={rateUsd},type={type}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("symbol") { type = NavType.StringType },
                navArgument("currencySymbol") { type = NavType.StringType },
                navArgument("rateUsd") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
            val currencySymbol = backStackEntry.arguments?.getString("currencySymbol")
            val rateUsd = backStackEntry.arguments?.getString("rateUsd") ?: ""
            val type = backStackEntry.arguments?.getString("type") ?: ""
            CurrencyDetailView(Rate(id, symbol, currencySymbol, rateUsd, type))
        }
    }
}

@Composable
fun CurrencyListView(
    navController: NavHostController = rememberNavController(),
    viewModel: CurrencyListViewModel
) {
    when (val uiState = viewModel.rateList.observeAsState().value) {
        is RateApiState.Success -> {
            LazyColumn {
                items(uiState.rateList) {
                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("detailview?" +
                                    "id=${it.id}," +
                                    "symbol=${it.symbol}," +
                                    "currencySymbol=${it.currencySymbol ?: ""}," +
                                    "rateUsd=${it.rateUsd}," +
                                    "type=${it.type}")
                        }) {
                        CurrencyListItem(rate = it)
                    }
                }
            }
        }

        is RateApiState.Error -> {
            Toast.makeText(
                LocalContext.current,
                "No data received from api",
                Toast.LENGTH_SHORT
            ).show()
        }
        is RateApiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> {}
    }
}

@Composable
fun CurrencyListItem(rate: Rate) {
    Column(Modifier.padding(16.dp)) {
        Text(text = rate.symbol)
        Text(text = rate.currencySymbol ?: "N/A")
        Text(text = rate.rateUsd)
    }
}

@Composable
fun CurrencyDetailView(rate: Rate) {
    Column(
        Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = rate.id)
        Text(text = rate.symbol)
        Text(text = rate.currencySymbol ?: "N/A")
        Text(text = rate.rateUsd)
        Text(text = rate.type)
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyListViewPreview() {
    CurrencyListView(viewModel = CurrencyListViewModel(RatesApiClient()))
}