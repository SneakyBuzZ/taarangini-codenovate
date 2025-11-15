package com.tarangini.traiana.screens.home

import android.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppRow
import com.tarangini.traiana.components.ui.RowLayout

@Composable
fun ScoreDetails(
  modifier: Modifier = Modifier,
  detailsList : List<Pair<String, String>>
) {
  Column(
    modifier = modifier
      .fillMaxHeight(),
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    detailsList.forEachIndexed { index, pair ->
      val canvasColor = when(index){
        0 -> Colors.CoralGreen300
        1 -> Colors.CoralAmber100
        2 -> Colors.CoralRed200
        else -> Colors.CoralBlue100
      }
      AppRow(
        layout = RowLayout.SpaceEvenly,
        modifier = Modifier
          .fillMaxWidth()
          .height(20.dp)
          .padding(horizontal = Dimens.PaddingS)
      ) {
        Row(
          modifier = Modifier
            .fillMaxHeight()
            .weight(1f),
          horizontalArrangement = Arrangement.Start,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Canvas(
            modifier = Modifier
              .size(10.dp)
          ) {
            drawCircle(
              color = canvasColor.copy(
                alpha = 0.8f
              ),
              radius = 5.dp.toPx(),
              center = center
            )
          }

          Spacer(modifier = Modifier.width(5.dp))

          Text(
            text = pair.first,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp)
          )
        }

        Text(
          text = pair.second,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
          modifier = Modifier
        )
      }
    }
  }
}
