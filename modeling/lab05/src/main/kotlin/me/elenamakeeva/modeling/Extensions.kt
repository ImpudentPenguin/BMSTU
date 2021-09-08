package me.elenamakeeva.modeling

import javafx.scene.control.TextField
import java.util.*

fun TextField.toDouble(): Double = this.text.toDouble()

fun TextField.toInt(): Int = this.text.toInt()

fun Float.format(digits: Int): String = "%.${digits}f".format(Locale.US, this)