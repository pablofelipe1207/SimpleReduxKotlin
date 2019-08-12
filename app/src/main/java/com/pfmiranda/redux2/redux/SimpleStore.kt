package com.pfmiranda.redux2.redux

abstract class SimpleStore<S>(
    private val initialState: S,
    private val reducers: List<Reducer<S>>,
    private val middleware: List<Middleware<S>>): Store<S> {

    private var currentState: S = initialState
    private val subscriptions = arrayListOf<Subscription<S>>()

    override fun getState() = currentState

    override fun dispatch(action: Action) {
        val newAction = applyMiddleware(currentState, action)
        val newState = applyReducers(currentState, newAction)
        if (newState == currentState) {
            return
        }
        currentState = newState
        subscriptions.forEach { it(currentState, ::dispatch) }
    }

    private fun applyMiddleware(state: S, action: Action): Action {
        return next(0)(state, action, ::dispatch)
    }

    private fun next(index: Int): Next<S> {
        if (index == middleware.size) {
            return { s, action, function -> action }
        }
        return { state, action, dispatch -> middleware[index].invoke(state, action, dispatch, next(index+1)) }
    }

    private fun applyReducers(current: S, action: Action): S {
        var newState = current
        for (reducer in reducers) {
            newState = reducer(newState, action)
        }
        return newState
    }

    override fun subscribe(subscription: Subscription<S>): Unsubscribe {
        subscriptions.add(subscription)
        subscription(currentState, ::dispatch)
        return { subscriptions.remove(subscription) }
    }

}