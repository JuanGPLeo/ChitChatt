package com.jgpleo.ui_common.component.dialog

data class DialogModel(
    val showDialog: Boolean = false,
    val title: String = "",
    val description: String = "",
    val primaryButtonText: String = "",
    val primaryButtonAction: () -> Unit = {},
    val secondaryButtonText: String? = null,
    val dismissOnBackPress: Boolean = false,
    val dismissOnClickOut: Boolean = false
)
