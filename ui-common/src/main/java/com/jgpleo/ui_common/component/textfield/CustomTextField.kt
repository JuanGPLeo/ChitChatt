package com.jgpleo.ui_common.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.jgpleo.ui_common.theme.ErrorColor
import com.jgpleo.ui_common.theme.PrimaryColor

@Composable
fun CustomTextField(
    label: Int,
    value: String,
    leadingIcon: Int,
    leadingIconContentDescription: Int,
    singleLine: Boolean = true,
    error: TextFieldError = TextFieldError(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit
) {
    var focused by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focused = it.isFocused },
            label = { Text(text = stringResource(id = label)) },
            value = value,
            singleLine = singleLine,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = stringResource(
                        id = leadingIconContentDescription
                    ),
                    tint = if (error.hasError) {
                        ErrorColor
                    } else if (focused) {
                        PrimaryColor
                    } else {
                        LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                    }
                )
            },
            isError = error.hasError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onValueChange = onValueChange
        )

        if (error.hasError && error.errorMessage != null) {
            Text(
                text = stringResource(id = error.errorMessage),
                color = ErrorColor
            )
        }
    }
}