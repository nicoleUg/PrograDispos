package com.calyrsoft.ucbp1.features.profile.domain.model.vo


import android.util.Patterns

@JvmInline
value class PathUrl private constructor(val value: String) {
    companion object {
        fun create(raw: String): PathUrl {
            val v = raw.trim()
            require(v.isNotEmpty()) { "URL vacía" }
            require(Patterns.WEB_URL.matcher(v).matches()) { "URL inválida" }
            return PathUrl(v)
        }
    }
}