package io.github.kloping.Mcore.kotlin.utils

import net.mamoe.mirai.message.data.Message.toString
import net.mamoe.mirai.message.data.At.target
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader.main
import net.mamoe.mirai.contact.Contact.id
import net.mamoe.mirai.event.SimpleListenerHost.handleException
import net.mamoe.mirai.event.events.GroupMessageEvent.sender
import net.mamoe.mirai.contact.User.id
import net.mamoe.mirai.event.events.GroupMessageEvent.subject
import net.mamoe.mirai.event.events.GroupMessageEvent.message
import net.mamoe.mirai.event.events.FriendMessageEvent.sender
import net.mamoe.mirai.event.events.FriendMessageEvent.subject
import net.mamoe.mirai.event.events.FriendMessageEvent.message
import net.mamoe.mirai.Bot.configuration
import net.mamoe.mirai.utils.BotConfiguration.autoReconnectOnForceOffline
import net.mamoe.mirai.utils.BotConfiguration.protocol
import net.mamoe.mirai.utils.BotConfiguration.heartbeatStrategy
import net.mamoe.mirai.utils.BotConfiguration.cacheDir
import net.mamoe.mirai.utils.BotConfiguration.fileBasedDeviceInfo
import net.mamoe.mirai.BotFactory.INSTANCE.newBot
import net.mamoe.mirai.Bot.eventChannel
import net.mamoe.mirai.event.EventChannel.registerListenerHost
import net.mamoe.mirai.message.data.MessageChainBuilder.append
import net.mamoe.mirai.message.data.MessageChainBuilder.build
import java.lang.StringBuilder
import java.io.PrintStream
import net.mamoe.mirai.contact.Contact
import io.github.kloping.MySpringTool.annotations.AutoStand
import java.lang.NumberFormatException
import io.github.kloping.arr.Class2OMap
import io.github.kloping.MySpringTool.annotations.ReturnResult
import io.github.kloping.MySpringTool.annotations.TimeEve
import net.mamoe.mirai.event.SimpleListenerHost
import kotlin.coroutines.CoroutineContext
import net.mamoe.mirai.event.events.GroupMessageEvent
import io.github.kloping.MySpringTool.StarterApplication
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.StrangerMessageEvent
import net.mamoe.mirai.event.events.BotOfflineEvent
import io.github.kloping.MySpringTool.Starter
import io.github.kloping.MySpringTool.annotations.CommentScan
import net.mamoe.mirai.Bot
import kotlin.jvm.JvmStatic
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.BotConfiguration.HeartbeatStrategy
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import io.github.kloping.MySpringTool.entity.interfaces.Runner
import kotlin.Throws
import io.github.kloping.MySpringTool.exceptions.NoRunException
import net.mamoe.mirai.message.data.*
import java.lang.Runnable
import java.util.concurrent.ConcurrentHashMap

/**
 * @author github-kloping
 */
object MessageUtils {
    /**
     * 将消息链 转为 可匹配的 文本
     * @param messageChain
     * @return
     */
    fun toText(messageChain: MessageChain): String {
        val sb = StringBuilder()
        for (message in messageChain) {
            if (message is MessageSource) continue else if (message is PlainText) sb.append(
                message.toString().trim { it <= ' ' }) else if (message is At) sb.append("[@").append(
                message.target
            )
                .append("]") else if (message is Image) sb.append("[图片]") else if (message is Audio) sb.append("[语音]") else sb.append(
                "[其他消息]"
            )
            //可以自己定义
        }
        return sb.toString()
    }
}