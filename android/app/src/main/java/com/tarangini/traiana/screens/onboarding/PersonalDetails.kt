package com.tarangini.traiana.screens.onboarding

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppButton
import com.tarangini.traiana.components.ui.AppInput
import com.tarangini.traiana.components.ui.AppSelect
import com.tarangini.traiana.components.ui.ButtonVariant
import com.tarangini.traiana.lib.dto.RegisterUserDTO
import com.tarangini.traiana.lib.api.user.UserViewModel

@Composable
fun PersonalDetails (
  modifier: Modifier = Modifier,
  incrementStepIndex: () -> Unit
) {
  val userViewModel : UserViewModel = mavericksActivityViewModel()
  val state by userViewModel.collectAsState()
  val context = LocalContext.current

  val formState = remember {
    FormState(
      fields = listOf(
        TextFieldState(
          name =  "fullname",
          validators = listOf(Validators.Required()),
          initial = "Kaushik Katikala"
        ),
        TextFieldState(
          name = "gender",
          validators = listOf(Validators.Required()),
          initial = "Male"
        ),
        TextFieldState(
          name = "dob",
          validators = listOf(Validators.Required()),
          initial = "2004-06-17"
        ),
        TextFieldState(
          name = "email",
          validators = listOf(
            Validators.Required(),
          ),
          initial = "kaushikx304@gmail.com"
        ),
        TextFieldState(
          name = "mobile",
          validators = listOf(Validators.Required()),
          initial = "9876543210"
        ),
        TextFieldState(
          name = "bloodType",
          validators = listOf(Validators.Required()),
          initial = "A+"
        ),
        TextFieldState(
          name = "nationality",
          validators = listOf(Validators.Required()),
          initial = "Indian"
        )
      )
    )
  }

  val fullnameState: TextFieldState = formState.getState("fullname")
  val genderState: TextFieldState = formState.getState("gender")
  val dobState: TextFieldState = formState.getState("dob")
  val emailState : TextFieldState = formState.getState("email")
  val mobileState: TextFieldState = formState.getState("mobile")
  val bloodTypeState: TextFieldState = formState.getState("bloodType")
  val nationalityState: TextFieldState = formState.getState("nationality")

  fun handleFormSubmit() {
    val isValid = formState.validate()
    if (isValid) {
      val data = formState.getData(RegisterUserDTO::class)
      userViewModel.register(data,context)
      incrementStepIndex()
    } else {
      Log.d("FORM ERROR", "Some required fields are missing")
    }
  }

  Column(
    modifier = modifier
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    AppInput(
      label = "Full Name",
      value = fullnameState.value,
      isError = fullnameState.hasError,
      onValueChange = { fullnameState.change(it) },
      placeHolder = "Enter full name"
    )
    Spacer(modifier = Modifier.height(Dimens.SpaceL))
    AppSelect(
      label = "Gender",
      options = listOf("Male", "Female", "Other"),
      selectedOption = genderState.value,
      onOptionSelected = { genderState.change(it) },
      placeHolder = "Select gender"
    )
    Spacer(modifier = Modifier.height(Dimens.SpaceL))
    AppInput(
      label = "Dob",
      value = dobState.value,
      isError = dobState.hasError,
      onValueChange = { dobState.change(it) },
      placeHolder = "Select dob"
    )
    Spacer(modifier = Modifier.height(Dimens.SpaceL))
    AppInput(
      label = "Email",
      value = emailState.value,
      isError = emailState.hasError,
      onValueChange = { emailState.change(it) },
      placeHolder = "Enter email"
    )
    Spacer(modifier = Modifier.height(Dimens.SpaceL))
    AppInput(
      label = "Mobile",
      value = mobileState.value,
      isError = mobileState.hasError,
      onValueChange = { mobileState.change(it) },
      placeHolder = "Enter mobile no."
    )
    Spacer(modifier = Modifier.height(Dimens.SpaceL))
    AppInput(
      label = "Blood Type",
      value = bloodTypeState.value,
      isError = bloodTypeState.hasError,
      onValueChange = { bloodTypeState.change(it) },
      placeHolder = "Enter blood type"
    )
    Spacer(modifier = Modifier.height(Dimens.SpaceL))
    AppInput(
      label = "Nationality",
      value = nationalityState.value,
      isError = nationalityState.hasError,
      onValueChange = { nationalityState.change(it) },
      placeHolder = "Enter nationality"
    )
    Spacer(modifier = Modifier.height(Dimens.SpaceL))
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
      text = when(state.registerRequest){
        is Loading -> "Saving..."
        else -> "Save Details"
      },
      variant = ButtonVariant.Primary,
      onClick = { handleFormSubmit() },
      enabled = state.registerRequest !is Loading
    )
  }
}