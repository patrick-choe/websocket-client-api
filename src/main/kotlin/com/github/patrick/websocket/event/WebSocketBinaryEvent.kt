package com.github.patrick.websocket.event

import com.neovisionaries.ws.client.WebSocket
import org.bukkit.event.HandlerList

@Suppress("MemberVisibilityCanBePrivate", "unused", "CanBeParameter")
class WebSocketBinaryEvent(val socket: WebSocket, val binary: ByteArray) : WebSocketEvent(socket) {
    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }

    override fun getHandlers() = handlerList
}