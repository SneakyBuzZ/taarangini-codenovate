package com.tarangini.traiana.components.shared

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarangini.traiana.components.theme.Colors

@Composable
fun DashedBorderBox(
  modifier: Modifier = Modifier,
  borderColor: Color = Colors.Neutral700,
  cornerRadius: Dp = 16.dp,
  strokeWidth: Dp = 1.dp,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    modifier = modifier
      .drawWithCache{
        val stroke = Stroke(
          width = strokeWidth.toPx() + 2,
          pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        val cornerRadius = cornerRadius.toPx()
        onDrawBehind {
          drawRoundRect(
            color = borderColor,
            style = stroke,
            cornerRadius = CornerRadius(cornerRadius - 10, cornerRadius - 10)
          )
        }
      }
      .background(Colors.Neutral800.copy(
        alpha = 0.5f
      ), shape = MaterialTheme.shapes.medium)
      .padding(20.dp),
    contentAlignment = Alignment.Center,
    content = content
  )
}

@Composable
fun IdUploader(
  modifier: Modifier = Modifier,
) {
  var uploadState by remember { mutableStateOf("empty") }
  var progress by remember { mutableStateOf(0) }
  var uploadedFileName by remember { mutableStateOf("") }
  var uploadedUri by remember { mutableStateOf<Uri?>(null) }

  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Upload ID",
      style = MaterialTheme.typography.bodySmall.copy(
        color = Colors.Neutral300
      ),
      modifier = Modifier
        .fillMaxWidth()
    )

    Spacer(Modifier.height(10.dp))

    DashedBorderBox(
      modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
    ) {
      when (uploadState) {
        "empty" -> {
          Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              painter = painterResource(id = android.R.drawable.ic_menu_upload),
              contentDescription = null,
              modifier = Modifier.size(38.dp),
              tint = Colors.Neutral500
            )
            Spacer(Modifier.height(10.dp))
            ClickableText(
              text = androidx.compose.ui.text.AnnotatedString("Tap to upload photo"),
              style = MaterialTheme.typography.bodyMedium,
              onClick = { /* TODO: launch gallery picker; set uploadState="progress" on start */ }
            )
            Spacer(Modifier.height(4.dp))
            Text(
              "PNG, JPG or PDF",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 13.sp
              )
            )
            Spacer(Modifier.height(10.dp))
            Button (
              onClick = { /* TODO: launch camera picker; set uploadState="progress" on start */ },
              modifier = Modifier,
              colors = ButtonDefaults.buttonColors(
                containerColor = Colors.Neutral200,
                contentColor = Colors.Neutral900
              )
            ){
              Text(
                "Upload",
                color = Colors.Neutral900
              )
            }
          }
        }
        "progress" -> {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              painter = painterResource(id = /* your file type icon resource */ android.R.drawable.ic_menu_gallery), // Replace
              contentDescription = null,
              modifier = Modifier.size(48.dp),
              tint = Colors.Neutral500
            )
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
              progress = progress / 100f,
              modifier = Modifier.fillMaxWidth(0.7f)
            )
            Spacer(Modifier.height(8.dp))
            Text("Uploading Document...", style = MaterialTheme.typography.bodySmall)
            Text(uploadedFileName, style = MaterialTheme.typography.bodySmall.copy(color = Colors.Neutral500))
          }
        }
        "complete" -> {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              painter = painterResource(id = android.R.drawable.checkbox_on_background),
              contentDescription = null,
              modifier = Modifier.size(42.dp),
              tint = Colors.CoralGreen100
            )
            Spacer(Modifier.height(10.dp))
            Text("Upload Complete", style = MaterialTheme.typography.bodyMedium.copy(color = Colors.CoralGreen100))
            Text(uploadedFileName, style = MaterialTheme.typography.bodySmall.copy(color = Colors.Neutral500))
            Spacer(Modifier.height(10.dp))
            ClickableText(
              text = androidx.compose.ui.text.AnnotatedString("Clear Upload"),
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error),
              onClick = {
                uploadState = "empty"
                progress = 0
                uploadedFileName = ""
                uploadedUri = null
              }
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(15.dp))
  }
}
