package com.github.patrick.websocket.event

import com.neovisionaries.ws.client.WebSocket
import org.bukkit.event.HandlerList

@Suppress("MemberVisibilityCanBePrivate", "unused", "CanBeParameter")
class WebSocketDisconnectedEvent(val socket: WebSocket, val closedByServer: Boolean) : WebSocketEvent(socket) {
    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }

    override fun getHandlers() = handlerList
}
