package org.example.rinhabackend2024kotlinweb

data class CreateTransactionRequest(
    val valor: Number,
    val tipo: String,
    val descricao: String?
) : TransactionRequest {
    override fun fetchAmount(): Long {
        return when (valor) {
            is Double -> if (valor.compareTo(valor.toInt()) == 0) valor.toLong() else throw InvalidInputException("Value invalid")
            is Int -> valor.toLong()
            else -> throw InvalidInputException("Value invalid")
        }
    }

    override fun fetchType(): String {
        if (tipo.uppercase() == "C" || tipo.uppercase() == "D") return tipo.uppercase()
        throw InvalidInputException("Type transaction is invalid")
    }

    override fun fetchDescription(): String {
        if (descricao.isNullOrEmpty() || descricao.length > 10) throw InvalidInputException("Description very large or null")
        return descricao
    }
}
