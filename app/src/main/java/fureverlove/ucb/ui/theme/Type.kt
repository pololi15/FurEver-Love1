package fureverlove.ucb.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fureverlove.ucb.R

// Fuentes locales (archivos .ttf)
val CoinyFontFamily = FontFamily(
    Font(R.font.coiny, FontWeight.Normal)
)

val RibeyeFontFamily = FontFamily(
    Font(R.font.ribeye, FontWeight.Normal)
)



// Definimos HandwrittenFontFamily usando Ribeye como base
val HandwrittenFontFamily = RibeyeFontFamily

// Paleta de colores pasteles
val pastelPink = Color(0xFF181717)
val pastelBlue = Color(0xFF5FA0E1)
val pastelGreen = Color(0xFF335433)
val pastelPurple = Color(0xFF381F64)
val textGray = Color(0xFF100F0F)
val shadowColor = Color(0x33000000)

// Configuración tipográfica
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        color = pastelPurple,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        color = pastelBlue
    ),
    displaySmall = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        color = pastelGreen
    ),
    headlineLarge = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        color = pastelPink
    ),
    headlineMedium = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        color = pastelBlue
    ),
    headlineSmall = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        color = pastelGreen
    ),
    titleLarge = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        color = pastelPurple,
        shadow = Shadow(
            color = shadowColor,
            offset = Offset(1f, 1f),
            blurRadius = 3f
        )
    ),
    titleMedium = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        color = pastelBlue
    ),
    titleSmall = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = pastelGreen
    ),
    bodyLarge = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = textGray,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = textGray
    ),
    bodySmall = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = textGray
    ),
    labelLarge = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = pastelPink
    ),
    labelMedium = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = pastelBlue
    ),
    labelSmall = TextStyle(
        fontFamily = HandwrittenFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        color = pastelGreen,
        letterSpacing = 0.5.sp
    )
)