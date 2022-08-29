package com.jgpleo.chitchatt.ui.screen.logon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgpleo.chitchatt.R
import com.jgpleo.chitchatt.ui.component.button.PrimaryButton
import com.jgpleo.chitchatt.ui.theme.linkStyle
import com.jgpleo.chitchatt.ui.theme.subtitleStyle
import com.jgpleo.chitchatt.ui.theme.titleStyle

@Composable
fun SignupFragment(
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var pass by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPass by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.signup_title),
            style = titleStyle()
        )

        Text(
            text = stringResource(id = R.string.signup_subtitle),
            style = subtitleStyle()
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.signup_email_address)) },
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.signup_pass)) },
            value = pass,
            onValueChange = { pass = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.signup_confirm_pass)) },
            value = confirmPass,
            onValueChange = { confirmPass = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions { createAccountAction() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.signup_signup_button)
        ) {
            createAccountAction()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.signup_back_login_link),
            style = linkStyle(),
            modifier = Modifier.clickable {
                onCurrentFragmentChange(LogonSelectedFragment.SignIn)
            }
        )

    }
}

private fun createAccountAction() {
    // TODO: create account here
    // Request create account and auto login on response
}

@Preview(showBackground = true)
@Composable
private fun SignupFragmentPreview() {
    SignupFragment {}
}