package com.tarangini.traiana.screens.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppButton
import com.tarangini.traiana.components.ui.AppInput
import com.tarangini.traiana.components.ui.AppRow
import com.tarangini.traiana.R
import com.tarangini.traiana.components.ui.BadgeVariant

@Composable
fun AlertsScreen(){
  val scrollState = rememberScrollState()
  var searchInput by remember { mutableStateOf("") }

  val alerts = listOf(
    AlertItem(
      title = "Air Quality Alert",
      description = "AQI levels have risen to hazardous levels due to post-Diwali smog.",
      date = "25 Sept.",
      category = "Environment",
      severity = "High",
      severityVariant = BadgeVariant.Error,
      imageUrl = "https://www.en.etemaaddaily.com/pages/world/hyderabad/540citypolice.jpg"
    ),
    AlertItem(
      title = "Traffic Disruption",
      description = "Heavy congestion expected near Necklace Road due to festival rally.",
      date = "27 Sept.",
      category = "Transport",
      severity = "Medium",
      severityVariant = BadgeVariant.Info,
      imageUrl = "https://images.unsplash.com/photo-1508921912186-1d1a45ebb3c1"
    ),
    AlertItem(
      title = "Rainfall Warning",
      description = "IMD forecasts heavy rainfall across Hyderabad in the next 24 hours.",
      date = "29 Sept.",
      category = "Weather",
      severity = "High",
      severityVariant = BadgeVariant.Error,
      imageUrl = "https://images.unsplash.com/photo-1503264116251-35a269479413"
    )
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = Dimens.PaddingXS + 2.dp, vertical = Dimens.PaddingS)
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.Top
  ) {
    AppRow(
      modifier = Modifier.fillMaxWidth()
    ){
      AppInput(
        value = searchInput,
        onValueChange = { searchInput = it },
        placeHolder = "Search",
        modifier = Modifier.weight(1f)
      )
      Spacer(modifier = Modifier.width(8.dp))
      AppButton(
        iconResId = R.drawable.ic_filter,
        onClick = {},
      )
    }
    Spacer(modifier = Modifier.height(Dimens.PaddingS))

    alerts
      .filter { it.title.contains(searchInput, ignoreCase = true) }
      .forEach { alert ->
        AlertCard(alert = alert)
        Spacer(modifier = Modifier.height(Dimens.PaddingS))
      }
    Spacer(modifier = Modifier.height(Dimens.HeightM))
  }
}