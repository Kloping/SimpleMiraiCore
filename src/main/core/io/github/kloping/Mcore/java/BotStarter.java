package io.github.kloping.Mcore.java;

import io.github.kloping.Mcore.java.ListenerHosts.BaseMessageListener;
import io.github.kloping.Mcore.java.Plugins.PluginLoader;
import io.github.kloping.MySpringTool.StarterApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.CommentScan;
import io.github.kloping.MySpringTool.entity.interfaces.Runner;
import io.github.kloping.MySpringTool.exceptions.NoRunException;
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

/**
 * @author github-kloping
 */
@CommentScan(path = "io.github.kloping.Mcore.java")
public class BotStarter {

    public static final ExecutorService SERVICES = Executors.newFixedThreadPool(20);
    public static final String CONF_FILE = "./conf.properties";
    private static final Map<Long, At> ATS = new ConcurrentHashMap<>();
    @AutoStand(id = "qq")
    public static Number qq = 0L;
    @AutoStand(id = "pwd")
    public static String password = "";
    @AutoStand(id = "ReLogin")
    public static Boolean autoReLogin = false;
    public static Bot bot;
    @AutoStand(id = "Protocol")
    private static String Protocol = "ANDROID_PAD";

    public static void main(String[] args) {
        //启动时 删除缓存 减少 程序启动后堵塞无法登录的情况
        deleteCache();
        // 启动 工具处理
        startSpring();
        // 加载插件
        PluginLoader.load(args);
        // 创建配置
        BotConfiguration botConfiguration = new BotConfiguration();
        // 登录协议
        botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.valueOf(Protocol));
        // 心跳协议
        botConfiguration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
        // 设置 cache 目录
        botConfiguration.setCacheDir(new File("./cache"));
        // 设置 device
        botConfiguration.fileBasedDeviceInfo("./device.json");
        //设置是否掉线重登录
        botConfiguration.setAutoReconnectOnForceOffline(autoReLogin);
        // 创建 Bot
        bot = BotFactory.INSTANCE.newBot(qq.longValue(), password, botConfiguration);
        // 登录
        bot.login();
        // 注册消息处理 通道
        bot.getEventChannel().registerListenerHost(new BaseMessageListener());
    }

    private static void deleteCache() {
        try {
            File file = new File("./cache");
            for (File f : file.listFiles()) {
                f.deleteOnExit();
            }
            file.deleteOnExit();
        } catch (Exception e) {
        }
    }

    private static void startSpring() {
        StarterApplication.addConfFile(CONF_FILE);
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
