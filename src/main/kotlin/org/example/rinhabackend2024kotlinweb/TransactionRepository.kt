package org.example.rinhabackend2024kotlinweb

interface TransactionRepository {
    fun createTransaction(transaction: Transaction, customerId: Int)
    fun statement(customerId: Int): MutableList<Statement>
}
