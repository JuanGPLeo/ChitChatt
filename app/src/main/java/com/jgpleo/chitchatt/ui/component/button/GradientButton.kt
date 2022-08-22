package com.jgpleo.chitchatt.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    gradient: Brush,
    onClickAction: () -> Unit
) {
    Button(
        modifier = Modifier
            .height(56.dp)
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        shape = MaterialTheme.shapes.medium,
        onClick = { onClickAction() }
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .height(56.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .then(modifier),
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
private fun GradientButtonPreview() {
    GradientButton(
        text = "Click me",
        textColor = Color.White,
        gradient = Brush.horizontalGradient(
            listOf(
                Color(0xFF5433FF),
                Color(0xFF20BDFF),
                Color(0xFFA5FECB)
            )
        )
    ) {}
}