package com.tarangini.traiana.screens.emergency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppColumn
import com.tarangini.traiana.components.ui.ColumnHorizontalPlacement

@Composable
fun EmergencyScreen(){
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = Dimens.PaddingXS + 2.dp, vertical = Dimens.PaddingM)
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.Top
  ) {
    AppColumn(
      modifier = Modifier
        .fillMaxWidth(),
      horizontal = ColumnHorizontalPlacement.Start,
    ) {
      Text(
        text = "Police Help",
        style = MaterialTheme.typography.headlineSmall.copy(
          fontSize = 15.sp
        ),
        modifier = Modifier.padding(horizontal = Dimens.SpaceM)
      )
      Spacer(modifier = Modifier.fillMaxWidth().height(Dimens.SpaceS))
      PoliceStationsCard()
    }
    Spacer(modifier = Modifier.fillMaxWidth().height(Dimens.SpaceM))
    AppColumn(
      modifier = Modifier
        .fillMaxWidth(),
      horizontal = ColumnHorizontalPlacement.Start,
    ) {
      Text(
        text = "Nearest Aids",
        style = MaterialTheme.typography.headlineSmall.copy(
          fontSize = 15.sp
        ),
        modifier = Modifier.padding(horizontal = Dimens.SpaceM)
      )
      Spacer(modifier = Modifier.fillMaxWidth().height(Dimens.SpaceS))
      NearestAidsCard()
    }
    Spacer(modifier = Modifier.fillMaxWidth().height(Dimens.SpaceM))
    AppColumn(
      modifier = Modifier
        .fillMaxWidth(),
      horizontal = ColumnHorizontalPlacement.Start,
    ) {
      Text(
        text = "Emergency Guides",
        style = MaterialTheme.typography.headlineSmall.copy(
          fontSize = 15.sp
        ),
        modifier = Modifier.padding(horizontal = Dimens.SpaceM)
      )
      Spacer(modifier = Modifier.fillMaxWidth().height(Dimens.SpaceS))
      EmergencyGuides()
    }
    Spacer(modifier = Modifier.height(Dimens.HeightM))
  }
}