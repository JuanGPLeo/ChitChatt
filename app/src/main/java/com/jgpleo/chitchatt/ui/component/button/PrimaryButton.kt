package com.jgpleo.chitchatt.ui.component.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jgpleo.chitchatt.ui.theme.PrimaryColor
import com.jgpleo.chitchatt.ui.theme.PrimaryColorVariant

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit
) {
    GradientButton(
        modifier = modifier,
        text = text,
        textColor = Color.White,
        gradient = Brush.horizontalGradient(listOf(PrimaryColor, PrimaryColorVariant))
    ) {
        onClickAction()
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    PrimaryButton(text = "Primary button") {}
}