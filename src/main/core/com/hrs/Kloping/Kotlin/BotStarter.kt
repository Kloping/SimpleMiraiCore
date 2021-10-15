package com.hrs.Kloping.Kotlin

import com.hrs.Kloping.Kotlin.ListenerHosts.BaseMessageListener
import com.hrs.Kloping.Kotlin.Plugins.PluginLoader
import com.hrs.MySpringTool.Starter
import com.hrs.MySpringTool.Starter.AllAfterOrBefore
import com.hrs.MySpringTool.annotations.CommentScan
import com.hrs.MySpringTool.exceptions.NoRunException
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.BotFactory.INSTANCE.newBot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.BotConfiguration.HeartbeatStrategy
import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

@CommentScan(path = "com.hrs.Kloping.Kotlin")
object BotStarter {
    const val qq = 0L
    const val password = ""

    @JvmStatic
    fun main(args: Array<String>) {
        val botConfiguration = BotConfiguration()
        //登录协议
        botConfiguration.protocol = MiraiProtocol.ANDROID_PHONE
        // 心跳协议
        botConfiguration.heartbeatStrategy = HeartbeatStrategy.STAT_HB
        // 设置 cache 目录
        botConfiguration.cacheDir = File("./cache")
        // 设置 device
        botConfiguration.fileBasedDeviceInfo("./device.json")
        // 创建 Bot
        val bot = newBot(qq, password, botConfiguration)
        runBlocking {
            // 登录
            bot.login()
        }
        // 注册消息处理 通道
        bot.eventChannel.registerListenerHost(BaseMessageListener())
        // 启动 工具处理
        startSpring()
        //加载插件
        PluginLoader.load(args)
    }

    val threads = Executors.newFixedThreadPool(20)

    // 这里是关键点 不懂得话可以去看我的另一个github
    //https://github.com/Kloping/my-spring-tool
    private fun startSpring() {
        Starter.run(BotStarter::class.java)
        Starter.setLog_Level(1)
        Starter.set_key(Class.forName("java.lang.Long"))
        Starter.setWaitTime(25L)
        Starter.setAccPars(
            Class.forName("java.lang.Long"),
            Class.forName("net.mamoe.mirai.contact.Contact"),
            Class.forName("net.mamoe.mirai.message.data.Message")
        )
        Starter.setAllAfter(object : AllAfterOrBefore(State.After) {
            @Throws(NoRunException::class)
            override fun run(o: Any?, objects: Array<Any>) {
                threads.execute { onReturnResult(o, objects) }
            }
        })
        Starter.setAllBefore(object : AllAfterOrBefore(State.Before) {
            @Throws(NoRunException::class)
            override fun run(o: Any?, objects: Array<Any>) {
                println("所有程序运行之前")
            }
        })
    }

    private fun onReturnResult(o: Any?, objects: Array<Any>) {
        println("所有程序运行之后")
        if (o == null) return
        val builder = MessageChainBuilder()
        if (objects[3] is Group) builder.append(getAt(objects[0].toString().toLong())!!).append("\n")
        if (o is String) builder.append(o.toString()) else builder.append((o as Message?)!!)
        runBlocking {
            (objects[3] as Contact).sendMessage(builder.build())
        }
    }

    private val ats: MutableMap<Long, At> = ConcurrentHashMap()

    private fun getAt(q: Long): At? {
        if (ats.containsKey(q)) return ats[q]
        val at = At(q)
        ats[q] = at
        return at
    }
}