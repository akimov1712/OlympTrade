package ru.topbun.olymptrade.presentation

import ru.topbun.olymptrade.data.Bar

sealed class TerminalScreenState {

    data object Initial: TerminalScreenState()
    data class Result(val result: List<Bar>): TerminalScreenState()

}