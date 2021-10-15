package com.hrs.Kloping.Kotlin.Plugins

import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader.main

object PluginLoader {
    fun load(args: Array<String>) {
        try {
            main(arrayOf())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}