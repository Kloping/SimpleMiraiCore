package io.github.kloping.Mcore.java.Plugins;

import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;

import java.io.PrintStream;

/**
 * @author github-kloping
 */
public class PluginLoader {
    public static void load(String[] args) {
        try {
            PrintStream oldPs = System.out;
            PrintStream oldErr = System.err;

            MiraiConsoleTerminalLoader.main(args);

            System.setErr(oldErr);
            System.setOut(oldPs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

