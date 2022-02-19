package io.github.kloping.Mcore.kotlin.ListenerHosts

import io.github.kloping.Mcore.kotlin.BotStarter
import io.github.kloping.Mcore.kotlin.utils.MessageUtils
import io.github.kloping.MySpringTool.Starter
import io.github.kloping.MySpringTool.StarterApplication
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.BotOfflineEvent
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.StrangerMessageEvent
import net.mamoe.mirai.message.data.Message
import kotlin.coroutines.CoroutineContext

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
        StarterApplication.executeMethod(q, text, q, contact, message)
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
        StarterApplication.executeMethod(q, text, q, contact, message)
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