package org.example.rinhabackend2024kotlinweb

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(propagation = Propagation.NEVER)
class FetchStatementAccountUseCase(
    private val transactionRepository: TransactionRepository
) {
    val mapCustomersLimit = mapOf(1 to 1000 * 100, 2 to 800 * 100, 3 to 10000 * 100, 4 to 100000 * 100, 5 to 5000 * 100)

    fun statement(customerId: Int): Statement {
        if (customerId < 0 || customerId > 5) throw CustomerNotFoundException("customer not found")

        val transactions = transactionRepository.fetchLastTenTransactions(customerId)

        return if (transactions.isEmpty()) {
            val limit = mapCustomersLimit[customerId] ?: 0

            Statement(
                balance = StatementBalance(
                    limit = limit,
                    total = 0,
                    consultAt = LocalDateTime.now()
                ),
                lastTransactions = emptyList()
            )
        } else {
            Statement(
                balance = StatementBalance(
                    limit = transactions[0].limit,
                    total = transactions[0].balance,
                    consultAt = LocalDateTime.now()
                ),
                lastTransactions = transactions
                    .map {
                        StatementResumeTransaction(
                            value = it.amount,
                            type = it.type.lowercase(),
                            description = it.description,
                            transactionCreatedAt = it.createdAt
                        )
                    }
            )
        }
    }
}
