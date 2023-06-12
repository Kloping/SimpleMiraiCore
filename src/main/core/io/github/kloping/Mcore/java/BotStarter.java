package io.github.kloping.Mcore.java;

import io.github.kloping.Mcore.java.ListenerHosts.BaseMessageListener;
import io.github.kloping.Mcore.java.Plugins.PluginLoader;
import io.github.kloping.MySpringTool.StarterApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.CommentScan;
import io.github.kloping.MySpringTool.entity.interfaces.Runner;
import io.github.kloping.MySpringTool.exceptions.NoRunException;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author github-kloping
 */
@CommentScan(path = "io.github.kloping.Mcore.java")
public class BotStarter {

    public static final ExecutorService SERVICES = Executors.newFixedThreadPool(20);
    private static final Map<Long, At> ATS = new ConcurrentHashMap<>();
    public static Bot bot;

    public static void main(String[] args) {
        // 启动 工具处理
        startSpring();
        //Start Mirai Console
        PluginLoader.load(args);
        // register default listener
        GlobalEventChannel.INSTANCE.registerListenerHost(new BaseMessageListener());
    }

    private static void startSpring() {
        StarterApplication.setMainKey(Long.class);
        StarterApplication.setWaitTime(25000L);
        StarterApplication.setAccessTypes(Long.class, Contact.class, Message.class);
        StarterApplication.setAllAfter(new Runner() {
            @Override
            public void run(Object t, Object[] objects) throws NoRunException {
                if (t != null)
                    SERVICES.execute(() -> {
                        onReturnResult(t, objects);
                    });
            }
        });
        StarterApplication.setAllBefore(new Runner() {
            @Override
            public void run(Object t, Object[] objects) throws NoRunException {
                System.out.println("所有程序运行之前");
            }
        });
        StarterApplication.run(BotStarter.class);
    }

    private static void onReturnResult(Object o, Object[] objects) {
        System.out.println("所有程序运行之后");
        if (o == null) return;
        MessageChainBuilder builder = new MessageChainBuilder();
        if (objects[3] instanceof Group)
            builder.append(getAt(Long.parseLong(objects[0].toString()))).append("\n");
        if (o instanceof String)
            builder.append(o.toString());
        else
            builder.append((Message) o);
        ((Contact) objects[3]).sendMessage(builder.build());
    }

    private static At getAt(long q) {
        if (ATS.containsKey(q)) return ATS.get(q);
        At at = new At(q);
        ATS.put(q, at);
        return at;
    }
}
