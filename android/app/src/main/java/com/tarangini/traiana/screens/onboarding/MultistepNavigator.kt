package com.tarangini.traiana.screens.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.tarangini.traiana.components.theme.Dimens

@Composable
fun MultistepNavigator () {
  var stepIndex by remember { mutableIntStateOf(0) }
  val steps = listOf("Personal Details", "Kyc Details", "Itinerary Details")
  val scrollState = rememberScrollState()

  LaunchedEffect(stepIndex) {
    scrollState.animateScrollTo(0)
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = Dimens.PaddingM)
      .verticalScroll(scrollState),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    StepProgressBar(
      steps=steps,
      currentStep = stepIndex,
      completedSteps = listOf(stepIndex > 0, stepIndex > 1, stepIndex > 2)
    )

    Text(
      modifier = Modifier
        .padding(vertical = 40.dp),
      text = steps[stepIndex],
      style = MaterialTheme.typography.displaySmall
    )

    Crossfade(
      targetState = stepIndex,
      animationSpec = tween(500)
    ) { index ->
      when(index){
        0 ->
          PersonalDetails(
            incrementStepIndex = { stepIndex++ }
          )
        1->
          KycDetails(
            incrementStepIndex = { stepIndex++ }
          )
        2->
          ItineraryDetails()
      }
    }

    Spacer(modifier = Modifier.height(90.dp))
  }
}