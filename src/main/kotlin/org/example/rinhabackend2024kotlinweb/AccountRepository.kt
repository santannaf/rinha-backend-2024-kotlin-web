package org.example.rinhabackend2024kotlinweb

interface AccountRepository {
    fun updateBalance(amount: Long, customerId: Int): Triple<Int, Any?, Any?>
}
