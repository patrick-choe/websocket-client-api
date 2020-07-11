package com.github.patrick.websocket.plugin

import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class WebSocketPlugin : JavaPlugin() {
    companion object {
        internal lateinit var INSTANCE: WebSocketPlugin
    }

    override fun onEnable() {
        INSTANCE = this
    }
}