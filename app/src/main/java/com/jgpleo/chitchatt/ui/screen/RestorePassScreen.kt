package com.jgpleo.chitchatt.ui.screen

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
import com.jgpleo.chitchatt.R
import com.jgpleo.chitchatt.ui.component.button.PrimaryButton
import com.jgpleo.chitchatt.ui.theme.linkStyle
import com.jgpleo.chitchatt.ui.theme.titleStyle

@Composable
fun RestorePassScreen() {

    var email by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.restore_pass_title),
            style = titleStyle()
        )

        Text(text = stringResource(id = R.string.restore_pass_subtitle))

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.restore_pass_email_address)) },
            value = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.restore_pass_send_button)
        ) {
            // TODO: request restore password and navigate to login screen
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.restore_pass_back_login_link),
            style = linkStyle()
        )
    }
}

@Preview
@Composable
fun RestorePassScreenPreview() {
    RestorePassScreen()
}