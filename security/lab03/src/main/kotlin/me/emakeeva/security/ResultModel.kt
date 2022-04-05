package me.emakeeva.security

data class ResultModel(
    val original: ByteArray,
    val encrypted: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResultModel

        if (!original.contentEquals(other.original)) return false
        if (!encrypted.contentEquals(other.encrypted)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = original.contentHashCode()
        result = 31 * result + encrypted.contentHashCode()
        return result
    }
}