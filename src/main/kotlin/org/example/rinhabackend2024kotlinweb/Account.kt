package org.example.rinhabackend2024kotlinweb

import com.fasterxml.jackson.annotation.JsonProperty

data class Account(
    @JsonProperty("limite") val accountLimit: Long,
    @JsonProperty("saldo") var balance: Long = 0
)
