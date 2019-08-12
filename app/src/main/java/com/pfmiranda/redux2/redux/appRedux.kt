package com.pfmiranda.redux2.redux

data class NewData(val d: String): Action
object ClearData: Action

data class AppState (val data: String = "Inicio") : State

fun reduceDataState(state: AppState, action: Action): AppState {
    return when(action) {
        is ClearData -> state.copy(data = "")
        is NewData -> state.copy(data = action.d)
        else -> state
    }
}

class AppStore: SimpleStore<AppState>(
    initialState = AppState(),
    reducers = listOf(::reduceDataState)) {
    companion object {
        val instance by lazy {
            AppStore()
        }
    }
}