package com.pfmiranda.redux2.redux

import android.util.Log

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

fun loggerMiddleware(state: AppState, action: Action, dispatch: Dispatch, next: Next<AppState>): Action {
    Log.d("middleware", "action in <-- $action")
    val newAction = next(state, action, dispatch)
    Log.d("middleware", "action out --> $newAction")
    return newAction
}

fun magicMiddleware(state: AppState, action: Action, dispatch: Dispatch, next: Next<AppState>): Action {
    return when (action) {
        is NewData -> {
            return if (action.d == "aaaa") {
                ClearData
            } else {
                action
            }
        }
        else -> action
    }
}

class AppStore: SimpleStore<AppState>(
    initialState = AppState(),
    reducers = listOf(::reduceDataState),
    middleware = listOf(::loggerMiddleware, ::magicMiddleware)) {
    companion object {
        val instance by lazy {
            AppStore()
        }
    }
}