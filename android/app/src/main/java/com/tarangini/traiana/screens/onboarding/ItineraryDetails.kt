package com.tarangini.traiana.screens.onboarding

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.tarangini.traiana.components.layout.LocalNavController
import com.tarangini.traiana.components.theme.Colors
import com.tarangini.traiana.components.theme.Dimens
import com.tarangini.traiana.components.ui.AppButton
import com.tarangini.traiana.components.ui.AppInput
import com.tarangini.traiana.components.ui.AppRow
import com.tarangini.traiana.components.ui.ButtonVariant
import com.tarangini.traiana.components.ui.RowHorizontalPlacement
import com.tarangini.traiana.lib.api.itinerary.ItineraryViewModel
import com.tarangini.traiana.lib.dto.LocationDTO
import com.tarangini.traiana.lib.dto.RegisterItineraryFormDTO

@Composable
fun ItineraryDetails(
  modifier: Modifier = Modifier,
) {
  val itineraryViewModel : ItineraryViewModel = mavericksViewModel()
  val state by itineraryViewModel.collectAsState()
  val navController = LocalNavController.current
  val context = LocalContext.current

  val formState = remember {
    FormState(
      fields = listOf(
        TextFieldState(
          name = "arrivalDate",
          validators = listOf(Validators.Required()),
          initial = "2025-11-25"
        ),
        TextFieldState(
          name = "departureDate",
          validators = listOf(Validators.Required()),
          initial = "2025-12-31"
        ),
        TextFieldState(
          name = "transportMode",
          validators = listOf(Validators.Required()),
          initial = "bus, flight"
        )
      )
    )
  }

  val arrivalDateState = formState.getState<TextFieldState>("arrivalDate")
  val departureDateState = formState.getState<TextFieldState>("departureDate")
  val transportModeState = formState.getState<TextFieldState>("transportMode")
  val locations = remember {
    mutableStateListOf(
      LocationDTO(
      city = "Hyderabad",
      stay = "Hyatt Palace, Banjara Hills",
      fromDate = "2025-06-25",
      toDate = "2025-06-27"
    ))
  }

  fun handleFormSubmit(){
    val isValid = formState.validate()
    if(isValid){
      val data = formState.getData(RegisterItineraryFormDTO::class)
      itineraryViewModel.register(data,locations)
    }else{
      Log.d("FORM ERROR", "Some required fields are missing")
    }
  }

  LaunchedEffect(state.registerRequest) {
    when(state.registerRequest){
      is Success -> {
        navController.navigate("home")
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
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {

    Text(
      "Itinerary Dates",
      style = MaterialTheme.typography.bodyMedium.copy(
        color = Colors.Neutral200,
      ),
      textAlign = TextAlign.Start,
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(15.dp))

    AppRow (
      modifier = Modifier
        .fillMaxWidth()
    ){
      AppInput(
        label = "Arrival Date",
        value = arrivalDateState.value,
        isError = arrivalDateState.hasError,
        onValueChange = { arrivalDateState.change(it) },
        placeHolder = "YYYY-MM-DD",
        modifier = Modifier.weight(1f)
      )

      Spacer(modifier = Modifier.width(12.dp))

      AppInput(
        label = "Departure Date",
        value = departureDateState.value,
        isError = departureDateState.hasError,
        onValueChange = { departureDateState.change(it) },
        placeHolder = "YYYY-MM-DD",
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(15.dp))

    Text(
      "Location Details",
      style = MaterialTheme.typography.bodyMedium.copy(
        color = Colors.Neutral200,
      ),
      textAlign = TextAlign.Start,
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(15.dp))

    locations.forEachIndexed { index, location ->
      AppInput(
        label = "City",
        value = location.city,
        onValueChange = { locations[index] = location.copy(city = it) },
        placeHolder = "Enter City"
      )
      AppInput(
        label = "Stay",
        value = location.stay,
        onValueChange = { locations[index] = location.copy(stay = it) },
        placeHolder = "Enter Stay"
      )
      AppRow (
        modifier = Modifier
          .fillMaxWidth()
      ) {
        AppInput(
          label = "From Date",
          value = location.fromDate,
          onValueChange = { locations[index] = location.copy(fromDate = it) },
          placeHolder = "YYYY-MM-DD",
          modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        AppInput(
          label = "To Date",
          value = location.toDate,
          onValueChange = { locations[index] = location.copy(toDate = it) },
          placeHolder = "YYYY-MM-DD",
          modifier = Modifier.weight(1f)
        )
      }
    }

    AppRow(
      modifier = Modifier
        .fillMaxWidth(),
      horizontal = RowHorizontalPlacement.End
    ){
      AppButton(
        text = "-",
        variant = ButtonVariant.Outline,
        onClick = { if(locations.size > 1) locations.removeAt(locations.size - 1) },
        modifier = Modifier
          .width(Dimens.HeightXS)
          .height(Dimens.HeightXS),
      )

      Spacer(modifier = Modifier.width(12.dp))

      AppButton(
        text = "+",
        variant = ButtonVariant.Outline,
        onClick = { locations.add(LocationDTO("", "", "", "")) },
        modifier = Modifier
          .width(Dimens.HeightXS)
          .height(Dimens.HeightXS),
      )
    }

    AppInput(
      label = "Transport Modes",
      value = transportModeState.value,
      isError = transportModeState.hasError,
      onValueChange = { transportModeState.change(it) },
      placeHolder = "bus, flight"
    )

    Spacer(modifier = Modifier.height(15.dp))

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = "By submitting this form you agree to our terms and conditions",
      style = MaterialTheme.typography.bodySmall.copy(
        fontSize = 12.sp,
        color = Colors.Neutral400
      )
    )

    Spacer(modifier = Modifier.height(10.dp))

    AppButton(
      text = "Save & Continue",
      variant = ButtonVariant.Primary,
      onClick = { handleFormSubmit() }
    )
  }
}
