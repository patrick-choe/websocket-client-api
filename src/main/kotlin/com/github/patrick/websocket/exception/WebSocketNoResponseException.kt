package com.github.patrick.websocket.exception

/**
 * Thrown when websocket has no response
 *
 * @param message message to throw
 */
class WebSocketNoResponseException(message: String) : Exception(message)