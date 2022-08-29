package com.jgpleo.chitchatt.ui.screen.logon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.jgpleo.chitchatt.R
import com.jgpleo.chitchatt.ui.component.button.PrimaryButton
import com.jgpleo.chitchatt.ui.theme.PrimaryColor
import com.jgpleo.chitchatt.ui.theme.linkStyle
import com.jgpleo.chitchatt.ui.theme.titleStyle

@Composable
fun SignInFragment(
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var emailFocused by remember { mutableStateOf(false) }

    var pass by remember { mutableStateOf(TextFieldValue("")) }
    var passFocused by remember { mutableStateOf(false) }
    var showingPass by remember { mutableStateOf(false) }
    val passFocusRequester = remember { FocusRequester() }

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
                    tint = if (emailFocused) PrimaryColor else LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
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
                    tint = if (passFocused) PrimaryColor else LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            trailingIcon = {
                if (pass.text.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye),
                        contentDescription = stringResource(
                            id = R.string.login_eye_icon_content_description
                        ),
                        tint = getTintForEyeIcon(pass.text.isNotEmpty(), showingPass, passFocused),
                        modifier = Modifier.clickable {
                            showingPass = !showingPass
                            passFocusRequester.requestFocus()
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                signInAction()
            },
            onValueChange = { pass = it }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login_button)
        ) {
            signInAction()
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(id = R.string.login_forgot_password),
            style = linkStyle(),
            modifier = Modifier.clickable {
                onCurrentFragmentChange(LogonSelectedFragment.RestorePass)
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
                    }
            )
        }
    }
}

@Composable
private fun getTintForEyeIcon(
    hasCharacters: Boolean,
    isShowingPass: Boolean,
    passFocused: Boolean
): Color {
    return if (hasCharacters) {
        if (isShowingPass && passFocused) {
            PrimaryColor
        } else {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    } else {
        Color.Transparent
    }
}

private fun signInAction() {
    // TODO: signIn action here
    println("Hello world!!")
}

@Preview(showBackground = true)
@Composable
private fun SignInFragmentPreview() {
    Box(modifier = Modifier.padding(10.dp)) {
        SignInFragment {}
    }
}