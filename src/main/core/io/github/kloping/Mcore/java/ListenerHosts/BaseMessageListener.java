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

    //群消息
    @EventHandler
    public void handGroupMessage(GroupMessageEvent event) {
        long q = event.getSender().getId();
        Contact contact = event.getSubject();
        Message message = event.getMessage();
        String text = MessageUtils.toText(event.getMessage()).trim();
        StarterApplication.ExecuteMethod(q, text, q, contact, message);
    }

    // 朋友消息
    @EventHandler
    public void handFriendMessage(FriendMessageEvent event) {

    }

    // 陌生人消息
    @EventHandler
    public void handStrangerMessage(StrangerMessageEvent event) {

    }

    @EventHandler
    public void onLost(BotOfflineEvent event) {
        Starter.reLoadConfigurationFile(BotStarter.confFile);
        Boolean k = Starter.getContextValue(boolean.class, "ReLogin");
        k = k == null ? BotStarter.autoReLogin : k;
        BotStarter.bot.getConfiguration().setAutoReconnectOnForceOffline(k);
    }
}
