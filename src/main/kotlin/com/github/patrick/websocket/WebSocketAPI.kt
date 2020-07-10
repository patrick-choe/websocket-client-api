package com.github.patrick.websocket

import com.github.patrick.websocket.exception.WebSocketNoResponseException

@Suppress("unused", "MemberVisibilityCanBePrivate")
object WebSocketAPI {
    @JvmStatic
    fun createWebSocket(url: String): WebSocketClient? {
        return createWebSocket(url, false)
    }

    @JvmStatic
    fun createWebSocket(url: String, tls: Boolean): WebSocketClient? {
        return try {
            WebSocketClient(url, WebSocketHandler(), tls)
        } catch (exception: WebSocketNoResponseException) {
            null
        }
    }

    @JvmStatic
    fun createUnsafeWebSocket(url: String): WebSocketClient {
        return createUnsafeWebSocket(url, false)
    }

    @JvmStatic
    fun createUnsafeWebSocket(url: String, tls: Boolean): WebSocketClient {
        return WebSocketClient(url, WebSocketHandler(), tls)
    }
}