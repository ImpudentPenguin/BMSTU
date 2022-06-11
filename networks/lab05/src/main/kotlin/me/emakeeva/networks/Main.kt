package me.emakeeva.networks

import java.io.File
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

/**
 * Первый аргумент - почта получателя
 * Второй аргумент - почта отправителя
 * Третий аргумент - пароль отправителя
 */
fun main(args: Array<String>) {
    val userName = args[1]
    val password = args[2]
    val emailTo = args[0]

    println("Введите интервал в секундах (опционально):")
    val interval = readLine()?.toIntOrNull()?.let { it * 1000 }?.toLong()

    println("Введите тему:")
    val title = readLine()

    println("Введите сообщение:")
    val userMessage = readLine()

    println("Введите абсолютный путь до файла для прикрепления (опционально):")
    val path = readLine()

    val props = Properties().apply {
        set("mail.smtp.host", "smtp.yandex.ru")
        set("mail.smtp.port", "465")
        set("mail.smtp.ssl.protocols", "TLSv1.2")
        set("mail.smtp.auth", "true")
        set("mail.smtp.starttls.enable", "true")
        set("mail.smtp.ssl.enable", "true")
    }

    val session = Session.getDefaultInstance(props, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(userName, password)
        }
    }).also { it.debug = true }

    fun sendMessage(message: MimeMessage) {
        Transport.send(message)
        println("[CLIENT] Email Sent successfully")
    }

    try {
        val message = MimeMessage(session)
        message.setFrom(InternetAddress(userName))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(emailTo))
        message.subject = title

        if (!path.isNullOrEmpty()) {
            val attachmentPart = MimeBodyPart().also { it.attachFile(File(path)) }
            val messageBodyPart = MimeBodyPart().also { it.setText(userMessage) }
            val multipart = MimeMultipart().also {
                it.addBodyPart(messageBodyPart)
                it.addBodyPart(attachmentPart)
            }

            message.setContent(multipart)
        } else {
            message.setText(userMessage)
        }

        if (interval != null) {
            while (true) {
                sendMessage(message)
                Thread.sleep(interval)
            }
        } else {
            sendMessage(message)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}