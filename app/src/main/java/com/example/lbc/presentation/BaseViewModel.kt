package com.example.lbc.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lbc.common.AppDispatchers
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<A : BaseAction, S : BaseState>(private val dispatchers: AppDispatchers, private val initialState: S) : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    private val _state = MutableLiveData<S>().apply { value = initialState }

    val state = _state as LiveData<S>

    override val coroutineContext: CoroutineContext = dispatchers.computation + job + CoroutineExceptionHandler { _, exception ->
        Log.e("TAG", exception.toString())
    }

    protected abstract suspend fun onHandle(action: A)

    fun handle(action: A) = launch {
        onHandle(action)
    }

    protected suspend fun updateState(reducer: (S) -> S) = withContext(dispatchers.main) {
        val currentState = state.value ?: initialState
        _state.value = reducer(currentState)
    }

    override fun onCleared() {
        coroutineContext.cancelChildren()
    }

}

interface BaseAction
interface BaseState
