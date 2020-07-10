package com.github.patrick.websocket

import com.github.patrick.websocket.exception.WebSocketNoResponseException
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory
import javax.net.ssl.SSLContext

@Suppress("unused")
class WebSocketClient(url: String, adapter: WebSocketAdapter, tls: Boolean) {
    var socket: WebSocket
        private set
    private var checked = true

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
            throw WebSocketNoResponseException()
            checked = false
        }
    }

    fun connect(): Boolean {
        return if (checked) {
            try {
                socket.connect()
                true
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                false
            }
        } else {
            false
        }
    }

    fun disconnect() {
        socket.disconnect()
    }
}