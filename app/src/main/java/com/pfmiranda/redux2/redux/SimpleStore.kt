package com.pfmiranda.redux2.redux

abstract class SimpleStore<S>(
    private val initialState: S,
    private val reducers: List<Reducer<S>>): Store<S> {

    private var currentState: S = initialState
    private val subscriptions = arrayListOf<Subscription<S>>()

    override fun getState() = currentState

    override fun dispatch(action: Action) {
        val newState = applyReducers(currentState, action)
        if (newState == currentState) {
            return
        }
        currentState = newState
        subscriptions.forEach { it(currentState, ::dispatch) }
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