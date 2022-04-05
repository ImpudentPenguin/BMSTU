package me.emakeeva.security.data

enum class Language(val alphabet: List<Char>) {
    Russian((('а'..'я') + ' ').toList()),
    English((('a'..'z') + ' ').toList())
}