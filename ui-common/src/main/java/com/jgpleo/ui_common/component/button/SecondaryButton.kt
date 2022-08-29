package com.jgpleo.ui_common.component.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.jgpleo.ui_common.theme.PrimaryColor
import com.jgpleo.ui_common.theme.PrimaryColorVariant

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClickAction: () -> Unit
) {
    OutlinedGradientButton(
        modifier = modifier,
        text = text,
        textColor = PrimaryColor,
        gradient = Brush.horizontalGradient(listOf(PrimaryColor, PrimaryColorVariant))
    ) {
        onClickAction()
    }
}

@Preview
@Composable
fun SecondaryButtonPreview() {
    SecondaryButton(text = "Secondary button") {}
}