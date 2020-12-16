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

import com.github.patrick.websocket.event.WebSocketBinaryEvent
import com.github.patrick.websocket.event.WebSocketConnectedEvent
import com.github.patrick.websocket.event.WebSocketDisconnectedEvent
import com.github.patrick.websocket.event.WebSocketEvent
import com.github.patrick.websocket.event.WebSocketMessageEvent
import com.github.patrick.websocket.plugin.WebSocketPlugin
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFrame
import org.bukkit.Bukkit

/**
 * Handles websocket events
 */
internal class WebSocketHandler : WebSocketAdapter() {
    /**
     * On connect
     */
    override fun onConnected(socket: WebSocket, headers: Map<String, List<String>>) {
        callEvent(WebSocketConnectedEvent(socket, headers))
    }

    /**
     * On disconnect
     */
    override fun onDisconnected(socket: WebSocket, serverCloseFrame: WebSocketFrame, clientCloseFrame: WebSocketFrame, closedByServer: Boolean) {
        callEvent(WebSocketDisconnectedEvent(socket, closedByServer))
    }

    /**
     * On message receive
     */
    override fun onTextMessage(socket: WebSocket, message: String) {
        callEvent(WebSocketMessageEvent(socket, message))
    }

    /**
     * On binary receive
     */
    override fun onBinaryMessage(socket: WebSocket, binary: ByteArray) {
        callEvent(WebSocketBinaryEvent(socket, binary))
    }

    private fun callEvent(event: WebSocketEvent) {
        Bukkit.getScheduler().callSyncMethod(WebSocketPlugin.INSTANCE) {
            Bukkit.getServer().pluginManager.callEvent(event)
        }.get()
    }
}