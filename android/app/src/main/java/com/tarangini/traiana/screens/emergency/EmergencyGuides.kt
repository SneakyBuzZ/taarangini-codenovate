package com.tarangini.traiana.screens.emergency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.tarangini.traiana.R
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppColumn
import com.tarangini.traiana.components.ui.AppDivider
import com.tarangini.traiana.components.ui.ColumnHorizontalPlacement
import com.tarangini.traiana.components.ui.DividerOrientation
import com.tarangini.traiana.components.ui.DividerStyle

@Composable
fun EmergencyGuides() {
  AppColumn(
    modifier = Modifier
      .fillMaxWidth()
      .clip(MaterialTheme.shapes.medium)
      .background(MaterialTheme.colorScheme.surface)
      .padding(Dimens.PaddingXS),
    horizontal = ColumnHorizontalPlacement.Start
  ) {
    GuideRow(
      title = "Fainting: What to Do",
      description = "Check breathing • Raise legs • Loosen clothing",
      leadingIconRes = R.drawable.ic_first_aid,
      leftIconTint = Colors.CoralRed100.copy(alpha = 0.3f),
      onClick = { }
    )
    AppDivider(
      orientation = DividerOrientation.Horizontal,
      style = DividerStyle.Dotted,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.SpaceS)
    )

    GuideRow(
      title = "Road Accident Guide",
      description = "Check injuries • Move to safety • Call emergency",
      leadingIconRes = R.drawable.ic_bus_front,
      onClick = { }
    )
    AppDivider(
      orientation = DividerOrientation.Horizontal,
      style = DividerStyle.Dotted,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.SpaceS)
    )

    GuideRow(
      title = "Panic Attack Help",
      description = "Slow breaths • Grounding technique • Reassure",
      leadingIconRes = R.drawable.ic_waves,
      leftIconTint = Colors.CoralRed300.copy(alpha = 0.4f),
      onClick = { }
    )
    AppDivider(
      orientation = DividerOrientation.Horizontal,
      style = DividerStyle.Dotted,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.SpaceS)
    )

    GuideRow(
      title = "Snake Bite Steps",
      description = "Stay still • Keep limb lowered • No cutting/sucking",
      leadingIconRes = R.drawable.ic_snake,
      leftIconTint = Colors.CoralGreen200.copy(alpha = 0.2f),
      onClick = { }
    )
    AppDivider(
      orientation = DividerOrientation.Horizontal,
      style = DividerStyle.Dotted,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.SpaceS)
    )

    GuideRow(
      title = "Unconscious Person",
      description = "Check pulse • Put in recovery position • Stay nearby",
      leadingIconRes = R.drawable.ic_brain_not,
      onClick = { }
    )
    AppDivider(
      orientation = DividerOrientation.Horizontal,
      style = DividerStyle.Dotted,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.SpaceS)
    )
    GuideRow(
      title = "Fire Emergency Basics",
      description = "Stop-Drop-Roll • Avoid smoke • Keep low",
      leadingIconRes = R.drawable.ic_fire,
      leftIconTint = Colors.CoralAmber300.copy(alpha = 0.6f),
      onClick = { }
    )
    AppDivider(
      orientation = DividerOrientation.Horizontal,
      style = DividerStyle.Dotted,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = Dimens.SpaceS)
    )
    GuideRow(
      title = "Severe Bleeding",
      description = "Apply pressure • Elevate area • Avoid removing cloth",
      leadingIconRes = R.drawable.ic_blood,
      leftIconTint = Colors.CoralRed200.copy(alpha = 0.6f),
      onClick = { }
    )
  }
}