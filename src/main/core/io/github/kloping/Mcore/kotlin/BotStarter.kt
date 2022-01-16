package io.github.kloping.Mcore.kotlin

import io.github.kloping.Mcore.kotlin.ListenerHosts.BaseMessageListener
import io.github.kloping.Mcore.kotlin.Plugins.PluginLoader
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
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.*
import java.lang.Runnable
import java.io.File
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

/**
 * @author github-kloping
 */
@CommentScan(path = "io.github.kloping.Mcore.java")
object BotStarter {
    @AutoStand(id = "qq")
    var qq: Number = 0L

    @AutoStand(id = "pwd")
    var password = ""

    @JvmField
    @AutoStand(id = "ReLogin")
    var autoReLogin = false

    @AutoStand(id = "Protocol")
    private val Protocol = "ANDROID_PAD"

    @JvmField
    var bot: Bot? = null

    @JvmStatic
    fun main(args: Array<String>) {
        //启动时 删除缓存 减少 程序启动后堵塞无法登录的情况
        deleteCache()
        // 启动 工具处理
        startSpring()
        // 创建配置
        val botConfiguration = BotConfiguration()
        // 登录协议
        botConfiguration.protocol = BotConfiguration.MiraiProtocol.valueOf(Protocol)
        // 心跳协议
        botConfiguration.heartbeatStrategy = HeartbeatStrategy.STAT_HB
        // 设置 cache 目录
        botConfiguration.cacheDir = File("./cache")
        // 设置 device
        botConfiguration.fileBasedDeviceInfo("./device.json")
        //设置是否掉线重登录
        botConfiguration.autoReconnectOnForceOffline = autoReLogin
        // 创建 Bot
        bot = newBot(qq.toLong(), password, botConfiguration)
        // 登录
        bot!!.login()
        // 注册消息处理 通道
        bot!!.eventChannel.registerListenerHost(BaseMessageListener())
        // 加载插件
        PluginLoader.load(args)
    }

    private fun deleteCache() {
        try {
            val file = File("./cache")
            for (f: File in file.listFiles()) {
                f.deleteOnExit()
            }
            file.deleteOnExit()
        } catch (e: Exception) {
        }
    }

    val threads = Executors.newFixedThreadPool(20)

    @JvmField
    val confFile = "./conf.properties"

    // 这里是关键点 不懂得话可以去看我的另一个github
    //https://github.com/Kloping/my-spring-tool
    private fun startSpring() {
        StarterApplication.addConfFile(confFile)
        StarterApplication.setMainKey(Long::class.java)
        StarterApplication.setWaitTime(25L)
        StarterApplication.setAccessTypes(Long::class.java, Contact::class.java, Message::class.java)
        StarterApplication.setAllAfter(Runner { t, objects ->
            if (t != null) threads.execute(
                { onReturnResult(t, objects) })
        })
        StarterApplication.setAllBefore(object : Runner {
            @Throws(NoRunException::class)
            override fun run(t: Any, objects: Array<Any>) {
                println("所有程序运行之前")
            }
        })
        StarterApplication.run(BotStarter::class.java)
    }

    private fun onReturnResult(o: Any?, objects: Array<Any>) {
        println("所有程序运行之后")
        if (o == null) return
        val builder = MessageChainBuilder()
        if (objects[3] is Group) builder.append(
            (getAt(
                objects[0].toString().toLong()
            ))!!
        ).append("\n")
        if (o is String) builder.append(o.toString()) else builder.append((o as Message?)!!)
        (objects[3] as Contact).sendMessage(builder.build())
    }

    private val ats: MutableMap<Long, At> = ConcurrentHashMap()
    private fun getAt(q: Long): At? {
        if (ats.containsKey(q)) return ats[q]
        val at = At(q)
        ats[q] = at
        return at
    }
}