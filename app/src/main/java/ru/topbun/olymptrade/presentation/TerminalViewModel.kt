package ru.topbun.olymptrade.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.olymptrade.data.ApiFactory

class TerminalViewModel: ViewModel() {

    private val apiService = ApiFactory.apiService

    private val _state = MutableStateFlow<TerminalScreenState>(TerminalScreenState.Initial)
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler{_, throwable ->
        Log.e("TerminalViewModel", "Exception caught: ${throwable.message}")
    }

    private fun getBars() = viewModelScope.launch(exceptionHandler) {
        val result = apiService.loadBars().result
        _state.value = TerminalScreenState.Result(result)
    }

    init {
        getBars()
    }

}