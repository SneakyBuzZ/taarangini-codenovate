package com.tarangini.traiana.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tarangini.traiana.components.theme.Colors
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.sp

@Composable
fun AppSelect(
  label: String? = null,
  options: List<String>,
  selectedOption: String,
  onOptionSelected: (String) -> Unit,
  modifier: Modifier = Modifier,
  isError: Boolean = false,
  enabled: Boolean = true,
  placeHolder: String = ""
) {
  val borderColor = when {
    !enabled -> Colors.Neutral700
    isError -> MaterialTheme.colorScheme.error
    else -> Colors.Neutral700
  }

  val background = Colors.Neutral800

  var expanded by remember { mutableStateOf(false) }
  var columnSize by remember { mutableStateOf(IntSize.Zero) }

  Column(
    modifier = modifier
      .onGloballyPositioned { coordinates ->
        columnSize = coordinates.size
      },
    verticalArrangement = Arrangement.spacedBy(6.dp),
    horizontalAlignment = Alignment.Start,
  ) {
    if (label != null) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodySmall.copy(
          color = Colors.Neutral300
        )
      )
    }

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(background, MaterialTheme.shapes.medium)
        .border(1.dp, borderColor, MaterialTheme.shapes.medium)
    ) {
      BasicTextField(
        value = selectedOption,
        onValueChange = { },
        readOnly = true,
        enabled = enabled,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall.copy(
          color = if (enabled) Colors.Neutral400 else Colors.Neutral600,
          fontSize = 16.sp
        ),
        decorationBox = { innerTextField ->
          Row(
            modifier = Modifier
              .padding(horizontal = 12.dp)
              .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Box(modifier = Modifier.weight(1f)) {
              if (selectedOption.isEmpty()) {
                Text(
                  text = placeHolder,
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = Colors.Neutral500
                  )
                )
              }
              innerTextField()
            }
            IconButton(onClick = { expanded = !expanded }) {
              Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Colors.Neutral300
              )
            }
          }
        }
      )

      DropdownMenu(
        modifier = Modifier
          .width(with(LocalDensity.current) { columnSize.width.toDp() })
          .background(background)
          .clip(MaterialTheme.shapes.medium),
        expanded = expanded,
        onDismissRequest = { expanded = false },
        offset = DpOffset(x = 0.dp, y = 4.dp)
      ) {
        options.forEach { option ->
          DropdownMenuItem(
            text = {
              Text(
                option,
                style = MaterialTheme.typography.bodySmall.copy(color = Colors.Neutral400)
              )
            },
            onClick = {
              onOptionSelected(option)
              expanded = false
            }
          )
        }
      }
    }
  }
}
