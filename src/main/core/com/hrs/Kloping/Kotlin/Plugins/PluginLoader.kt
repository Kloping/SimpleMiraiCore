package com.hrs.Kloping.Kotlin.Plugins

import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader.main

object PluginLoader {
    fun load(args: Array<String>) {
        try {
            val old_ps = System.out
            val old_err = System.err
            main(args)
            System.setErr(old_err)
            System.setOut(old_ps)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}