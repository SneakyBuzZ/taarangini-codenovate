package com.tarangini.traiana.screens.emergency

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppButton
import com.tarangini.traiana.components.ui.ButtonVariant
import com.tarangini.traiana.utils.SvgLoader

@Composable
fun EmergencyScreen(){
  Column (
    modifier = Modifier
      .fillMaxSize()
      .padding(Dimens.PaddingS),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top
  ) {
    Row (
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Colors.Neutral700, MaterialTheme.shapes.medium)
        .clip(MaterialTheme.shapes.medium)
        .height(200.dp)
        .background(MaterialTheme.colorScheme.surface)
        .padding(Dimens.PaddingXS)
    ){
      SvgLoader(
        url = "https://i.pinimg.com/736x/97/9c/b3/979cb3410838984a57a5c1f7f41bde3e.jpg",
        modifier = Modifier
          .fillMaxHeight()
          .width(200.dp)
          .clip(MaterialTheme.shapes.medium)
      )
      Column (
        modifier = Modifier
          .fillMaxSize()
          .padding(Dimens.PaddingS),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
      ){
        Text(
          text = "Hotel Shaadan",
          style = MaterialTheme.typography.bodyMedium
        )
        Text(
          text = "Emergency",
          style = MaterialTheme.typography.bodySmall
        )
        AppButton(
          text= " book",
          onClick = {},
          variant = ButtonVariant.Bright,
          modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HeightXS)
            .padding(top = Dimens.PaddingS)
        )
      }
    }
  }
}