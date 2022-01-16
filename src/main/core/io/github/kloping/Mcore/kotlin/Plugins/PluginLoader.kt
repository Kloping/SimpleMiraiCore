package io.github.kloping.Mcore.kotlin.Plugins

import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader.main

/**
 * @author github-kloping
 */
object PluginLoader {
    fun load(args: Array<String>?) {
        try {
            val oldPs = System.out
            val oldErr = System.err
            main(args!!)
            System.setErr(oldErr)
            System.setOut(oldPs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}