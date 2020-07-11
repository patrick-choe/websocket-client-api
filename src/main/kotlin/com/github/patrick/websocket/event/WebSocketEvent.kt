package com.github.patrick.websocket.event

import com.neovisionaries.ws.client.WebSocket
import org.bukkit.event.Event

/**
 * Websocket event abstract class
 *
 * @param websocket event's websocket
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class WebSocketEvent(val websocket: WebSocket) : Event()