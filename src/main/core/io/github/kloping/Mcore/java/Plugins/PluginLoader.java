package io.github.kloping.Mcore.java.Plugins;

import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;

/**
 * @author github-kloping
 */
public class PluginLoader {
    public static void load(String[] args) {
        MiraiConsoleTerminalLoader.main(args);
//        try {
//            PrintStream oldPs = System.out;
//            PrintStream oldErr = System.err;
//            System.setErr(oldErr);
//            System.setOut(oldPs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

