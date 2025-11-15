package com.tarangini.traiana.screens.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.tarangini.traiana.components.layout.LocalNavController
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppColumn
import com.tarangini.traiana.components.ui.AppRow
import com.tarangini.traiana.components.ui.ColumnLayout
import com.tarangini.traiana.lib.api.user.User
import com.tarangini.traiana.lib.api.user.UserViewModel

@Composable
fun ProfileSheet() {

  val userViewModel : UserViewModel = mavericksActivityViewModel()
  val userState by userViewModel.collectAsState {it.getUserRequest}

  when (userState) {
    is Uninitialized, is Loading -> {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        CircularProgressIndicator()
      }
    }

    is Fail -> {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "Failed to load profile",
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.error
        )
      }
    }

    is Success -> {
      val user: User? = userState.invoke()
      if (user == null) {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          Text("No user found")
        }
      } else {
        ProfileContent(user = user, bitmap = extractQrCodeFromBase64(user.qrcode.split(",")[1])!!)
      }
    }

  }

}

@Composable
fun ProfileContent(user : User, bitmap: Bitmap){
  val navController = LocalNavController.current
  AppColumn(
    layout = ColumnLayout.Top,
    modifier = Modifier
      .fillMaxSize()
      .padding(Dimens.PaddingS)
  ) {
    AppRow(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      Icon(
        imageVector = Icons.Default.KeyboardArrowLeft,
        contentDescription = "Back",
        modifier = Modifier.size(Dimens.IconSize)
      )
      Spacer(modifier = Modifier.width(Dimens.SpaceXS))
      Text(
        "Back",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
          .clickable(true, onClick = {
          navController.navigate("home")
        })
      )
    }

    Spacer(modifier = Modifier.height(Dimens.SpaceL))

    // QR container
    AppColumn(
      layout = ColumnLayout.Top,
      modifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .border(1.dp, Colors.Neutral700, MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.surface)
        .padding(Dimens.PaddingS)
    ) {
      AppColumn(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = Dimens.PaddingS)
      ) {
        Text(
          user.fullname,
          style = MaterialTheme.typography.displaySmall
        )
        Text(
          user.touristId.substring(0,5) + "...." + user.touristId.substring(user.touristId.length - 5),
          style = MaterialTheme.typography.bodyMedium
        )
      }
      Spacer(modifier = Modifier.height(Dimens.SpaceL))
      Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "QR Code",
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1f)
          .clip(MaterialTheme.shapes.medium)
          .border(1.dp, Colors.CoralBlue100, MaterialTheme.shapes.medium)
      )
    }

    Spacer(modifier = Modifier.height(Dimens.SpaceXL))

    AppColumn(
      layout = ColumnLayout.Top,
      horizontalAlignment = Alignment.Start,
      modifier = Modifier
        .fillMaxWidth()
    ) {
      Text(
        "Personal Details",
        style = MaterialTheme.typography.bodyLarge.copy(
          fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier
          .align(Alignment.Start)
      )
      Spacer(modifier = Modifier.height(Dimens.SpaceL))
      AppColumn (
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
          .clip(MaterialTheme.shapes.medium)
          .fillMaxWidth()
          .border(1.dp, Colors.Neutral700)
          .background(MaterialTheme.colorScheme.surface)
          .padding(Dimens.PaddingS)
      ) {
        Text(
          "Email",
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier
            .align(Alignment.Start)
        )
        Text(
          user.email,
          style = MaterialTheme.typography.headlineSmall,
          modifier = Modifier
            .align(Alignment.Start)
        )
      }
      Spacer(modifier = Modifier.height(Dimens.SpaceL))
      AppRow {
        AppColumn (
          horizontalAlignment = Alignment.Start,
          modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .border(1.dp, Colors.Neutral700)
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimens.PaddingS)
            .weight(1f)
        ) {
          Text(
            "DOB",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
              .align(Alignment.Start)
          )
          Text(
            user.dob.substring(0,10),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
              .align(Alignment.Start)
          )
        }
        Spacer(modifier = Modifier.width(Dimens.SpaceS))
        AppColumn (
          horizontalAlignment = Alignment.Start,
          modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .border(1.dp, Colors.Neutral700)
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimens.PaddingS)
            .weight(1f)
        ) {
          Text(
            "Gender",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
              .align(Alignment.Start)
          )
          Text(
            user.gender,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
              .align(Alignment.Start)
          )
        }
      }
    }
  }
}

fun extractQrCodeFromBase64(base64String: String): Bitmap? {

  val imageBytes: ByteArray? = try {
    Base64.decode(base64String, Base64.DEFAULT)
  } catch (e: Exception) {
    null
  }

  // Convert ByteArray → Bitmap
  val bitmap = imageBytes?.let {
    BitmapFactory.decodeByteArray(it, 0, it.size)
  }

  return bitmap
}

