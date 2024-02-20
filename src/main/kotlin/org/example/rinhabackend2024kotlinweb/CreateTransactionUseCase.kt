package org.example.rinhabackend2024kotlinweb

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(propagation = Propagation.NEVER)
class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
) {
    fun movementAccount(request: TransactionRequest, customerId: Int): Map<String, Any?> {
        if (customerId < 0 || customerId > 5) throw CustomerNotFoundException("customer not found")

        val amount = if (request.fetchType() == "C") request.fetchAmount() else -request.fetchAmount()

        val result = accountRepository.updateBalance(amount, customerId)

        if (result.first < 1) throw BalanceInconsistencyException("balance inconsistency")

        transactionRepository.createTransaction(
            Transaction(
                amount = request.fetchAmount(),
                type = request.fetchType(),
                description = request.fetchDescription()
            ), customerId = customerId
        )

        return mapOf("limite" to result.third, "saldo" to result.second)
    }

    fun statement(customerId: Int): Map<String, Any>? {
        if (customerId < 0 || customerId > 5) throw CustomerNotFoundException("customer not found")

        val statement = transactionRepository.statement(customerId)

        if (statement.isEmpty()) return null

        val resume = object {
            val limite = statement[0].limit.toInt()
            val total = statement[0].balance.toInt()
            val data_extrato = LocalDateTime.now()
        }

        val response = mapOf("saldo" to resume, "ultimas_transacoes" to statement.map {
            object {
                val valor = it.amount
                val tipo = it.type?.lowercase()
                val descricao = it.description
                val realizada_em = it.transactionCreatedAt
            }
        })

        return response
    }
}
