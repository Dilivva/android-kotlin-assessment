package com.solt.deliveryweatherinsighttest.data.remote.model.util

sealed interface OperationResult  {
    class Failure(val e :Exception):OperationResult
    class Success<T>(val data : T):OperationResult
}