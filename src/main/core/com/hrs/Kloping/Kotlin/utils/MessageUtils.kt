package com.hrs.Kloping.Kotlin.utils

import net.mamoe.mirai.message.data.*

object MessageUtils {
    /**
     * 将消息链 转为 可匹配的 文本
     * @param messageChain
     * @return
     */
    fun toText(messageChain: MessageChain): String? {
        val sb = StringBuilder()
        for (message in messageChain) {
            if (message is MessageSource) continue
            else if (message is PlainText) sb.append(message.toString().trim { it <= ' ' })
            else if (message is At) sb.append("[@").append(message.target).append("]")
            else if (message is Image) sb.append("[图片]")
            else if (message is Audio) sb.append("[语音]")
            else sb.append("[其他消息]")
            //可以自己定义
        }
        return sb.toString()
    }
}