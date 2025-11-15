package com.tarangini.traiana.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.airbnb.mvrx.compose.mavericksViewModel
import com.tarangini.traiana.components.layout.LocalNavController
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppButton
import com.tarangini.traiana.components.ui.AppRow
import com.tarangini.traiana.components.ui.RowLayout
import com.tarangini.traiana.lib.api.user.UserViewModel

@Composable
fun HomeScreen() {
  val context = LocalContext.current
  var hasPermission by remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()
  val navController = LocalNavController.current
  val userViewModel : UserViewModel = mavericksActivityViewModel()

  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions()
  ) { permissions ->
    hasPermission = permissions.values.all { it }
    if (!hasPermission) Toast.makeText(context, "Location denied", Toast.LENGTH_LONG).show()
  }

  LaunchedEffect(Unit) {
    if (locationPermissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED })
      hasPermission = true
    else launcher.launch(locationPermissions)
    userViewModel.getUser()
  }

  fun handleClick(){
    Log.d("CLICK","HAPPENED")
    navController.navigate("profile")
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(Dimens.PaddingS)
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.Top
  ) {
    MapCard(
      hasPermission = hasPermission,
      modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .clip(MaterialTheme.shapes.medium)
        .border(1.dp, Colors.Neutral700, MaterialTheme.shapes.medium)
    )
    Spacer(modifier = Modifier.height(Dimens.PaddingS))
    AppRow (
      layout = RowLayout.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
        .border(1.dp, Colors.Neutral600, MaterialTheme.shapes.medium)
        .height(Dimens.HeightL)
        .padding(Dimens.PaddingXS)
    ) {
      Box(
        modifier = Modifier
          .weight(0.8f)
      ){
        SafetyScoreCard(
          score = 85,
          lastUpdated = "2 min ago"
        )
      }
      val detailsList = listOf(
        Pair("Nearby Safe Zones", "3"),
        Pair("Nearby Alerts", "2"),
        Pair("Nearby Risky Zones", "1"),
        Pair("Police Stations", "0")
      )
      ScoreDetails(
        detailsList = detailsList,
        modifier = Modifier
          .weight(1f),
      )
    }
  }
}

private val locationPermissions = arrayOf(
  Manifest.permission.ACCESS_FINE_LOCATION,
  Manifest.permission.ACCESS_COARSE_LOCATION
)
