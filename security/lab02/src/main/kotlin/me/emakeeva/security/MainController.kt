package me.emakeeva.security

import me.emakeeva.security.data.ResultModel
import tornadofx.Controller
import java.math.BigInteger
import java.util.Random

typealias D = BigInteger
typealias E = BigInteger
typealias N = BigInteger
typealias PublicKey = Pair<E, N>
typealias PrivateKey = Pair<D, N>

class MainController : Controller() {

    fun encrypt(message: String, size: Int): ResultModel {
        val keys = generateKeys(size)
        val encryptedMessage: List<BigInteger> = message.toCharArray().map {
            BigInteger.valueOf(it.code.toLong()).modPow(keys.first.first, keys.first.second)
        }

        val decryptedMessage = decrypt(encryptedMessage, keys.second)

        return ResultModel(encryptedMessage, decryptedMessage)
    }

    private fun decrypt(encryptedData: List<BigInteger>, privateKey: PrivateKey): String {
        val decrypted = encryptedData.map {
           it.modPow(privateKey.first, privateKey.second).toInt().toChar()
        }

        return decrypted.joinToString("")
    }

    private fun generateKeys(size: Int): Pair<PublicKey, PrivateKey> {
        val p = BigInteger.probablePrime(size, Random())
        val q = BigInteger.probablePrime(size, Random())
        val n = p * q
        val phi = (p - BigInteger.ONE) * (q - BigInteger.ONE)
        val e = generateNum(BigInteger.valueOf(17), phi)
        val d = ExtendedEuclideanAlgorithm.calculate(phi, e)
        val publicKey = e to n
        val privateKey = d to n

        return publicKey to privateKey
    }

    private fun generateNum(from: BigInteger, to: BigInteger): BigInteger {
        var num: BigInteger

        do {
            num = BigInteger.probablePrime(to.bitLength(), Random())
        } while (!((from < num && num < to) && ExtendedEuclideanAlgorithm.gcd(num, to) == BigInteger.ONE))

        return num
    }
}