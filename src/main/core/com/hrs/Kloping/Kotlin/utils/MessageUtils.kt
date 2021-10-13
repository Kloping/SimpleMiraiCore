package com.hrs.Kloping.Kotlin.utils

import net.mamoe.mirai.message.data.*
import java.lang.StringBuilder

object MessageUtils {
    fun toText(messageChain: MessageChain): String {
        val sb = StringBuilder()
        for (message in messageChain) {
            if (message is MessageSource) continue
            if (message is PlainText) sb.append(message.toString().trim { it <= ' ' })
            if (message is At) sb.append("[@").append(message.target).append("]")
            //.........
        }
        return sb.toString()
    }
}