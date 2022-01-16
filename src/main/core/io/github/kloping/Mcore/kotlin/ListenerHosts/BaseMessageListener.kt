package io.github.kloping.Mcore.kotlin.ListenerHosts

import io.github.kloping.Mcore.kotlin.BotStarter
import io.github.kloping.Mcore.kotlin.utils.MessageUtils
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
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.message.data.*
import java.lang.Runnable
import java.util.concurrent.ConcurrentHashMap

/**
 * @author github-kloping
 */
class BaseMessageListener : SimpleListenerHost {
    constructor() : super() {}
    constructor(coroutineContext: CoroutineContext?) : super(coroutineContext!!) {}

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        super.handleException(context, exception)
    }

    /**
     * 收到群消息
     *
     * @param event
     */
    @EventHandler
    fun handGroupMessage(event: GroupMessageEvent) {
        val q = event.sender.id
        val contact: Contact = event.subject
        val message: Message = event.message
        val text = MessageUtils.toText(event.message).trim { it <= ' ' }
        StarterApplication.ExecuteMethod(q, text, q, contact, message)
    }

    /**
     * 收到朋友消息
     *
     * @param event
     */
    @EventHandler
    fun handFriendMessage(event: FriendMessageEvent) {
        val q = event.sender.id
        val contact: Contact = event.subject
        val message: Message = event.message
        val text = MessageUtils.toText(event.message).trim { it <= ' ' }
        StarterApplication.ExecuteMethod(q, text, q, contact, message)
    }

    /**
     * 陌生人消息
     *
     * @param event
     */
    @EventHandler
    fun handStrangerMessage(event: StrangerMessageEvent?) {
    }

    /**
     * 机器人掉线
     *
     * @param event
     */
    @EventHandler
    fun onLost(event: BotOfflineEvent?) {
        Starter.reLoadConfigurationFile(BotStarter.confFile)
        var k = Starter.getContextValue<Boolean>(Boolean::class.javaPrimitiveType, "ReLogin")
        k = k ?: BotStarter.autoReLogin
        BotStarter.bot!!.configuration.autoReconnectOnForceOffline = k
    }
}