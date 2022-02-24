package com.example.lbc.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MainUseCase<T> {
    val result: StateFlow<T>

}