package org.emakeeva.tisd

fun Double.format(digits: Int) = "%.${digits}f".format(this)