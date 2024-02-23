package org.example.rinhabackend2024kotlinweb

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.NEVER)
class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    @Transactional
    fun movementAccount(request: TransactionRequest, customerId: Int): Map<String, Any?> {
        if (customerId < 0 || customerId > 5) throw CustomerNotFoundException("customer not found")

        val amount = if (request.fetchType() == "C") request.fetchAmount() else -request.fetchAmount()

        val result = accountRepository.updateBalance(amount, customerId)

        if (result.first < 1) throw BalanceInconsistencyException("balance inconsistency")

        transactionRepository.createTransaction(
            Transaction(
                amount = request.fetchAmount(),
                type = request.fetchType(),
                description = request.fetchDescription(),
                limit = result.third as Int,
                balance = result.second as Int
            ), customerId = customerId
        )

        return mapOf("limite" to result.third, "saldo" to result.second)
    }
}
