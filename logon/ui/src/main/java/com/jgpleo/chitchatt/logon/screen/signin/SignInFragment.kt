@file:OptIn(ExperimentalComposeUiApi::class)

package com.jgpleo.chitchatt.logon.screen.signin

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.jgpleo.chitchatt.logon.screen.LogonSelectedFragment
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.button.PrimaryButton
import com.jgpleo.ui_common.component.dialog.Dialog
import com.jgpleo.ui_common.component.textfield.CustomTextField
import com.jgpleo.ui_common.theme.ErrorColor
import com.jgpleo.ui_common.theme.PrimaryColor
import com.jgpleo.ui_common.theme.linkStyle
import com.jgpleo.ui_common.theme.titleStyle

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInFragment(
    viewModel: SignInViewModel = viewModel(),
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    val state = viewModel.state.collectAsState().value

    var passFocused by remember { mutableStateOf(false) }
    var showingPass by remember { mutableStateOf(false) }
    val passFocusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = stringResource(R.string.login_title),
            style = titleStyle()
        )

        Spacer(modifier = Modifier.padding(16.dp))

        CustomTextField(
            label = R.string.login_email,
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

        Spacer(modifier = Modifier.padding(4.dp))

        OutlinedTextField(
            value = viewModel.pass.collectAsState().value,
            onValueChange = { viewModel.pass.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passFocusRequester)
                .onFocusChanged {
                    passFocused = it.isFocused
                    if (!it.isFocused) {
                        showingPass = false
                    }
                },
            label = { Text(text = stringResource(id = R.string.login_pass)) },
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
            trailingIcon = {
                if (viewModel.pass.value.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye),
                        contentDescription = stringResource(
                            id = R.string.login_eye_icon_content_description
                        ),
                        tint = getTintForEyeIcon(
                            viewModel.pass.value.isNotEmpty(),
                            showingPass,
                            passFocused,
                            viewModel.passError.collectAsState().value.hasError
                        ),
                        modifier = Modifier.clickable {
                            showingPass = !showingPass
                            passFocusRequester.requestFocus()
                        }
                    )
                }
            },
            isError = viewModel.passError.collectAsState().value.hasError,
            visualTransformation = if (showingPass && passFocused) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions {
                signInAction(
                    viewModel,
                    keyboardController
                )
            }
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

        Spacer(modifier = Modifier.padding(8.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login_button)
        ) {
            signInAction(viewModel, keyboardController)
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(id = R.string.login_forgot_password),
            style = linkStyle(),
            modifier = Modifier.clickable {
                onCurrentFragmentChange(LogonSelectedFragment.RestorePass)
                viewModel.dispose()
            }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(
            mainAxisAlignment = MainAxisAlignment.Center
        ) {
            Text(text = stringResource(id = R.string.login_register))
            Text(
                text = stringResource(id = R.string.login_register_link),
                style = linkStyle(),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable {
                        onCurrentFragmentChange(LogonSelectedFragment.SignUp)
                        viewModel.dispose()
                    }
            )
        }

        if (state is SignInViewModel.State.Error) {
            Dialog(
                model = state.error,
                dismissAction = { viewModel.dismissError() }
            )
        }
    }
}

@Composable
private fun getTintForEyeIcon(
    hasCharacters: Boolean,
    isShowingPass: Boolean,
    passFocused: Boolean,
    passError: Boolean
): Color {
    return if (hasCharacters) {
        if (isShowingPass && passFocused) {
            if (passError) ErrorColor else PrimaryColor
        } else {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    } else {
        Color.Transparent
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun signInAction(
    viewModel: SignInViewModel,
    keyboardController: SoftwareKeyboardController?
) {
    viewModel.login()
    keyboardController?.hide()
}

@Preview(showBackground = true)
@Composable
private fun SignInFragmentPreview() {
    Box(modifier = Modifier.padding(10.dp)) {
        SignInFragment {}
    }
}