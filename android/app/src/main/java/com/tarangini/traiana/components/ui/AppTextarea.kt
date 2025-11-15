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

@Composable
fun AppTextarea(
  label: String?,
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  placeHolder: String = "",
  isError: Boolean = false,
  enabled: Boolean = true,
  minLines: Int = 2,
  maxLines: Int = 8,
) {
  val borderColor = when {
    !enabled -> Colors.Neutral700
    isError -> MaterialTheme.colorScheme.error
    else -> Colors.Neutral700
  }

  val background = Colors.Neutral800

  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.spacedBy(6.dp),
  ) {
    if (label != null) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodySmall.copy(
          color = Colors.Neutral300
        )
      )
    }
    BasicTextField(
      value = value,
      onValueChange = onValueChange,
      enabled = enabled,
      singleLine = false,
      minLines = minLines,
      maxLines = maxLines,
      textStyle = MaterialTheme.typography.bodySmall.copy(
        fontSize = 16.sp
      ),
      modifier = Modifier
        .heightIn(min = 60.dp, max = 120.dp)
        .background(background, MaterialTheme.shapes.medium)
        .border(1.dp, borderColor, MaterialTheme.shapes.medium)
        .padding(horizontal = 12.dp, vertical = 10.dp)
        .fillMaxWidth(),
      cursorBrush = SolidColor(Colors.Neutral500),
      decorationBox = { innerTextField ->
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.TopStart
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
