package com.jgpleo.ui_common.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    gradient: Brush,
    onClickAction: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .height(56.dp)
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, Color.Transparent),
        contentPadding = PaddingValues(),
        shape = MaterialTheme.shapes.medium,
        onClick = { onClickAction() }
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .border(
                    brush = gradient,
                    width = 1.dp,
                    shape = MaterialTheme.shapes.medium,
                )
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