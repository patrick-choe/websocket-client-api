package com.github.patrick.websocket.event

import com.neovisionaries.ws.client.WebSocket
import org.bukkit.event.Event

@Suppress("unused")
abstract class WebSocketEvent(val webSocket: WebSocket) : Event()