package com.tarangini.traiana.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppButton
import com.tarangini.traiana.components.ui.AppColumn
import com.tarangini.traiana.components.ui.AppInput
import com.tarangini.traiana.components.ui.ButtonVariant

@Composable
fun GetSafeRouteCard(){
    AppColumn(
      modifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .border(1.dp, Colors.Neutral700, MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.surface)
        .padding(horizontal = Dimens.PaddingS, vertical = Dimens.PaddingM)
    ){
      AppInput(
        label = "Location",
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
      )
      Spacer(modifier = Modifier.padding(Dimens.SpaceS))
      AppInput(
        label = "Destination",
        value = "",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
      )
      Spacer(modifier = Modifier.padding(Dimens.SpaceS))
      Text(
        text = "Our safety can be wronged calculated, please be alert if near any danger",
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 10.sp
        ),
      )
      Spacer(modifier = Modifier.padding(Dimens.SpaceXS))
      AppButton(
        text = "Get Safe Route",
        onClick = {},
        variant = ButtonVariant.Bright
        ,
      )
    }
}