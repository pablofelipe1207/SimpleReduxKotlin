package com.pfmiranda.redux2.redux

interface State
interface Action
typealias Dispatch = (Action) -> Unit
typealias Reducer<S> = (S, Action) -> S
typealias Subscription<S> = (S,Dispatch) -> Unit
typealias Unsubscribe = () -> Unit

typealias Next<S> = (S, Action, Dispatch) -> Action
typealias Middleware<S> = (S, Action, Dispatch, Next<S>) -> Action



interface Store<S> {
    fun dispatch(action: Action)
    fun subscribe(subscription: Subscription<S>): Unsubscribe
    fun getState(): S
}