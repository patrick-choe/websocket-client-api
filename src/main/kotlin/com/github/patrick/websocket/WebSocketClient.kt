package com.github.patrick.websocket

import com.github.patrick.websocket.exception.WebSocketNoResponseException
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory
import javax.net.ssl.SSLContext

@Suppress("unused")
class WebSocketClient(url: String, adapter: WebSocketAdapter, tls: Boolean) {
    private var webSocket: WebSocket
    private var flag = true

    init {
        try {
            webSocket = WebSocketFactory().apply {
                if (tls) {
                    sslContext = SSLContext.getInstance("TLS").apply {
                        init(null, null, null)
                    }
                }
                verifyHostname = false
            }.createSocket(url).apply {
                addListener(adapter)
            }
            flag = true
        } catch (throwable: Throwable) {
            throw WebSocketNoResponseException()
            flag = false
        }
    }

    fun connect(): Boolean {
        return if (flag) {
            try {
                webSocket.connect()
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
        webSocket.disconnect()
    }
}