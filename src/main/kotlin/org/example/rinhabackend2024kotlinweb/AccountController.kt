package org.example.rinhabackend2024kotlinweb

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/clientes"])
class AccountController(
    private val createTransactionUseCase: CreateTransactionUseCase
) {
    @PostMapping(path = ["/{customerId}/transacoes"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createTransaction(@PathVariable customerId: Int, @RequestBody payload: CreateTransactionRequest): ResponseEntity<Map<String, Any?>> {
        return ResponseEntity.ok(createTransactionUseCase.movementAccount(payload, customerId))
    }

    @GetMapping(path = ["/{customerId}/extrato"])
    fun fetchStatementByCustomerId(@PathVariable customerId: Int): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(createTransactionUseCase.statement(customerId))
    }
}
