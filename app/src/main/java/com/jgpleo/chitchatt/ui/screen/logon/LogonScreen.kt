package com.jgpleo.chitchatt.ui.screen.logon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jgpleo.chitchatt.R
import com.jgpleo.chitchatt.ui.theme.logoStyle

@Composable
fun LogonScreen() {

    var currentFragment by remember { mutableStateOf(LogonSelectedFragment.SignIn) }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        LogoAnimation()

        when (currentFragment) {
            LogonSelectedFragment.SignIn -> SignInFragment { currentFragment = it }
            LogonSelectedFragment.SignUp -> SignupFragment { currentFragment = it }
            LogonSelectedFragment.RestorePass -> RestorePassFragment { currentFragment = it }
        }
    }

}

@Composable
private fun LogoAnimation() {

    val logoComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.chitchatt_splash))
    val animationProgress by animateLottieCompositionAsState(composition = logoComposition)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 64.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(200.dp)
                .padding(top = 24.dp),
            composition = logoComposition,
            progress = animationProgress
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = logoStyle()
        )
    }

}