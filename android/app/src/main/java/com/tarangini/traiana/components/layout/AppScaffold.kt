package com.tarangini.traiana.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.tarangini.traiana.components.shared.PanicButton
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.lib.animations.enterTransition
import com.tarangini.traiana.lib.animations.exitTransition
import com.tarangini.traiana.screens.alerts.AlertsScreen
import com.tarangini.traiana.screens.emergency.EmergencyScreen
import com.tarangini.traiana.screens.home.HomeScreen
import com.tarangini.traiana.screens.onboarding.MultistepNavigator
import com.tarangini.traiana.screens.profile.ProfileSheet
import com.tarangini.traiana.screens.splash.SplashScreen

val LocalNavController = staticCompositionLocalOf<NavController> {
  error("No NavController found")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    topBar = {
      when (currentRoute) {
        "onboard" -> {
          TopAppBar(
            title = { Text("User Onboarding") },
            colors = TopAppBarDefaults.topAppBarColors(
              containerColor = MaterialTheme.colorScheme.background,
              titleContentColor = MaterialTheme.colorScheme.onSurface
            )
          )
        }
        "splash" -> Unit
        else -> AppTopbar(navController)
      }
    }
  ) { innerPadding ->
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
            startDestination = "splash",
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = enterTransition(),
            popExitTransition = exitTransition()
          ) {
            composable("splash") { SplashScreen() }
            composable("home") { HomeScreen() }
            composable("onboard") { MultistepNavigator() }
            composable("emergency") { EmergencyScreen() }
            composable("profile") { ProfileSheet() }
            composable("alerts") { AlertsScreen() }
          }
        }
      }

      if (currentRoute != "onboard" && currentRoute != "splash") {
        PanicButton(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(Dimens.PaddingS)
        )
      }
    }
  }
}
