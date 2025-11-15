package com.tarangini.traiana.utils

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class MobileWebSocketManager(
  private val url: String
) : WebSocketListener() {

  private val client = OkHttpClient()
  private var webSocket: WebSocket? = null

  fun connect() {
    val request = Request.Builder()
      .url(url)
      .build()

    webSocket = client.newWebSocket(request, this)
  }

  fun sendMessage(message: String) {
    webSocket?.send(message)
  }

  fun close() {
    webSocket?.close(1000, "Closed by user")
  }

  override fun onOpen(webSocket: WebSocket, response: Response) {
    Log.d("WEBSOCKET", "Connection opened on ${response.request.url}")
  }

  override fun onMessage(webSocket: WebSocket, text: String) {
    Log.d("WEBSOCKET", "Received message: $text")
  }

  override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    Log.d("WEBSOCKET", "Connection failed: ${t.message}")
  }

  override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    Log.d("WEBSOCKET", "Connection closing: $code $reason")

  }
}