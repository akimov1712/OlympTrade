package ru.topbun.olymptrade.presentation

import ru.topbun.olymptrade.data.Bar

sealed class TerminalState {

    data object Initial: TerminalState()
    data class Result(val result: List<Bar>): TerminalState()

}