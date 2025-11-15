package com.tarangini.traiana.components.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest


@Composable
fun AppImage(url : String , modifier : Modifier = Modifier){
  AsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
      .data(url)
      .decoderFactory(SvgDecoder.Factory())
      .build(),
    contentDescription = null,
    modifier = modifier,
    contentScale = ContentScale.Crop
  )
}