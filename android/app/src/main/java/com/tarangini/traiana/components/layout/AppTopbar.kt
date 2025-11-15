package com.tarangini.traiana.components.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.shared.UserAvatar
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppImage
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.tarangini.traiana.components.ui.AppRow
import com.tarangini.traiana.R
import com.tarangini.traiana.components.ui.RowHorizontalPlacement

@Composable
fun AppTopbar(
  navController : NavController,
  modifier: Modifier = Modifier
) {
  val barRowHeight = Dimens.HeightXS - 10.dp
  val navList = listOf(
    Pair("HOME",R.drawable.ic_home),
    Pair("TRIP",R.drawable.ic_trip),
    Pair("EVENTS",R.drawable.ic_events),
    Pair("ALERTS",R.drawable.ic_alert),
    Pair("EMERGENCY",R.drawable.ic_emergency),
  )
  var activeIndex by remember { mutableIntStateOf(0) }

  fun handleRouteChange(route: String, index: Int) {
    activeIndex = index
    navController.navigate(route.lowercase()) {
      popUpTo(navController.graph.startDestinationId) { saveState = true }
      launchSingleTop = true
      restoreState = true
    }
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(Dimens.HeightXL)
      .drawBehind {
        val strokeWidth = 1.dp.toPx()
        val y = size.height - strokeWidth / 2
        drawLine(
          color = Colors.Neutral700,
          start = Offset(0f, y),
          end = Offset(size.width, y),
          strokeWidth = strokeWidth
        )
      }
  ) {
    AppImage(
      url = "https://res.cloudinary.com/dvwnsmtdy/image/upload/v1757780482/top-grad1_iseyyz.png",
      modifier = Modifier
        .fillMaxWidth()
        .height(Dimens.HeightXL)
        .align(Alignment.TopCenter)
        .alpha(0.8f)
        .zIndex(0f)
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
        .zIndex(1f),
    ) {

      AppRow (
        modifier = Modifier
          .fillMaxWidth()
          .height(barRowHeight)
          .padding(horizontal = Dimens.PaddingS),
        horizontal = RowHorizontalPlacement.SpaceBetween
      ) {
        AppRow (
          modifier = Modifier
            .fillMaxHeight(),
        ) {
          Image(
            painter = painterResource(R.drawable.ic_location),
            contentDescription = "Location",
            modifier = Modifier
              .size(Dimens.IconSize + 2.dp)
              .alpha(0.7f)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Column(
            modifier = Modifier
              .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              "KMIT Hyderabad",
              style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 16.sp
              )
            )
            Text(
              "Hari Vihar Colony, Bhawani Nagar",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp
              )
            )
          }
          Spacer(modifier = Modifier.width(2.dp))
          Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Chevron Down",
            modifier = Modifier
              .size(Dimens.IconSize - 2.dp)
              .alpha(0.7f)
          )
        }
        UserAvatar(
          imageUrl = "https://i.pinimg.com/736x/0f/68/94/0f6894e539589a50809e45833c8bb6c4.jpg",
          name = "Kaushik",
          size = Dimens.HeightXXS,
          showBorder = true,
          navController = navController
        )
      }

      Column(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "Welcome Trotter!",
          style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "You are in safe zone. Keep exploring.",
          style = MaterialTheme.typography.bodyMedium,
          color = Colors.Neutral300
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(Dimens.HeightXS)
          .padding(horizontal = Dimens.PaddingXS),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
      ) {
        navList.forEachIndexed { index, (title, iconRes) ->
          val isActive = index == activeIndex
          Column(
            modifier = Modifier
              .weight(1f)
              .fillMaxHeight()
              .clickable {  handleRouteChange(title, index) }
              .padding(top = 8.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Image(
              painter = painterResource(iconRes),
              contentDescription = "Home",
              modifier = Modifier
                .size(Dimens.IconSize)
                .alpha(if(isActive) 1f else 0.5f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = title,
              style = MaterialTheme.typography.bodySmall.copy(
                color = if (isActive) Color.White else Colors.Neutral400,
                fontSize = 12.sp
              )
            )
            Spacer(modifier = Modifier.height(6.dp))
            if (isActive) {
              Box(
                modifier = Modifier
                  .height(1.dp)
                  .width(50.dp)
                  .background(
                    color = Color.White,
                    shape = RoundedCornerShape(2.dp)
                  )
              )
            } else {
              Spacer(modifier = Modifier.height(3.dp))
            }
          }
        }
      }
    }
  }
}
