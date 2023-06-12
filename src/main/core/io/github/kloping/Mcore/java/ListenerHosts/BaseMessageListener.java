package io.github.kloping.Mcore.java.ListenerHosts;

import io.github.kloping.Mcore.java.BotStarter;
import io.github.kloping.Mcore.java.utils.MessageUtils;
import io.github.kloping.MySpringTool.Starter;
import io.github.kloping.MySpringTool.StarterApplication;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.StrangerMessageEvent;
import net.mamoe.mirai.message.data.Message;

/**
 * @author github-kloping
 */
public class BaseMessageListener extends SimpleListenerHost {
    public BaseMessageListener() {
        super();
    }

    public BaseMessageListener(CoroutineContext coroutineContext) {
        super(coroutineContext);
    }

    @Override
    public void handleException(CoroutineContext context, Throwable exception) {
        super.handleException(context, exception);
    }

    /**
     * 收到群消息
     *
     * @param event
     */
    @EventHandler
    public void handGroupMessage(GroupMessageEvent event) {
        long q = event.getSender().getId();
        Contact contact = event.getSubject();
        Message message = event.getMessage();
        String text = MessageUtils.toText(event.getMessage()).trim();
        StarterApplication.executeMethod(q, text, q, contact, message);
    }

    /**
     * 收到朋友消息
     *
     * @param event
     */
    @EventHandler
    public void handFriendMessage(FriendMessageEvent event) {
        long q = event.getSender().getId();
        Contact contact = event.getSubject();
        Message message = event.getMessage();
        String text = MessageUtils.toText(event.getMessage()).trim();
        StarterApplication.executeMethod(q, text, q, contact, message);
    }

    /**
     * 陌生人消息
     *
     * @param event
     */
    @EventHandler
    public void handStrangerMessage(StrangerMessageEvent event) {

    }

    /**
     * 机器人掉线
     *
     * @param event
     */
    @EventHandler
    public void onLost(BotOfflineEvent event) {
    }
}
