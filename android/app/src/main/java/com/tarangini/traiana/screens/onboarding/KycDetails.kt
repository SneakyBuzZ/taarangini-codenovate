package com.tarangini.traiana.screens.onboarding

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.ui.AppButton
import com.tarangini.traiana.components.ui.AppInput
import com.tarangini.traiana.components.ui.AppSelect
import com.tarangini.traiana.components.ui.AppTextarea
import com.tarangini.traiana.components.ui.ButtonVariant
import com.tarangini.traiana.lib.dto.RegisterKycFormDTO
import com.tarangini.traiana.lib.api.kyc.KycViewModel
import com.tarangini.traiana.lib.dto.toApiDTO

@Composable
fun KycDetails (
  modifier: Modifier = Modifier,
  incrementStepIndex: () -> Unit
) {
  val kycViewModel : KycViewModel = mavericksViewModel()
  val state by kycViewModel.collectAsState()
  val context = LocalContext.current

  val formState = remember {
    FormState(
      fields = listOf(
        TextFieldState(
          name = "docType",
          validators = listOf(Validators.Required()),
          initial = "Aadhaar Card"
        ),
        TextFieldState(
          name = "docNumber",
          validators = listOf(Validators.Required()),
          initial = "12264067890"
        ),
        TextFieldState(
          name = "docImage",
          validators = listOf(Validators.Required()),
          initial = "https://example.com/image.jpg"
        ),
        TextFieldState(
          name = "issuedAt",
          validators = listOf(Validators.Required()),
          initial = "Unique Id Authority of India"
        ),
        TextFieldState(
          name = "address",
          validators = listOf(Validators.Required()),
          initial = "123 Main Street, City, Country"
        ),
        TextFieldState(
          name = "emergencyName",
          validators = listOf(Validators.Required()),
          initial = "John Doe"
        ),
        TextFieldState(
          name = "emergencyRelation",
          validators = listOf(Validators.Required()),
          initial = "Parent"
        ),
        TextFieldState(
          name = "emergencyPhone",
          validators = listOf(Validators.Required()),
          initial = "9874545210"
        ),
      )
    )
  }

  val docTypeState: TextFieldState = formState.getState("docType")
  val docNumberState: TextFieldState = formState.getState("docNumber")
  val docImageState: TextFieldState = formState.getState("docImage")
  val issuedAtState: TextFieldState = formState.getState("issuedAt")
  val addressState: TextFieldState = formState.getState("address")
  val emergencyNameState: TextFieldState = formState.getState("emergencyName")
  val emergencyRelationState: TextFieldState = formState.getState("emergencyRelation")
  val emergencyPhoneState: TextFieldState = formState.getState("emergencyPhone")

  fun handleFormSubmit() {
    val isValid = formState.validate()
    if(isValid){
      val data = formState.getData(RegisterKycFormDTO::class)
      val apiData = data.toApiDTO()
      kycViewModel.register(apiData)
    }else{
      Log.d("FORM ERROR", "Some required fields are missing")
    }
  }

  LaunchedEffect(state.registerRequest) {
    when(state.registerRequest){
      is Success -> {
        incrementStepIndex()
      }
      is Fail -> {
        Toast.makeText(
          context,
          "Something went wrong",
          Toast.LENGTH_LONG,
        ).show()
      }
      else -> Unit
    }
  }

  Column(
    modifier = modifier
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    AppSelect(
      label = "Select Document Type",
      options = listOf("Aadhaar Card", "Passport", "Driving License"),
      selectedOption = docTypeState.value,
      onOptionSelected = { docTypeState.change(it) },
      placeHolder = "Select Type"
    )

    AppInput(
      label = "Document Number",
      value = docNumberState.value,
      isError = docNumberState.hasError,
      onValueChange = { docNumberState.change(it) },
      placeHolder = "Enter Document Number"
    )

    AppInput(
      label = "Document Image",
      value = docImageState.value,
      isError = docImageState.hasError,
      onValueChange = { docImageState.change(it) },
      placeHolder = "Upload Document Image"
    )

    AppSelect(
      label = "Issued At",
      options = listOf(
        "Govt. of India",
        "Unique Id Authority of India",
        "Ministry of Enternal Affair",
        "Road Transport Authority",
        "Foreign Passport Authority",
        "Other"
      ),
      selectedOption = issuedAtState.value,
      onOptionSelected = { issuedAtState.change(it) },
      placeHolder = "Select Issuer"
    )

    AppTextarea(
      label = "Address",
      value = addressState.value,
      onValueChange = { addressState.change(it) },
      placeHolder = "Enter Address"
    )

    AppInput(
      label = "Emergency Contact Name",
      value = emergencyNameState.value,
      isError = emergencyNameState.hasError,
      onValueChange = { emergencyNameState.change(it) },
      placeHolder = "Enter Emergency Contact Name"
    )

    AppSelect(
      label = "Emergency Contact Relation",
      options = listOf("Parent", "Spouse", "Sibling", "Friend", "Guardian", "Other"),
      selectedOption = emergencyRelationState.value,
      onOptionSelected = { emergencyRelationState.change(it) },
      placeHolder = "Select Relation"
    )

    AppInput(
      label = "Emergency Contact Phone",
      value = emergencyPhoneState.value,
      isError = emergencyPhoneState.hasError,
      onValueChange = { emergencyPhoneState.change(it) },
      placeHolder = "Enter Emergency Contact Phone"
    )

    Text(
      modifier = Modifier
        .fillMaxWidth(),
      text = "By submitting this form you agree to our terms and conditions",
      style = MaterialTheme.typography.bodySmall.copy(
        fontSize = 12.sp,
        color = Colors.Neutral400
      )
    )

    Spacer(modifier = Modifier.height(10.dp))

    AppButton(
      text =  when(state.registerRequest){
        is Loading -> "Saving..."
        else -> "Save Details"
      },
      variant = ButtonVariant.Primary,
      onClick = { handleFormSubmit() }
    )
  }
}
