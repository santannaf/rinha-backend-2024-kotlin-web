package org.example.rinhabackend2024kotlinweb

import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository

@Repository
class TransactionPostgresRepository(
    private val jdbcClient: JdbcClient
) : TransactionRepository {
    override fun createTransaction(transaction: Transaction, customerId: Int) {
        this.jdbcClient.sql("insert into transactions (id, customer_id, type, amount, description, created_at, account_limit, balance) values (:id, :customerId, :type, :amount, :description, :createdAt, :limit, :balance)")
            .params(
                mapOf(
                    "id" to transaction.id,
                    "customerId" to customerId,
                    "type" to transaction.type,
                    "amount" to transaction.amount,
                    "description" to transaction.description,
                    "createdAt" to transaction.createdAt,
                    "limit" to transaction.limit,
                    "balance" to transaction.balance,
                )
            )
            .update()
    }

    override fun fetchLastTenTransactions(customerId: Int): MutableList<Transaction> {
        return this.jdbcClient.sql("""select id, type, amount, description, created_At as "createdAt", account_limit as "limit", balance from transactions T where T.customer_id = :customerId order by created_at desc limit 10""")
            .param("customerId", customerId)
            .query(Transaction::class.java)
            .list()
    }
}
