package com.och.mvi.states

interface ScreenState<T, S> {
    fun visit(screen: T)

    fun merge(prevState: S): S
}
