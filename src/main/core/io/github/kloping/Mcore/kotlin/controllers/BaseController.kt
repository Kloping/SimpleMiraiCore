package io.github.kloping.Mcore.kotlin.controllers

import io.github.kloping.Mcore.kotlin.services.BaseService
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
import net.mamoe.mirai.message.data.MessageChain
import java.lang.StringBuilder
import net.mamoe.mirai.message.data.MessageSource
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.At
import java.io.PrintStream
import net.mamoe.mirai.contact.Contact
import java.lang.NumberFormatException
import io.github.kloping.arr.Class2OMap
import net.mamoe.mirai.event.SimpleListenerHost
import kotlin.coroutines.CoroutineContext
import net.mamoe.mirai.event.events.GroupMessageEvent
import io.github.kloping.MySpringTool.StarterApplication
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.StrangerMessageEvent
import net.mamoe.mirai.event.events.BotOfflineEvent
import io.github.kloping.MySpringTool.Starter
import io.github.kloping.MySpringTool.annotations.*
import net.mamoe.mirai.Bot
import kotlin.jvm.JvmStatic
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.BotConfiguration.HeartbeatStrategy
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import io.github.kloping.MySpringTool.entity.interfaces.Runner
import kotlin.Throws
import io.github.kloping.MySpringTool.exceptions.NoRunException
import java.lang.Runnable
import net.mamoe.mirai.message.data.MessageChainBuilder
import java.util.concurrent.ConcurrentHashMap

/**
 * @author github-kloping
 */
@Controller
class BaseController {
    @Before
    fun before(contact: Contact) {
        println(contact.id.toString() + "消息 运行之前")
        //若此处抛出 NoRunException 将阻止 Action运行
    }

    @Action("你好啊")
    fun hello(): String {
        return "你好,你好"
    }

    @AutoStand
    var service: BaseService? = null

    @Action("计算<.+=>str>")
    fun add(@Param("str") str: String): String {
        return try {
            val ss = str.split("\\+").toTypedArray()
            val a = ss[0].toInt()
            val b = ss[1].toInt()
            "结果" + service!!.add(a, b)
        } catch (e: NumberFormatException) {
            "格式错误"
        }
    }

    @Action("获取指定类型.*")
    fun getAnyType(cm: Class2OMap): String {
        cm.isIdentical = false
        val (target) = cm.get(At::class.java)
        return String.format("你At了: $target")
    }

    @After
    fun after(contact: Contact, @ReturnResult t1: Any) {
        println("处理结果 =>$t1")
        println(contact.id.toString() + " 消息 运行之后")
        //若此处抛出 NoRunException 将阻止 消息的 发出
    }
}