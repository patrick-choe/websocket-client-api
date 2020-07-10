package com.github.patrick.websocket

import com.github.patrick.websocket.event.WebSocketConnectedEvent
import com.github.patrick.websocket.event.WebSocketDisconnectedEvent
import com.github.patrick.websocket.event.WebSocketMessageEvent
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFrame
import org.bukkit.Bukkit

class WebSocketHandler : WebSocketAdapter() {
    override fun onConnected(socket: WebSocket, headers: Map<String, List<String>>) {
        Bukkit.getServer().pluginManager.callEvent(WebSocketConnectedEvent(socket, headers))
    }

    override fun onDisconnected(socket: WebSocket, serverCloseFrame: WebSocketFrame, clientCloseFrame: WebSocketFrame, closedByServer: Boolean) {
        Bukkit.getServer().pluginManager.callEvent(WebSocketDisconnectedEvent(socket, closedByServer))
    }

    override fun onTextMessage(socket: WebSocket, message: String) {
        Bukkit.getServer().pluginManager.callEvent(WebSocketMessageEvent(socket, message))
    }
}