package com.jgpleo.chitchatt.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedGradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClickAction: () -> Unit
) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, Color.Transparent),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(percent = 50),
        onClick = { onClickAction() }
    ) {
        Box(
            modifier = Modifier
                .border(
                    brush = gradient,
                    width = 1.dp,
                    shape = RoundedCornerShape(percent = 50)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
fun OutlinedGradientButtonPreview() {
    OutlinedGradientButton(
        text = "Click me",
        textColor = Color(0xFF20BDFF),
        gradient = Brush.horizontalGradient(
            listOf(
                Color(0xFF5433FF),
                Color(0xFF20BDFF),
                Color(0xFFA5FECB)
            )
        )
    ) {}
}