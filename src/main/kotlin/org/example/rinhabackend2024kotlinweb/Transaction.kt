package org.example.rinhabackend2024kotlinweb

import java.time.LocalDateTime
import java.util.*

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val amount: Long,
    val type: String,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val limit: Int,
    val balance: Int
)
