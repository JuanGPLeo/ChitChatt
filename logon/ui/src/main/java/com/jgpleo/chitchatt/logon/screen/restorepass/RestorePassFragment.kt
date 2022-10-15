@file:OptIn(ExperimentalComposeUiApi::class)

package com.jgpleo.chitchatt.logon.screen.restorepass

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgpleo.chitchatt.logon.screen.LogonSelectedFragment
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.button.PrimaryButton
import com.jgpleo.ui_common.theme.linkStyle
import com.jgpleo.ui_common.theme.titleStyle

@Composable
fun RestorePassFragment(
    viewModel: RestorePassViewModel = viewModel(),
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    var email by remember { mutableStateOf(TextFieldValue("")) }

    val keyboardController = LocalSoftwareKeyboardController.current

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
            keyboardActions = KeyboardActions {
                restorePasswordAction(viewModel, email.toString(), keyboardController, onCurrentFragmentChange)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.restore_pass_send_button)
        ) {
            restorePasswordAction(viewModel, email.toString(), keyboardController, onCurrentFragmentChange)
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

private fun restorePasswordAction(
    viewModel: RestorePassViewModel,
    email: String,
    keyboardController: SoftwareKeyboardController?,
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {
    viewModel.restorePass(email)
    keyboardController?.hide()
//    onCurrentFragmentChange(LogonSelectedFragment.SignIn)
}

@Preview(showBackground = true)
@Composable
private fun RestorePassFragmentPreview() {
    RestorePassFragment {}
}