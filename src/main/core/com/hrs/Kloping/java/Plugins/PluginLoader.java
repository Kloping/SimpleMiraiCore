package com.hrs.Kloping.java.Plugins;

import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;

import java.io.PrintStream;

public class PluginLoader {
    public static void load(String[] args) {
        try {
            PrintStream old_ps = System.out;
            PrintStream old_err = System.err;

            MiraiConsoleTerminalLoader.main(args);

            System.setErr(old_err);
            System.setOut(old_ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

