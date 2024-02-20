package org.example.rinhabackend2024kotlinweb

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AccountHandlerController {
    @ExceptionHandler(CustomerNotFoundException::class)
    fun onCustomerNotFoundException(error: CustomerNotFoundException): ResponseEntity<Unit> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(BalanceInconsistencyException::class, InvalidInputException::class)
    fun onBalanceInconsistencyException(error: Exception): ResponseEntity<Unit> {
        return ResponseEntity.unprocessableEntity().build()
    }
}
