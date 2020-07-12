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
import com.github.patrick.websocket.ssl.WebSocketSSLContext
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory

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
                    sslContext = WebSocketSSLContext.getInstance("TLS")
                    verifyHostname = false
                }
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