package org.example.rinhabackend2024kotlinweb

import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository

@Repository
class TransactionPostgresRepository(
    private val jdbcClient: JdbcClient
) : TransactionRepository {
    override fun createTransaction(transaction: Transaction, customerId: Int) {
        this.jdbcClient.sql("insert into transactions (id, customer_id, type, amount, description, created_at) values (:id, :customerId, :type, :amount, :description, :createdAt)")
            .params(
                mapOf(
                    "id" to transaction.id,
                    "customerId" to customerId,
                    "type" to transaction.type,
                    "amount" to transaction.amount,
                    "description" to transaction.description,
                    "createdAt" to transaction.createdAt
                )
            )
            .update()
    }

    override fun statement(customerId: Int): MutableList<Statement> {
        return this.jdbcClient.sql(
            """
            select
                A.account_limit as "limit",
                A.balance,
                T.id,
                T.customer_id as customerId,
                T.type,
                T.amount,
                T.description,
                T.created_at as transactionCreatedAt
            from account A
            left join transactions T on T.customer_id = A.customer_id
            where A.customer_id = :customerId
            order by T.created_at desc limit 10
        """.trimIndent()
        )
            .param("customerId", customerId)
            .query(Statement::class.java)
            .list()
    }
}
