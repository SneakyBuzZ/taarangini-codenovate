package com.tarangini.traiana.screens.splash

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tarangini.traiana.R
import com.tarangini.traiana.components.layout.LocalNavController
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Manrope
import com.tarangini.traiana.utils.RetrofitInstance
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
  val navController = LocalNavController.current
  var visible by remember { mutableStateOf(true) }
  val systemUiController = rememberSystemUiController()

  LaunchedEffect(Unit) {
    systemUiController.isStatusBarVisible = false
    systemUiController.isNavigationBarVisible = false
    try {
      delay(1000)
      RetrofitInstance.api.getAuthStatus()
      visible = false

      systemUiController.isStatusBarVisible = true
      systemUiController.isNavigationBarVisible = true

      delay(350)
      navController.navigate("home") {
        popUpTo("splash") { inclusive = true }
      }
    } catch (e: Exception) {
      Log.d("TRIANA", e.message.toString())
      visible = false

      systemUiController.isStatusBarVisible = true
      systemUiController.isNavigationBarVisible = true

      delay(350)
      navController.navigate("onboard") {
        popUpTo("splash") { inclusive = true }
      }
    }
  }

  AnimatedVisibility(
    visible = visible,
    enter = fadeIn(),
    exit = fadeOut()
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Colors.Neutral950),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Image(
          painter = painterResource(id = R.drawable.ic_logo),
          contentDescription = "App Logo",
          modifier = Modifier.size(width = 220.dp, height = 100.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
          text = "Safe travel with Traiana",
          style = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = Manrope,
            color = Colors.Neutral400
          )
        )
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator(color = Colors.Neutral400)
      }
    }
  }
}
