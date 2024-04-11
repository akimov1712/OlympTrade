package ru.topbun.olymptrade.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.topbun.olymptrade.ui.theme.OlympTradeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OlympTradeTheme {
                val viewModel = viewModel<TerminalViewModel>()
                val screenState = viewModel.state.collectAsState()
                when(val currentState = screenState.value){
                    is TerminalScreenState.Initial -> {}
                    is TerminalScreenState.Result -> {
                        Terminal(currentState.result)
                    }
                }
            }
        }
    }
}
