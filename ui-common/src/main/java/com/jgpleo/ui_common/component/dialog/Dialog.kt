package com.jgpleo.ui_common.component.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.jgpleo.ui_common.component.button.PrimaryButton
import com.jgpleo.ui_common.component.button.SecondaryButton
import com.jgpleo.ui_common.theme.subtitleStyle
import com.jgpleo.ui_common.theme.titleStyle

@Composable
fun Dialog(
    model: DialogModel,
    dismissAction: () -> Unit
) {
    with(model) {
        if (showDialog) {
            AlertDialog(
                properties = DialogProperties(
                    dismissOnBackPress = dismissOnBackPress,
                    dismissOnClickOutside = dismissOnClickOut
                ),
                onDismissRequest = { dismissAction() },
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = title),
                            style = titleStyle()
                        )
                    }
                },
                text = {
                    Text(
                        text = stringResource(id = description),
                        style = subtitleStyle()
                    )
                },
                dismissButton = {
                    secondaryButtonText?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp)
                        ) {
                            SecondaryButton(
                                text = stringResource(id = it),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                dismissAction()
                            }
                        }
                    }
                },
                confirmButton = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                    ) {
                        PrimaryButton(
                            text = stringResource(id = primaryButtonText),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            primaryButtonAction()
                            dismissAction()
                        }
                    }
                },
            )
        }
    }
}