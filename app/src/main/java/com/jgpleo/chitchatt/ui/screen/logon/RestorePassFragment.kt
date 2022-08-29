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
import com.jgpleo.chitchatt.ui.theme.titleStyle

@Composable
fun RestorePassFragment(
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    var email by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
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
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions { restorePasswordAction(onCurrentFragmentChange) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.restore_pass_send_button)
        ) {
            restorePasswordAction(onCurrentFragmentChange)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.restore_pass_back_login_link),
            style = linkStyle(),
            modifier = Modifier.clickable {
                onCurrentFragmentChange(LogonSelectedFragment.SignIn)
            }
        )
    }
}

private fun restorePasswordAction(onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit) {
    // TODO: restore password action here
    // request restore password and navigate to login screen
    onCurrentFragmentChange(LogonSelectedFragment.SignIn)
}

@Preview(showBackground = true)
@Composable
private fun RestorePassFragmentPreview() {
    RestorePassFragment {}
}