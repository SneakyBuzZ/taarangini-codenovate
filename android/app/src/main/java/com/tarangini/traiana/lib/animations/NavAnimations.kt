package com.tarangini.traiana.lib.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

private val orderedCarouselRoutes = listOf("home", "trip", "events", "alerts", "emergency")

private fun isCarouselRoute(route: String?): Boolean =
  route in orderedCarouselRoutes

private fun transitionDirection(
  from: NavBackStackEntry?,
  to: NavBackStackEntry?
): Int {
  val fromIndex = orderedCarouselRoutes.indexOf(from?.destination?.route).coerceAtLeast(0)
  val toIndex = orderedCarouselRoutes.indexOf(to?.destination?.route).coerceAtLeast(0)
  return when {
    toIndex > fromIndex -> 1
    toIndex < fromIndex -> -1
    else -> 1
  }
}

fun enterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
  val from = initialState.destination.route
  val to = targetState.destination.route

  if (isCarouselRoute(from) && isCarouselRoute(to)) {
    val dir = transitionDirection(initialState, targetState)
    if (dir >= 0)
      slideInHorizontally(
        animationSpec = tween(400),
        initialOffsetX = { fullWidth -> fullWidth }
      ) + fadeIn(animationSpec = tween(250))
    else
      slideInHorizontally(
        animationSpec = tween(400),
        initialOffsetX = { fullWidth -> -fullWidth }
      ) + fadeIn(animationSpec = tween(250))
  } else {
    fadeIn(animationSpec = tween(250))
  }
}

fun exitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
  val from = initialState.destination.route
  val to = targetState.destination.route

  if (isCarouselRoute(from) && isCarouselRoute(to)) {
    val dir = transitionDirection(initialState, targetState)
    if (dir >= 0)
      slideOutHorizontally(
        animationSpec = tween(300),
        targetOffsetX = { fullWidth -> -fullWidth / 2 }
      ) + fadeOut(animationSpec = tween(200))
    else
      slideOutHorizontally(
        animationSpec = tween(300),
        targetOffsetX = { fullWidth -> fullWidth / 2 }
      ) + fadeOut(animationSpec = tween(200))
  } else {
    fadeOut(animationSpec = tween(250))
  }
}
