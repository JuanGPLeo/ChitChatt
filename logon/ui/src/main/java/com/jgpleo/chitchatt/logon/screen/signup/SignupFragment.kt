@file:OptIn(ExperimentalComposeUiApi::class)

package com.jgpleo.chitchatt.logon.screen.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgpleo.chitchatt.logon.screen.LogonSelectedFragment
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.button.PrimaryButton
import com.jgpleo.ui_common.component.dialog.Dialog
import com.jgpleo.ui_common.component.textfield.CustomTextField
import com.jgpleo.ui_common.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupFragment(
    viewModel: SignupViewModel = viewModel(),
    onLoadingStateChange: (Boolean) -> Unit,
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    val state = viewModel.state.collectAsState().value
    onLoadingStateChange(state is SignupViewModel.State.Loading)

    var passFocused by remember { mutableStateOf(false) }
    var confirmPassFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

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

        CustomTextField(
            label = R.string.signup_email_address,
            value = viewModel.email.collectAsState().value,
            leadingIcon = R.drawable.ic_mail,
            leadingIconContentDescription = R.string.login_lock_icon_content_description,
            error = viewModel.emailError.collectAsState().value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            onValueChange = { viewModel.email.value = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { passFocused = it.isFocused }
            ,
            label = { Text(text = stringResource(id = R.string.signup_pass)) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = stringResource(
                        id = R.string.login_lock_icon_content_description
                    ),
                    tint = if (viewModel.passError.collectAsState().value.hasError) {
                        ErrorColor
                    } else if (passFocused) {
                        PrimaryColor
                    } else {
                        LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    }
                )
            },
            isError = viewModel.passError.collectAsState().value.hasError,
            value = viewModel.pass.collectAsState().value,
            onValueChange = { viewModel.pass.value = it },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            )
        )

        if (viewModel.passError.collectAsState().value.hasError) {
            viewModel.passError.collectAsState().value.errorMessage?.let {
                Text(
                    text = stringResource(id = it),
                    color = ErrorColor,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { confirmPassFocused = it.isFocused }
            ,
            label = { Text(text = stringResource(id = R.string.signup_confirm_pass)) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = stringResource(
                        id = R.string.login_lock_icon_content_description
                    ),
                    tint = if (viewModel.confirmPassError.collectAsState().value.hasError) {
                        ErrorColor
                    } else if (confirmPassFocused) {
                        PrimaryColor
                    } else {
                        LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    }
                )
            },
            isError = viewModel.confirmPassError.collectAsState().value.hasError,
            value = viewModel.confirmPass.collectAsState().value,
            onValueChange = { viewModel.confirmPass.value = it },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions { createAccountAction(viewModel, keyboardController) }
        )

        if (viewModel.confirmPassError.collectAsState().value.hasError) {
            viewModel.confirmPassError.collectAsState().value.errorMessage?.let {
                Text(
                    text = stringResource(id = it),
                    color = ErrorColor,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.signup_signup_button)
        ) { createAccountAction(viewModel, keyboardController) }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.signup_back_login_link),
            style = linkStyle(),
            modifier = Modifier.clickable {
                onCurrentFragmentChange(LogonSelectedFragment.SignIn)
                viewModel.dispose()
            }
        )

        if (state is SignupViewModel.State.Error) {
            Dialog(
                model = state.error,
                dismissAction = { viewModel.dismissError() }
            )
        }

    }
}

private fun createAccountAction(
    viewModel: SignupViewModel,
    keyboardController: SoftwareKeyboardController?
) {
    keyboardController?.hide()
    viewModel.register()
}

@Preview(showBackground = true)
@Composable
private fun SignupFragmentPreview() {
    SignupFragment(onLoadingStateChange = {}) {}
}