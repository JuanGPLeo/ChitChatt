@file:OptIn(ExperimentalComposeUiApi::class)

package com.jgpleo.chitchatt.logon.screen.signin

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
import com.jgpleo.chitchatt.logon.mapper.ErrorMapper
import com.jgpleo.chitchatt.logon.screen.LogonSelectedFragment
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.button.PrimaryButton
import com.jgpleo.ui_common.component.dialog.Dialog
import com.jgpleo.ui_common.component.dialog.DialogModel
import com.jgpleo.ui_common.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInFragment(
    viewModel: SignInViewModel = viewModel(),
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    val state = viewModel.state.collectAsState().value
    val errorState = viewModel.errorState.collectAsState()

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var emailFocused by remember { mutableStateOf(false) }
    val emailError = viewModel.emailError.collectAsState().value

    var pass by remember { mutableStateOf(TextFieldValue("")) }
    var passFocused by remember { mutableStateOf(false) }
    var showingPass by remember { mutableStateOf(false) }
    val passFocusRequester = remember { FocusRequester() }
    val passError = viewModel.passError.collectAsState().value

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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { emailFocused = it.isFocused },
            label = { Text(text = stringResource(id = R.string.login_email)) },
            value = email,
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mail),
                    contentDescription = stringResource(
                        id = R.string.login_lock_icon_content_description
                    ),
                    tint = if (emailError) {
                        ErrorColor
                    } else if (emailFocused) {
                        PrimaryColor
                    } else {
                        LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    }
                )
            },
            isError = emailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        OutlinedTextField(
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
            value = pass,
            singleLine = true,
            visualTransformation = if (showingPass && passFocused) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = stringResource(
                        id = R.string.login_lock_icon_content_description
                    ),
                    tint = if (passError) {
                        ErrorColor
                    } else if (passFocused) {
                        PrimaryColor
                    } else {
                        LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    }
                )
            },
            trailingIcon = {
                if (pass.text.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye),
                        contentDescription = stringResource(
                            id = R.string.login_eye_icon_content_description
                        ),
                        tint = getTintForEyeIcon(
                            pass.text.isNotEmpty(),
                            showingPass,
                            passFocused,
                            passError
                        ),
                        modifier = Modifier.clickable {
                            showingPass = !showingPass
                            passFocusRequester.requestFocus()
                        }
                    )
                }
            },
            isError = passError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions {
                signInAction(
                    viewModel,
                    email.text,
                    pass.text,
                    keyboardController
                )
            },
            onValueChange = { pass = it }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login_button)
        ) {
            signInAction(viewModel, email.text, pass.text, keyboardController)
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
//            val errorModel = ErrorMapper.map(state.error)
            Dialog(
                model = errorState,
                dismissAction = { viewModel.dismissError() }
            )
//            AlertDialog(
//                properties = DialogProperties(
//                    dismissOnBackPress = true,
//                    dismissOnClickOutside = true
//                ),
//                onDismissRequest = {
//                    viewModel.dispose()
//                },
//                title = {
//                    Row(
//                        horizontalArrangement = Arrangement.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(
//                            text = errorModel.title,
//                            style = titleStyle()
//                        )
//                    }
//                },
//                text = {
//                    Text(
//                        text = errorModel.description,
//                        style = subtitleStyle()
//                    )
//                },
//                confirmButton = {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
//                    ) {
//                        PrimaryButton(
//                            text = "Ok",
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            viewModel.dispose()
//                        }
//                    }
//                },
//            )
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

private fun signInAction(
    viewModel: SignInViewModel,
    email: String,
    pass: String,
    keyboardController: SoftwareKeyboardController?
) {
    viewModel.login(email, pass)
    keyboardController?.hide()
}

@Preview(showBackground = true)
@Composable
private fun SignInFragmentPreview() {
    Box(modifier = Modifier.padding(10.dp)) {
        SignInFragment {}
    }
}