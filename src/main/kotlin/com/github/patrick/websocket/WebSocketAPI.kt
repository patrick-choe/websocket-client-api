package com.github.patrick.websocket

import com.github.patrick.websocket.exception.WebSocketNoResponseException

/**
 * This API object provides simple WebSocket client for Bukkit plugins.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
object WebSocketAPI {
    /**
     * Creates a websocket client without tls support.
     * Returns null when websocket has no response.
     *
     * @param url websocket url to connect
     * @return nullable WebSocketClient
     */
    @JvmStatic
    fun createWebSocket(url: String): WebSocketClient? {
        return createWebSocket(url, tls = false, suppress = false)
    }

    /**
     * Creates a websocket client with tls option.
     * Returns null when websocket has no response.
     *
     * @param url websocket url to connect
     * @param tls whether to use tls support
     * @return nullable WebSocketClient
     */
    @JvmStatic
    fun createWebSocket(url: String, tls: Boolean, suppress: Boolean): WebSocketClient? {
        return try {
            WebSocketClient(url, WebSocketHandler(), tls = tls, suppress = suppress)
        } catch (exception: WebSocketNoResponseException) {
            null
        }
    }

    /**
     * Creates a websocket client without tls support.
     * This method throws WebSocketNoResponseException
     * when websocket has no response.
     *
     * @param url websocket url to connect
     * @return WebSocketClient
     */
    @JvmStatic
    @Throws(WebSocketNoResponseException::class)
    fun createUnsafeWebSocket(url: String): WebSocketClient {
        return createUnsafeWebSocket(url, tls = false, suppress = false)
    }

    /**
     * Creates a websocket client with tls option.
     * This method throws WebSocketNoResponseException
     * when websocket has no response.
     *
     * @param url websocket url to connect
     * @param tls whether to use tls support
     * @return WebSocketClient
     */
    @JvmStatic
    @Throws(WebSocketNoResponseException::class)
    fun createUnsafeWebSocket(url: String, tls: Boolean, suppress: Boolean): WebSocketClient {
        return WebSocketClient(url, WebSocketHandler(), tls = tls, suppress = suppress)
    }
}