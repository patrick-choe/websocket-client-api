package com.github.patrick.websocket

import com.github.patrick.websocket.event.WebSocketBinaryEvent
import com.github.patrick.websocket.event.WebSocketConnectedEvent
import com.github.patrick.websocket.event.WebSocketDisconnectedEvent
import com.github.patrick.websocket.event.WebSocketMessageEvent
import com.github.patrick.websocket.plugin.WebSocketPlugin
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFrame
import org.bukkit.Bukkit

/**
 * Handles websocket events
 */
class WebSocketHandler : WebSocketAdapter() {
    /**
     * On connect
     */
    override fun onConnected(socket: WebSocket, headers: Map<String, List<String>>) {
        Bukkit.getScheduler().callSyncMethod(WebSocketPlugin.INSTANCE) {
            Bukkit.getServer().pluginManager.callEvent(WebSocketConnectedEvent(socket, headers))
        }.get()
    }

    /**
     * On disconnect
     */
    override fun onDisconnected(socket: WebSocket, serverCloseFrame: WebSocketFrame, clientCloseFrame: WebSocketFrame, closedByServer: Boolean) {
        Bukkit.getScheduler().callSyncMethod(WebSocketPlugin.INSTANCE) {
            Bukkit.getServer().pluginManager.callEvent(WebSocketDisconnectedEvent(socket, closedByServer))
        }.get()
    }

    /**
     * On message receive
     */
    override fun onTextMessage(socket: WebSocket, message: String) {
        Bukkit.getScheduler().callSyncMethod(WebSocketPlugin.INSTANCE) {
            Bukkit.getServer().pluginManager.callEvent(WebSocketMessageEvent(socket, message))
        }.get()
    }

    /**
     * On binary receive
     */
    override fun onBinaryMessage(socket: WebSocket, binary: ByteArray) {
        Bukkit.getScheduler().callSyncMethod(WebSocketPlugin.INSTANCE) {
            Bukkit.getServer().pluginManager.callEvent(WebSocketBinaryEvent(socket, binary))
        }.get()
    }
}