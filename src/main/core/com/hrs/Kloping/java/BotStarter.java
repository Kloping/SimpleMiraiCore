package com.hrs.Kloping.java;

import com.hrs.Kloping.java.ListenerHosts.BaseMessageListener;
import com.hrs.Kloping.java.Plugins.PluginLoader;
import com.hrs.MySpringTool.Starter;
import com.hrs.MySpringTool.annotations.CommentScan;
import com.hrs.MySpringTool.exceptions.NoRunException;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CommentScan(path = "com.hrs.Kloping.java")
public class BotStarter {
    public static final long qq = 0L;
    public static final String password = "";

    public static void main(String[] args) {
        BotConfiguration botConfiguration = new BotConfiguration();
        //登录协议
        botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PHONE);
        // 心跳协议
        botConfiguration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
        // 设置 cache 目录
        botConfiguration.setCacheDir(new File("./cache"));
        // 设置 device
        botConfiguration.fileBasedDeviceInfo("./device.json");
        // 创建 Bot
        Bot bot = BotFactory.INSTANCE.newBot(qq, password, botConfiguration);
        // 登录
        bot.login();
        // 注册消息处理 通道
        bot.getEventChannel().registerListenerHost(new BaseMessageListener());
        // 启动 工具处理
        startSpring();
        //加载插件
        PluginLoader.load(args);
    }

    public static final ExecutorService threads = Executors.newFixedThreadPool(20);

    // 这里是关键点 不懂得话可以去看我的另一个github
    //https://github.com/Kloping/my-spring-tool
    private static void startSpring() {
        Starter.run(BotStarter.class);
        Starter.setLog_Level(1);
        Starter.set_key(Long.class);
        Starter.setWaitTime(25L);
        Starter.setAccPars(Long.class, Contact.class, Message.class);
        Starter.setAllAfter(new Starter.AllAfterOrBefore(Starter.AllAfterOrBefore.State.After) {
            @Override
            public void run(Object o, Object[] objects) throws NoRunException {
                threads.execute(() -> {
                    onReturnResult(o, objects);
                });
            }
        });
        Starter.setAllBefore(new Starter.AllAfterOrBefore(Starter.AllAfterOrBefore.State.Before) {
            @Override
            public void run(Object o, Object[] objects) throws NoRunException {
                System.out.println("所有程序运行之前");
            }
        });
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


    private static final Map<Long, At> ats = new ConcurrentHashMap<>();

    private static At getAt(long q) {
        if (ats.containsKey(q)) return ats.get(q);
        At at = new At(q);
        ats.put(q, at);
        return at;
    }
}
