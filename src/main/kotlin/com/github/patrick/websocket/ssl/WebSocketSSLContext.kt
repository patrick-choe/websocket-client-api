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

package com.github.patrick.websocket.ssl

import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/**
 * This class is motivated from [GitHub Gist by TakahikoKawasaki](https://gist.github.com/TakahikoKawasaki/d07de2218b4b81bf65ac)
 */
internal object WebSocketSSLContext {
    /**
     * Get an SSLContext without verification.
     */
    @Throws(RuntimeException::class)
    fun getInstance(protocol: String?): SSLContext {
        try {
            return SSLContext.getInstance(protocol).apply {
                init(null, arrayOf(WebSocketTrustManager()), null)
            }
        } catch (exception: Exception) {
            throw RuntimeException("Failed to initialize SSLContext.", exception)
        }
    }

    /**
     * Trusts all certificates without verification.
     */
    private class WebSocketTrustManager : X509TrustManager {
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}

        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}

        override fun getAcceptedIssuers(): Array<X509Certificate>? {
            return null
        }
    }
}