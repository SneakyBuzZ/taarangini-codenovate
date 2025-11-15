package com.tarangini.traiana.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens

@Composable
fun AppInput(
  label : String?,
  value : String,
  onValueChange: (String) -> Unit,
  modifier: Modifier= Modifier,
  placeHolder : String = "",
  isError : Boolean = false,
  enabled : Boolean = true,
){
  val borderColor = when {
    !enabled -> Colors.Neutral700
    isError -> MaterialTheme.colorScheme.error
    else -> Colors.Neutral700
  }

  val background = Colors.Neutral800

  Column (
    modifier = modifier,
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.spacedBy(6.dp),
  ){
    if(label != null){
      Text(
        text = label,
        style = MaterialTheme.typography.bodySmall.copy(
          color = Colors.Neutral300
        )
      )
    }
    BasicTextField(
      value =  value,
      onValueChange = onValueChange,
      enabled = enabled,
      singleLine = true,
      textStyle = MaterialTheme.typography.bodySmall.copy(
        fontSize = 16.sp
      ),
      modifier = Modifier
        .height(Dimens.HeightXS)
        .background(background, MaterialTheme.shapes.medium)
        .border(1.dp, borderColor, MaterialTheme.shapes.medium)
        .padding(horizontal = 12.dp),
      cursorBrush = SolidColor(Colors.Neutral500),
      decorationBox = {innerTextField ->
        Box(
          modifier = Modifier
            .fillMaxWidth(),
          contentAlignment = Alignment.CenterStart
        ) {
          if (value.isEmpty()) {
            Text(
              text = placeHolder,
              style = MaterialTheme.typography.bodySmall.copy(
                color = Colors.Neutral500
              )
            )
          }
          innerTextField()
        }
      },
    )

    Spacer(modifier = Modifier.height(10.dp))
  }

}