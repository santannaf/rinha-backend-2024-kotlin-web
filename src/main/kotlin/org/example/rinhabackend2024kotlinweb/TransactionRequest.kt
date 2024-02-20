package org.example.rinhabackend2024kotlinweb

interface TransactionRequest {
    fun fetchAmount(): Long
    fun fetchType(): String
    fun fetchDescription(): String
}
