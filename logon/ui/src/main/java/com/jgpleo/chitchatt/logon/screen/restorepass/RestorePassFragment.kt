@file:OptIn(ExperimentalComposeUiApi::class)

package com.jgpleo.chitchatt.logon.screen.restorepass

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgpleo.chitchatt.logon.screen.LogonSelectedFragment
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.button.PrimaryButton
import com.jgpleo.ui_common.component.dialog.Dialog
import com.jgpleo.ui_common.component.textfield.CustomTextField
import com.jgpleo.ui_common.theme.linkStyle
import com.jgpleo.ui_common.theme.titleStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RestorePassFragment(
    viewModel: RestorePassViewModel = viewModel(),
    onLoadingStateChange: (Boolean) -> Unit,
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    val state = viewModel.state.collectAsState().value
    onLoadingStateChange(state is RestorePassViewModel.State.Loading)

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

        CustomTextField(
            label = R.string.restore_pass_email_address,
            value = viewModel.email.collectAsState().value,
            leadingIcon = R.drawable.ic_mail,
            leadingIconContentDescription = R.string.login_lock_icon_content_description,
            error = viewModel.emailError.collectAsState().value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            onValueChange = { viewModel.email.value = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.restore_pass_send_button)
        ) {
            restorePasswordAction(viewModel, keyboardController, onCurrentFragmentChange)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.restore_pass_back_login_link),
            style = linkStyle(),
            modifier = Modifier.clickable {
                onCurrentFragmentChange(LogonSelectedFragment.SignIn)
                viewModel.dispose()
            }
        )

        if (state is RestorePassViewModel.State.Error) {
            Dialog(
                model = state.error,
                dismissAction = { viewModel.dismissError() }
            )
        }
    }
}

private fun restorePasswordAction(
    viewModel: RestorePassViewModel,
    keyboardController: SoftwareKeyboardController?,
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {
    viewModel.restorePass()
    keyboardController?.hide()
    onCurrentFragmentChange(LogonSelectedFragment.SignIn)
}

@Preview(showBackground = true)
@Composable
private fun RestorePassFragmentPreview() {
    RestorePassFragment(onLoadingStateChange = {}) {}
}