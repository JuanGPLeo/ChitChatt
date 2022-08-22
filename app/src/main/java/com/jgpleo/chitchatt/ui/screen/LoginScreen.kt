package com.jgpleo.chitchatt.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jgpleo.chitchatt.R
import com.jgpleo.chitchatt.ui.component.button.PrimaryButton
import com.jgpleo.chitchatt.ui.theme.linkStyle
import com.jgpleo.chitchatt.ui.theme.logoStyle
import com.jgpleo.chitchatt.ui.theme.titleStyle

@Composable
fun LoginScreen() {

    val logoComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.chitchatt_splash))
    val animationProgress by animateLottieCompositionAsState(composition = logoComposition)

    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var pass by remember { mutableStateOf(TextFieldValue("")) }


    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

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

        Text(
            text = stringResource(R.string.login_title),
            style = titleStyle()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.login_phone)) },
            value = phone,
            onValueChange = { phone = it }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.login_pass)) },
            value = pass,
            onValueChange = { pass = it }
        )

        Spacer(modifier = Modifier.padding(12.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login_button)
        ) {
            // TODO: onClick action
        }

        Spacer(modifier = Modifier.padding(12.dp))

        Text(
            text = stringResource(id = R.string.login_forgot_password),
            style = linkStyle(),
            modifier = Modifier.clickable {
                // TODO: navigate to recover password screen
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Row {
            Text(text = stringResource(id = R.string.login_register))
            Text(
                text = stringResource(id = R.string.login_register_link),
                style = linkStyle(),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable {
                        // TODO: navigate to register screen
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Box(modifier = Modifier.padding(10.dp)) {
        LoginScreen()
    }
}