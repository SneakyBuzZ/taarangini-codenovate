package com.tarangini.traiana.components.shared

import android.content.Intent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.airbnb.mvrx.compose.collectAsState
import com.tarangini.traiana.lib.api.wss.SosContent
import com.tarangini.traiana.lib.api.wss.SosViewModel
import androidx.core.net.toUri

@Composable
fun AlertDialogListener(viewModel: SosViewModel) {
  val context = LocalContext.current

  // Observe latest SOS state from ViewModel
  val sosState by viewModel.collectAsState()

  // Local state to show/hide the dialog
  var showDialog by remember { mutableStateOf(false) }
  var currentSos by remember { mutableStateOf<SosContent?>(null) }

  // Update dialog when a new SOS arrives
  LaunchedEffect(sosState.latestSos) {
    sosState.latestSos?.let { sos ->
      currentSos = sos
      showDialog = true
    }
  }

  if (showDialog && currentSos != null) {
    AlertDialog(
      onDismissRequest = { showDialog = false },
      title = { Text(text = "SOS Alert!") },
      text = {
        Text(
          "Tourist: ${currentSos!!.name}\n" +
              "Age: ${currentSos!!.age}\n" +
              "Gender: ${currentSos!!.gender}\n" +
              "Location: (${currentSos!!.location.latitude}, ${currentSos!!.location.longitude})\n" +
              "Emergency Contact: ${currentSos!!.emergencyContact.name} - ${currentSos!!.emergencyContact.phone}\n" +
              "Safety Score: ${currentSos!!.safetyScore}"
        )
      },
      confirmButton = {
        TextButton(onClick = { showDialog = false }) {
          Text("Close")
        }
      },
      dismissButton = {
        TextButton(onClick = {
          // Open phone dialer with emergency contact
          val intent = Intent(Intent.ACTION_DIAL)
          intent.data = "tel:${currentSos!!.emergencyContact.phone}".toUri()
          context.startActivity(intent)
        }) {
          Text("Call")
        }
      }
    )
  }
}

