package org.example.rinhabackend2024kotlinweb

import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository

@Repository
class AccountPostgresRepository(
    private val jdbcClient: JdbcClient
) : AccountRepository {
    override fun updateBalance(amount: Long, customerId: Int): Triple<Int, Any?, Any?> {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val rowsUpdated =
            this.jdbcClient.sql("update account set balance = balance + :amount where customer_id = :customerId and balance + :amount + account_limit > 0")
                .params(mapOf("amount" to amount, "customerId" to customerId))
                .update(keyHolder, "balance", "account_limit")
        return Triple(rowsUpdated, keyHolder.keys?.get("balance"), keyHolder.keys?.get("account_limit"))
    }
}
