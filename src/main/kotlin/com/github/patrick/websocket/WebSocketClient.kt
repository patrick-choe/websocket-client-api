/*
 * Copyright (C) 2020 PatrickKR
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Contact me on <mailpatrickkr@gmail.com>
 */

package com.github.patrick.websocket

import com.github.patrick.websocket.exception.WebSocketNoResponseException
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/**
 * This class represents the client itself.
 *
 * @param url the websocket url
 * @param adapter the adapter to handle this client
 * @param tls whether to use tls support
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class WebSocketClient internal constructor(val url: String, val adapter: WebSocketAdapter, val tls: Boolean = false, val suppress: Boolean = false) {
    lateinit var socket: WebSocket

    var checked = true
        private set

    init {
        try {
            socket = WebSocketFactory().apply {
                if (tls) {
                    sslContext = try {
                        SSLContext.getInstance("TLS").apply {
                            init(null, arrayOf(object : X509TrustManager {
                                override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}

                                override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}

                                override fun getAcceptedIssuers(): Array<X509Certificate>? {
                                    return null
                                }
                            }), null)
                        }
                    } catch (exception: Exception) {
                        throw RuntimeException("Failed to initialize SSLContext", exception)
                    }

                    verifyHostname = false
                }
            }.createSocket(url).apply {
                addListener(adapter)
            }
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
        return if (checked) {
            try {
                require(this::socket.isInitialized)

                socket.connect()

                true
            } catch (throwable: Throwable) {
                if (!suppress) {
                    throwable.printStackTrace()
                }

                false
            }
        } else false
    }

    /**
     * Reconnect to websocket
     *
     * @return true if reconnection is successful
     */
    fun reconnect(): Boolean {
        require(this::socket.isInitialized)

        runCatching {
            socket.disconnect()
        }

        return try {
            val client = WebSocketAPI.createUnsafeWebSocket(url, tls, suppress)
            socket = client.socket
            client.connect()
        } catch (throwable: Throwable) {
            if (!suppress) {
                throwable.printStackTrace()
            }

            false
        }
    }

    /**
     * Send text to websocket
     *
     * @param message a String to send to client
     * @return itself
     */
    fun send(message: String): WebSocketClient {
        require(this::socket.isInitialized)

        socket.sendText(message)

        return this
    }

    /**
     * Send binary to websocket
     *
     * @param binary a binary to send to client
     * @return itself
     */
    fun send(binary: ByteArray): WebSocketClient {
        require(this::socket.isInitialized)

        socket.sendBinary(binary)

        return this
    }
}