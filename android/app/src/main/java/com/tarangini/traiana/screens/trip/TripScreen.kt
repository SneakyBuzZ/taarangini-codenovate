package com.tarangini.traiana.screens.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.BuildConfig
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppButton
import com.tarangini.traiana.components.ui.AppColumn
import com.tarangini.traiana.components.ui.AppImage
import com.tarangini.traiana.components.ui.ButtonVariant
import com.tarangini.traiana.components.ui.ColumnHorizontalPlacement

@Composable
fun TripScreen() {
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = Dimens.PaddingXS + 2.dp, vertical = Dimens.PaddingS)
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.Top
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .clip(MaterialTheme.shapes.medium)
        .border(1.dp, Colors.Neutral700, MaterialTheme.shapes.medium)
    ){
      AppImage(
        url = BuildConfig.TRIP_CONFIG,
        modifier = Modifier.fillMaxSize(),
      )
    }
    Spacer(modifier = Modifier.fillMaxWidth().height(Dimens.SpaceL))
    AppColumn(
      modifier = Modifier
        .fillMaxWidth(),
      horizontal = ColumnHorizontalPlacement.Start,
    ) {
      Text(
        text = "Your Snapshots",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(horizontal = Dimens.SpaceM)
      )
      Spacer(modifier = Modifier.fillMaxWidth().height(Dimens.SpaceS))
      AppColumn(
        modifier = Modifier
          .fillMaxWidth()
          .height(Dimens.HeightM)
          .clip(MaterialTheme.shapes.medium)
          .border(1.dp, Colors.Neutral700, MaterialTheme.shapes.medium)
          .background(MaterialTheme.colorScheme.surface)
          .padding(horizontal = Dimens.PaddingXL)
      ){
        Text(
          text = "Please upload a snapshot",
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 15.sp
          ),
        )
        Spacer(modifier = Modifier.padding(Dimens.SpaceS))
        AppButton(
          text = "Upload Snapshot",
          onClick = {},
          variant = ButtonVariant.Bright,
        )
      }
    }
  }
}