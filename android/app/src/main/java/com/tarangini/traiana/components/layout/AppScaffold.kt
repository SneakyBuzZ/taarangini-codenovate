package com.tarangini.traiana.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tarangini.traiana.components.shared.PanicButton
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.screens.emergency.EmergencyScreen
import com.tarangini.traiana.screens.home.HomeScreen
import com.tarangini.traiana.screens.onboarding.MultistepNavigator
import com.tarangini.traiana.screens.profile.ProfileSheet
import com.tarangini.traiana.screens.splash.SplashScreen
import com.tarangini.traiana.utils.MobileWebSocketManager
import com.tarangini.traiana.utils.SecureTokenManager

val LocalNavController = staticCompositionLocalOf <NavController>{
  error("No NavController found")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(){
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  Scaffold (
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    topBar = {
      when(currentRoute){
        "home" -> {
          AppTopbar(navController)
        }
        "emergency" -> {
          AppTopbar(navController)
        }
        "onboard" -> {
          TopAppBar(
            title = { Text("User Onboarding") },
            colors = TopAppBarDefaults.topAppBarColors(
              containerColor = MaterialTheme.colorScheme.background,
              titleContentColor = MaterialTheme.colorScheme.onSurface
            ),
          )
        }
      }
    },
  ){ innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
      Column(
        modifier = Modifier
          .padding(innerPadding)
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        CompositionLocalProvider(LocalNavController provides navController) {
          NavHost(
            navController = navController,
            startDestination = "splash"
          ) {
            composable("splash") { SplashScreen() }
            composable("home") { HomeScreen() }
            composable("onboard") { MultistepNavigator() }
            composable("emergency") { EmergencyScreen() }
            composable("profile") { ProfileSheet() }
          }
        }
      }

      if(currentRoute != "onboard" && currentRoute != "splash"){
        PanicButton(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(Dimens.PaddingS),
        )
      }
    }
  }
}