package com.jgpleo.ui_common.component.dialog

data class DialogModel(
    val showDialog: Boolean = false,
    val title: Int = -1,
    val description: Int = -1,
    val primaryButtonText: Int = -1,
    val primaryButtonAction: () -> Unit = {},
    val secondaryButtonText: Int? = null,
    val dismissOnBackPress: Boolean = false,
    val dismissOnClickOut: Boolean = false
)
