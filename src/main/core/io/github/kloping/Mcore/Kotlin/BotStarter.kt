package io.github.kloping.Mcore.Kotlin

import io.github.kloping.Mcore.Kotlin.ListenerHosts.BaseMessageListener
import io.github.kloping.Mcore.Kotlin.Plugins.PluginLoader
import io.github.kloping.MySpringTool.StarterApplication
import io.github.kloping.MySpringTool.annotations.AutoStand
import io.github.kloping.MySpringTool.annotations.CommentScan
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
import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

@CommentScan(path = "io.github.kloping.Kotlin")
object BotStarter {

    @AutoStand(id = "qq")
    @JvmStatic
    var qq: Number = 0

    @AutoStand(id = "pwd")
    @JvmStatic
    var password = ""

    @AutoStand(id = "ReLogin")
    @JvmStatic
    var autoReLogin: Boolean? = false

    @AutoStand(id = "Protocol")
    @JvmStatic
    var Protocol = "ANDROID_PAD"

    var bot: Bot? = null

    @JvmStatic
    fun main(args: Array<String>) {
        //启动时 删除缓存 减少 程序启动后堵塞无法登录的情况
        deleteCache()
        // 启动 工具处理
        startSpring()
        //创建配置
        val botConfiguration = BotConfiguration()
        //登录协议
        botConfiguration.protocol = MiraiProtocol.valueOf(Protocol)
        // 心跳协议
        botConfiguration.heartbeatStrategy = HeartbeatStrategy.STAT_HB
        // 设置 cache 目录
        botConfiguration.cacheDir = File("./cache")
        // 设置 device
        botConfiguration.fileBasedDeviceInfo("./device.json")
        //设置是否掉线重登录
        botConfiguration.autoReconnectOnForceOffline = autoReLogin!!
        // 创建 Bot
        bot = newBot(qq.toLong(), password, botConfiguration)
        // 登录
        runBlocking {
            bot!!.login()
        }
        // 注册消息处理 通道
        bot!!.eventChannel.registerListenerHost(BaseMessageListener())
        //加载插件
        PluginLoader.load(args)
    }

    private fun deleteCache() {
        try {
            val file = File("./cache")
            for (f in file.listFiles()) {
                f.deleteOnExit()
            }
            file.deleteOnExit()
        } catch (e: Exception) {
        }
    }

    val threads = Executors.newFixedThreadPool(20)

    const val confFile = "./conf.properties"

    // 这里是关键点 不懂得话可以去看我的另一个github
    //https://github.com/Kloping/my-spring-tool
    private fun startSpring() {
        StarterApplication.addConfFile(BotStarter.confFile)
        StarterApplication.setMainKey(Long::class.java)
        StarterApplication.setWaitTime(25L)
        StarterApplication.setAccessTypes(Long::class.java, Contact::class.java, Message::class.java)
        StarterApplication.setAllAfter { t, objects ->
            if (t != null) threads.execute {
                onReturnResult(t, objects)
            }
        }
        StarterApplication.setAllBefore { t, objects -> println("所有程序运行之前") }
        StarterApplication.run(BotStarter::class.java)
    }

    @JvmStatic
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