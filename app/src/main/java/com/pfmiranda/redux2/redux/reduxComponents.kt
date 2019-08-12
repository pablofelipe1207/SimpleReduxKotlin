package com.pfmiranda.redux2.redux

interface State
interface Action
typealias Reducer<S> = (S, Action) -> S
typealias Subscription<S> = (S) -> Unit

interface Store<S> {
    fun dispatch(action: Action)
    fun unsubscribe(subscription: Subscription<S>)
    fun subscribe(subscription: Subscription<S>)
    fun getState(): S
}