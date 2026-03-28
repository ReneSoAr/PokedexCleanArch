package com.example.jetpackcomposepokedex.domain.error

/**
 * Sealed class representing domain-level errors.
 * Extends Throwable to work with Kotlin's Result type.
 * Used across all layers for type-safe error handling.
 */
sealed class DomainError(override val message: String) : Throwable(message) {

    data class NetworkError(val errorMessage: String) : DomainError(errorMessage)
    
    data class NotFound(val id: String) : DomainError("Pokemon with id '$id' not found")
    
    data class Unknown(val errorMessage: String) : DomainError(errorMessage)
}


