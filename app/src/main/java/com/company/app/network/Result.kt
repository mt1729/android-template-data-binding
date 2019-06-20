package com.company.app.network

sealed class Result<T> {
    data class Success<T>(val item: T): Result<T>()
    data class Failure<T>(val statusCode: Int): Result<T>()
    data class Error<T>(val error: Throwable): Result<T>()
}