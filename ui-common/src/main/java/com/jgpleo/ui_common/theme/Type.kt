package com.jgpleo.ui_common.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jgpleo.ui_common.R

private val InterFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val TypographyLight = Typography(
    defaultFontFamily = InterFontFamily,
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color.Black
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Gray600
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = PrimaryColor
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 10.sp,
        color = Gray600
    )
)

val TypographyDark = Typography(
    defaultFontFamily = InterFontFamily,
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Gray400
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = PrimaryColor
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 10.sp
    )
)

@Composable
fun titleStyle() = MaterialTheme.typography.h1

@Composable
fun titleToolbarStyle() = TextStyle(
    fontFamily = InterFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = Color.White
)

@Composable
fun subtitleStyle() = MaterialTheme.typography.h2

@Composable
fun bodyStyle() = MaterialTheme.typography.body1

@Composable
fun linkStyle() = MaterialTheme.typography.body2

@Composable
fun captionStyle() = MaterialTheme.typography.caption

@Composable
fun logoStyle() = TextStyle(
    fontFamily = InterFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp,
    color = PrimaryColor
)