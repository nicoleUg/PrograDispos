package com.calyrsoft.ucbp1.features.profile.domain.model.vo

@JvmInline
value class Email private constructor(val value: String) {
    companion object {
        private val REGEX = Regex("""^[A-Za-z0-9._%+\-]+@[A-Za-z0-9.\-]+\.[A-Za-z]{2,}$""")
        fun create(raw: String): Email {
            val v = raw.trim()
            require(REGEX.matches(v)) { "Email inválido" }
            return Email(v)
        }
    }
}