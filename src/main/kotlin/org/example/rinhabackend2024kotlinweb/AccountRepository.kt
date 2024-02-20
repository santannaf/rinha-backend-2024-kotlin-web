package org.example.rinhabackend2024kotlinweb

interface AccountRepository {
    fun fetchAccountByCustomerId(customerId: Int): Account
    fun updateBalance(amount: Long, customerId: Int): Triple<Int, Any?, Any?>
}
