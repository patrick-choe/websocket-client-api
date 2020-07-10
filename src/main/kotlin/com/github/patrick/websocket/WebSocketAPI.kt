package com.github.patrick.websocket

import com.github.patrick.websocket.exception.WebSocketNoResponseException
import com.neovisionaries.ws.client.WebSocketAdapter

@Suppress("unused", "MemberVisibilityCanBePrivate")
object WebSocketAPI {
    @JvmStatic
    fun createWebSocket(url: String): WebSocketClient? {
        return createWebSocket(url, false)
    }

    @JvmStatic
    fun createWebSocket(url: String, tls: Boolean): WebSocketClient? {
        return try {
            WebSocketClient(url, WebSocketAdapter(), tls)
        } catch (exception: WebSocketNoResponseException) {
            null
        }
    }
}