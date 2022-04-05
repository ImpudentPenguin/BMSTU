package me.emakeeva.security

import java.math.BigInteger

object ExtendedEuclideanAlgorithm {

    fun calculate(a0: BigInteger, b0: BigInteger): BigInteger {
        var x0 = BigInteger.ZERO
        var x =  BigInteger.ONE
        var y0 = a0
        var y = b0

        while (y > BigInteger.ZERO) {
            val a = y0 / y
            var temp = x0
            x0 = x
            x = temp - a * x
            temp = y0
            y0 = y
            y = temp - a * y
        }

        return x0 % b0
    }

    fun gcd(a: BigInteger, b: BigInteger): BigInteger {
        return if (b == BigInteger.ZERO) a
        else gcd(b, a % b)
    }
}