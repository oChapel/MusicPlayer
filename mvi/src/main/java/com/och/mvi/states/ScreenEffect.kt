package com.och.mvi.states

interface ScreenEffect<T> {
    fun visit(screen: T)
}
