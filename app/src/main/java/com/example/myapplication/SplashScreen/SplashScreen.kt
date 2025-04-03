package com.example.myapplication.SplashScreen

import android.annotation.SuppressLint
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import kotlinx.coroutines.delay

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember {
        Animatable(0.5f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 2f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(0.5f).getInterpolation(it)
                })
        )

        delay(1500L)


        navController.navigate("login") {
            popUpTo("splash") {
                inclusive = true
            }
        }
    }

    BoxWithConstraints {
        Box(
            contentAlignment = Alignment.Center,
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.splashh),
                contentDescription = "Logo",
                modifier = androidx.compose.ui.Modifier.scale(scale.value)
            )
        }
    }
}