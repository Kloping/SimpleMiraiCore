package com.hrs.Kloping.Kotlin.ListenerHosts

import com.hrs.Kloping.Kotlin.BotStarter
import com.hrs.Kloping.Kotlin.utils.MessageUtils
import com.hrs.MySpringTool.Starter
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.BotOfflineEvent
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.StrangerMessageEvent
import net.mamoe.mirai.message.data.Message
import kotlin.coroutines.CoroutineContext

class BaseMessageListener() : SimpleListenerHost() {


    override fun handleException(context: CoroutineContext, exception: Throwable) {
        super.handleException(context, exception)
    }

    //群消息
    @EventHandler
    fun handGroupMessage(event: GroupMessageEvent) {
        val q = event.sender.id
        val contact: Contact = event.subject
        val message: Message = event.message
        val text = MessageUtils.toText(event.message)?.trim { it <= ' ' }
        Starter.ExecuteMethod(q, text, q, contact, message)
    }

    // 朋友消息
    @EventHandler
    fun handFriendMessage(event: FriendMessageEvent) {
    }

    // 陌生人消息
    @EventHandler
    fun handStrangerMessage(event: StrangerMessageEvent) {
    }

    @EventHandler
    fun onLost(event: BotOfflineEvent?) {
        Starter.reLoadConfigurationFile(BotStarter.confFile)
        var k = Starter.getContextValue<Boolean>(Boolean::class.javaPrimitiveType, "ReLogin")
        k = k ?: BotStarter.autoReLogin
        BotStarter.bot?.configuration?.autoReconnectOnForceOffline = k
    }
}