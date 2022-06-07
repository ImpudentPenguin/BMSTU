package me.emakeeva.dsp


data class ResultModel(
    val original: ByteArray,
    val libResult: ByteArray?,
    val result: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResultModel

        if (!original.contentEquals(other.original)) return false
        if (!libResult.contentEquals(other.libResult)) return false
        if (!result.contentEquals(other.result)) return false

        return true
    }

    override fun hashCode(): Int {
        var res = original.contentHashCode()
        res = 31 * res + libResult.contentHashCode()
        res = 31 * res + result.contentHashCode()
        return res
    }
}