package com.tarangini.traiana.lib.api.wss

import android.util.Log
import com.airbnb.mvrx.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response
import org.json.JSONObject

// ----------------------
// 1️⃣ SOS Content Data Classes
// ----------------------
data class SosContent(
  val touristId: String,
  val name: String,
  val age: Int,
  val gender: String,
  val location: Location,
  val emergencyContact: EmergencyContact,
  val safetyScore: Int
) {
  data class Location(
    val latitude: Double,
    val longitude: Double
  )
  data class EmergencyContact(
    val name: String,
    val phone: String
  )
}

// ----------------------
// 2️⃣ Mavericks State
// ----------------------
data class SosState(
  val latestSos: SosContent? = null,
  val connected: Boolean = false
) : MavericksState

// ----------------------
// 3️⃣ ViewModel
// ----------------------
class SosViewModel(initialState: SosState) : MavericksViewModel<SosState>(initialState) {

  private var webSocket: WebSocket? = null

  fun connectWebSocket(token: String, socketUrl: String) {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val client = OkHttpClient()
        val request = Request.Builder()
          .url("$socketUrl?token=$token")
          .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

          override fun onOpen(ws: WebSocket, response: Response) {
            Log.d("TRIANA", "WebSocket connected")
            setState { copy(connected = true) }
          }

          override fun onMessage(ws: WebSocket, text: String) {
            try {
              val json = JSONObject(text)
              Log.d("TRIANA", "Received message: $json")
              val type = json.getString("type")
              if (type == "sos" || type == "alert") {
                val contentJson = json.getJSONObject("content")
                val locationJson = contentJson.getJSONObject("location")
                val emergencyJson = contentJson.getJSONObject("emergencyContact")

                val sos = SosContent(
                  touristId = contentJson.getString("touristId"),
                  name = contentJson.getString("name"),
                  age = contentJson.getInt("age"),
                  gender = contentJson.getString("gender"),
                  location = SosContent.Location(
                    latitude = locationJson.getDouble("latitude"),
                    longitude = locationJson.getDouble("longitude")
                  ),
                  emergencyContact = SosContent.EmergencyContact(
                    name = emergencyJson.getString("name"),
                    phone = emergencyJson.getString("phone")
                  ),
                  safetyScore = contentJson.getInt("safetyScore")
                )

                // Update state to trigger Compose recomposition
                setState { copy(latestSos = sos) }
              }
            } catch (e: Exception) {
              Log.e("SosViewModel", "Failed parsing SOS message: $e")
            }
          }

          override fun onClosing(ws: WebSocket, code: Int, reason: String) {
            Log.d("SosViewModel", "WebSocket closing: $reason")
            ws.close(code, reason)
            setState { copy(connected = false) }
          }

          override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
            Log.e("SosViewModel", "WebSocket failure: ${t.message}")
            setState { copy(connected = false) }
          }
        })

        // Shut down the OkHttp dispatcher when done
        client.dispatcher.executorService.shutdown()

      } catch (e: Exception) {
        Log.e("SosViewModel", "WebSocket connection error: ${e.message}")
      }
    }
  }

  fun disconnectWebSocket() {
    webSocket?.close(1000, "Manual close")
    webSocket = null
    setState { copy(connected = false) }
  }
}

data class SosEvent(
  val touristId: String,
  val name: String,
  val age: Int,
  val gender: String,
  val latitude: Double,
  val longitude: Double,
  val emergencyContactName: String,
  val emergencyContactPhone: String,
  val safetyScore: Int
)
