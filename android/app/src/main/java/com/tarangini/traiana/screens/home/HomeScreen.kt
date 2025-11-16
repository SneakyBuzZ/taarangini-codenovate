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
import com.tarangini.traiana.BuildConfig
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.tarangini.traiana.components.layout.LocalNavController
import com.tarangini.traiana.components.shared.AlertDialogListener
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppBadge
import com.tarangini.traiana.components.ui.AppRow
import com.tarangini.traiana.components.ui.BadgeVariant
import com.tarangini.traiana.components.ui.RowHorizontalPlacement
import com.tarangini.traiana.lib.api.user.UserViewModel
import com.tarangini.traiana.lib.api.wss.SosViewModel

@Composable
fun HomeScreen() {
  val context = LocalContext.current
  var hasPermission by remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()
  val navController = LocalNavController.current
  val userViewModel : UserViewModel = mavericksActivityViewModel()
  val sosViewModel : SosViewModel = mavericksActivityViewModel()

  //parameters
  val safetyScoreValue by remember { mutableIntStateOf(82) }
  val safetyScoreTimeValue by remember { mutableStateOf("10 min ago") }

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

    sosViewModel.connectWebSocket(
      token = BuildConfig.TOKEN,
      socketUrl = BuildConfig.SOCKET_URL
    )
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
    AppRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.PaddingXS),
      horizontal = RowHorizontalPlacement.Start
    ) {
      filterBadgeList.map {
        AppBadge(
          text = it.first,
          variant = it.second,
          onClick = {}
        )
        Spacer(modifier = Modifier
          .padding(Dimens.SpaceXS))
      }
    }

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
      modifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
        .border(1.dp, Colors.Neutral700, MaterialTheme.shapes.medium)
        .height(Dimens.HeightL)
        .padding(Dimens.PaddingXS),
      horizontal = RowHorizontalPlacement.SpaceBetween
    ) {
      Box(
        modifier = Modifier
          .weight(0.8f)
      ){
        SafetyScoreCard(
          score = safetyScoreValue,
          lastUpdated = safetyScoreTimeValue
        )
      }

      ScoreDetails(
        detailsList = detailsList,
        modifier = Modifier
          .weight(1f),
      )
    }
    Spacer(modifier = Modifier.height(Dimens.PaddingS))
    GetSafeRouteCard()
    Spacer(modifier = Modifier.height(Dimens.HeightS))

    AlertDialogListener(sosViewModel)
  }
}

private val locationPermissions = arrayOf(
  Manifest.permission.ACCESS_FINE_LOCATION,
  Manifest.permission.ACCESS_COARSE_LOCATION
)

val filterBadgeList = listOf(
  Pair("Time", BadgeVariant.Info),
  Pair("Safety", BadgeVariant.Success),
  Pair("Lights", BadgeVariant.Outline),
)

val detailsList = listOf(
  Pair("Nearby Safe Zones", "1"),
  Pair("Nearby Alerts", "3"),
  Pair("Nearby Risky Zones", "2"),
  Pair("Police Stations", "4")
)
