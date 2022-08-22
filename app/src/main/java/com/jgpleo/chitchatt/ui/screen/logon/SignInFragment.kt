package com.jgpleo.chitchatt.ui.screen.logon

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
import com.jgpleo.chitchatt.R
import com.jgpleo.chitchatt.ui.component.button.PrimaryButton
import com.jgpleo.chitchatt.ui.theme.linkStyle
import com.jgpleo.chitchatt.ui.theme.titleStyle

@Composable
fun SignInFragment(
    onCurrentFragmentChange: (current: LogonSelectedFragment) -> Unit
) {

    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var pass by remember { mutableStateOf(TextFieldValue("")) }


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
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.login_phone)) },
            value = phone,
            onValueChange = { phone = it }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.login_pass)) },
            value = pass,
            onValueChange = { pass = it }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login_button)
        ) {
            // TODO: onClick action
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

        Row {
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

@Preview(showBackground = true)
@Composable
private fun SignInFragmentPreview() {
    Box(modifier = Modifier.padding(10.dp)) {
        SignInFragment {}
    }
}