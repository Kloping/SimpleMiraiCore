package io.github.kloping.Mcore.java.Plugins;

import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal;
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;

import java.nio.file.Path;

/**
 * @author github-kloping
 */
public class PluginLoader {
    public static void load(String[] args) {
        MiraiConsoleImplementationTerminal terminal = new MiraiConsoleImplementationTerminal(Path.of("."));
        MiraiConsoleTerminalLoader.INSTANCE.startAsDaemon(terminal);
    }
}

