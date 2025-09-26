package com.calyrsoft.ucbp1.features.profile.domain.model.vo

@JvmInline
value class Summary private constructor(val value: String) {
    companion object {
        fun create(raw: String, maxLen: Int = 280): Summary {
            val v = raw.trim()
            require(v.length <= maxLen) { "Resumen supera $maxLen caracteres" }
            return Summary(v)
        }
    }
}