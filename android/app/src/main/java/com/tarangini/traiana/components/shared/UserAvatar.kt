package com.tarangini.traiana.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tarangini.traiana.components.theme.Colors

@Composable
fun UserAvatar(
  imageUrl: String? = null,
  name: String = "",
  size: Dp = 40.dp,
  showBorder: Boolean = false,
  borderColor: Color = Colors.Neutral700,
  borderWidth: Dp = 2.dp,
  navController: NavController,
  modifier: Modifier = Modifier
) {
  val initials = remember(name) {
    name.split(" ")
      .take(2)
      .map { it.firstOrNull()?.uppercaseChar() ?: "" }
      .joinToString("")
      .take(2)
  }

  val fallbackBgColor = remember(name) {
    val colors = listOf(
      Color(0xFF3B82F6), // blue
      Color(0xFF10B981), // emerald
      Color(0xFFF59E0B), // amber
      Color(0xFFEF4444), // red
      Color(0xFF8B5CF6), // violet
      Color(0xFFF97316), // orange
    )
    colors[name.hashCode().mod(colors.size)]
  }

  Box(
    modifier = modifier
      .size(size)
      .border(1.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
      .then(
        if (showBorder) {
          Modifier
            .border(borderWidth, borderColor, CircleShape)
            .clickable(true, onClick = {navController.navigate("profile")})
        } else {
          Modifier
        }
      )
      .clip(CircleShape),
    contentAlignment = Alignment.Center
  ) {
    if (!imageUrl.isNullOrEmpty()) {
      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(imageUrl)
          .crossfade(true)
          .build(),
        contentDescription = "Profile picture of $name",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxSize()
          .clip(CircleShape),
        onError = {
        }
      )
    } else {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(fallbackBgColor, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = initials,
          color = Color.White,
          fontSize = (size.value * 0.4).sp,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}