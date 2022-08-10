package com.jgpleo.chitchatt.ui.component.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.jgpleo.chitchatt.ui.theme.PrimaryColor
import com.jgpleo.chitchatt.ui.theme.PrimaryColorVariant

@Composable
fun SecondaryButton(
    text: String,
    onClickAction: () -> Unit
) {
    OutlinedGradientButton(
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