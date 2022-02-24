package com.example.lbc.common

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher
)
