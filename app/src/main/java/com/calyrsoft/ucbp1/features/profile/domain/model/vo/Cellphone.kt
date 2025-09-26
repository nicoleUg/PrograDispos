package com.calyrsoft.ucbp1.features.profile.domain.model.vo

@JvmInline
value class Cellphone private constructor(val value: String) {
    companion object {
        // Acepta formato internacional (+5917xxxxxxx) o solo dígitos 7–15
        private val REGEX = Regex("""^\+?[0-9]{7,15}$""")
        fun create(raw: String): Cellphone {
            val v = raw.trim().replace(" ", "")
            require(REGEX.matches(v)) { "Celular inválido (usa solo dígitos, opcional +código país)" }
            return Cellphone(v)
        }
    }
}