package com.github.patrick.websocket

import com.github.patrick.websocket.exception.WebSocketNoResponseException
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory
import javax.net.ssl.SSLContext

/**
 * This class represents the client itself.
 *
 * @param url the websocket url
 * @param adapter the adapter to handle this client
 * @param tls whether to use tls support
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class WebSocketClient(val url: String, val adapter: WebSocketAdapter, val tls: Boolean = false, val suppress: Boolean = false) {
    var socket: WebSocket? = null
        private set
    var checked = true
        private set
    var connected = false
        private set

    init {
        try {
            socket = WebSocketFactory().apply {
                if (tls) {
                    sslContext = SSLContext.getInstance("TLS").apply {
                        init(null, null, null)
                    }
                }
                verifyHostname = false
            }.createSocket(url).apply {
                addListener(adapter)
            }
            checked = true
        } catch (throwable: Throwable) {
            checked = false
            if (!suppress) {
                throw WebSocketNoResponseException("No response from $url")
            }
        }
    }

    /**
     * Try connecting to websocket
     *
     * @return true if connection is successful
     */
    fun connect(): Boolean {
        if (connected) {
            if (!suppress) {
                println("already connected")
            }
            return false
        }
        return if (checked) {
            try {
                socket?.connect()
                true
            } catch (throwable: Throwable) {
                if (!suppress) {
                    throwable.printStackTrace()
                }
                connected = true
                false
            }
        } else {
            connected = false
            false
        }
    }

    /**
     * Disconnect from websocket
     */
    fun disconnect() {
        if (connected) {
            socket?.disconnect()
        } else if (!suppress) {
            println("not connected")
        }
        connected = false
    }

    /**
     * Send text to websocket
     *
     * @param message a String to send to client
     * @return itself
     */
    fun send(message: String): WebSocketClient {
        socket?.sendText(message)
        return this
    }

    /**
     * Send binary to websocket
     *
     * @param binary a binary to send to client
     * @return itself
     */
    fun send(binary: ByteArray): WebSocketClient {
        socket?.sendBinary(binary)
        return this
    }
}