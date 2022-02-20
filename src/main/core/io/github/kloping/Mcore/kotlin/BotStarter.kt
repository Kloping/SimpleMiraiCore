package io.github.kloping.Mcore.kotlin

import io.github.kloping.Mcore.kotlin.ListenerHosts.BaseMessageListener
import io.github.kloping.Mcore.kotlin.Plugins.PluginLoader
import io.github.kloping.MySpringTool.StarterApplication
import io.github.kloping.MySpringTool.annotations.AutoStand
import io.github.kloping.MySpringTool.annotations.CommentScan
import io.github.kloping.MySpringTool.entity.interfaces.Runner
import io.github.kloping.MySpringTool.exceptions.NoRunException
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory.INSTANCE.newBot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.BotConfiguration.HeartbeatStrategy
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

/**
 * @author github-kloping
 */
@CommentScan(path = "io.github.kloping.Mcore.kotlin")
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
    suspend fun main(args: Array<String>) {
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
        StarterApplication.setWaitTime(25000L)
        StarterApplication.setAccessTypes(Long::class.java, Contact::class.java, Message::class.java)
        StarterApplication.setAllAfter(Runner { t, objects ->
            if (t != null) threads.execute { onReturnResult(t, objects) }
        })
        StarterApplication.setAllBefore { t, objects -> println("所有程序运行之前") }
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
        runBlocking {
            if (o is String) builder.append(o.toString()) else builder.append((o as Message?)!!)
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