package com.tarangini.traiana.components.shared

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.google.gson.JsonObject
import com.tarangini.traiana.BuildConfig
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.lib.api.user.UserViewModel
import com.tarangini.traiana.utils.GetLocation
import com.tarangini.traiana.utils.MobileWebSocketManager
import com.tarangini.traiana.utils.SecureTokenManager
import java.time.LocalDate
import java.time.Period

@Composable
fun PanicButton(modifier: Modifier = Modifier) {
  val token = SecureTokenManager.getToken(LocalContext.current)
  val socketUrl = BuildConfig.SOCKET_URL
  val wsManager = remember { MobileWebSocketManager("$socketUrl?token=$token") }
  val userViewModel: UserViewModel = mavericksActivityViewModel()
  val userState by userViewModel.collectAsState { it.getUserRequest }
  val location by userViewModel.collectAsState { it.currentLocation }
  val context = LocalContext.current

  LaunchedEffect(Unit) {
    wsManager.connect()
    userViewModel.updateCurrentLocation(context)
  }

  DisposableEffect(Unit) {
    onDispose { wsManager.close() }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  fun handleClick() {
    val user = userState.invoke() ?: return

    val age = try {
      val dob = LocalDate.parse(user.dob.substring(0, 10))
      Period.between(dob, LocalDate.now()).years
    } catch (e: Exception) {
      0
    }

    Log.d("TRIANA", "Location: $location")
    val locationJson = JsonObject().apply {
      addProperty("latitude", 78.453)
      addProperty("longitude", 17.412)
    }

    val emergencyContactJson = JsonObject().apply {
      addProperty("name", "Samee")
      addProperty("number", "9876543210")
    }

    val contentJson = JsonObject().apply {
      addProperty("touristId", user.touristId)
      addProperty("name", user.fullname)
      addProperty("age", age)
      addProperty("gender", user.gender)
      add("location", locationJson)
      add("emergencyContact", emergencyContactJson)
      addProperty("safetyScore", 42)
    }

    val json = JsonObject().apply {
      addProperty("type", "sos")
      add("content", contentJson)
      addProperty("timestamp", System.currentTimeMillis())
    }

    wsManager.sendMessage(json.toString())
  }

  Surface(
    modifier = modifier
      .size(54.dp)
      .border(1.dp, Colors.CoralRed300, MaterialTheme.shapes.large)
      .clickable { handleClick() },
    shape = MaterialTheme.shapes.large,
    color = Colors.CoralRed100.copy(alpha = 0.6f),
    tonalElevation = 8.dp,
    shadowElevation = 8.dp,
  ) {
    Box(contentAlignment = Alignment.Center) {
      Text(
        text = "SOS",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )
    }
  }
}
