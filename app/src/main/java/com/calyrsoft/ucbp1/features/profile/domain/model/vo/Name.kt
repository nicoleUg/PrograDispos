package com.calyrsoft.ucbp1.features.profile.domain.model.vo


@JvmInline
value class Name private constructor(val value: String) {
    companion object {
        fun create(raw: String): Name {
            val v = raw.trim().replace(Regex("\\s+"), " ")
            require(v.length in 2..80) { "Nombre debe tener 2–80 caracteres" }
            return Name(v)
        }
    }
}