package org.emakeeva.tisd.models

import java.lang.StringBuilder

class Tree(val codes: MutableMap<Char, String>) {

    override fun toString(): String {
        val builder = StringBuilder()
        codes.forEach { (key, value) ->
            builder.appendln("$key:$value")
        }

        return builder.toString().trim()
    }
}