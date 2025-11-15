package com.tarangini.traiana.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
  modifier: Modifier = Modifier,
  label: String? = null,
  value: String,
  onValueChange: (String) -> Unit,
  placeHolder: String = "",
  isError: Boolean = false,
  enabled: Boolean = true,
) {
  val borderColor = when {
    !enabled -> Colors.Neutral600
    isError -> MaterialTheme.colorScheme.error
    else -> Colors.Neutral600
  }

  val background = Colors.Neutral800

  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.Start
  ) {
    if (!label.isNullOrBlank()) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodySmall.copy(
          color = Colors.Neutral300
        )
      )
      Spacer(modifier = Modifier.height(6.dp))
    }

    BasicTextField(
      value = value,
      onValueChange = onValueChange,
      enabled = enabled,
      singleLine = true,
      textStyle = MaterialTheme.typography.bodySmall.copy(
        fontSize = 16.sp
      ),
      modifier = Modifier
        .height(Dimens.HeightXXS)
        .background(background, MaterialTheme.shapes.medium)
        .border(1.dp, borderColor, MaterialTheme.shapes.medium)
        .padding(horizontal = 12.dp),
      cursorBrush = SolidColor(Colors.Neutral500),
      decorationBox = { innerTextField ->
        Box(
          modifier = Modifier.fillMaxWidth(),
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
      }
    )
  }
}
