package core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Color palette
val Purple80 = Color(0xFF6650a4)
val PurpleGrey80 = Color(0xFF625b71)
val Pink80 = Color(0xFF7D5260)

val Purple40 = Color(0xFF7F67BE)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val CardBackground = Color(0xFFF5F5F5)
val TextPrimary = Color(0xFF1A1A1A)
val TextSecondary = Color(0xFF666666)

// Material 2 Colors
private val DarkColorPalette = darkColors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Teal200,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Teal200,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

// Material 3 Colors
data class ExtendedColors(
    val material3Primary: Color,
    val material3OnPrimary: Color,
    val material3PrimaryContainer: Color,
    val material3OnPrimaryContainer: Color,
    val material3Secondary: Color,
    val material3OnSecondary: Color,
    val material3SecondaryContainer: Color,
    val material3OnSecondaryContainer: Color,
    val material3Tertiary: Color,
    val material3OnTertiary: Color,
    val material3TertiaryContainer: Color,
    val material3OnTertiaryContainer: Color,
    val material3Error: Color,
    val material3OnError: Color,
    val material3ErrorContainer: Color,
    val material3OnErrorContainer: Color,
    val material3Background: Color,
    val material3OnBackground: Color,
    val material3Surface: Color,
    val material3OnSurface: Color,
    val material3SurfaceVariant: Color,
    val material3OnSurfaceVariant: Color,
    val material3Outline: Color,
    val material3OutlineVariant: Color,
    val material3Scrim: Color,
    val material3InverseSurface: Color,
    val material3InverseOnSurface: Color,
    val material3InversePrimary: Color,
)

private val DarkExtendedColors = ExtendedColors(
    material3Primary = Blue500,
    material3OnPrimary = Color.White,
    material3PrimaryContainer = Blue700,
    material3OnPrimaryContainer = Color.White,
    material3Secondary = Teal200,
    material3OnSecondary = Color.Black,
    material3SecondaryContainer = Teal700,
    material3OnSecondaryContainer = Color.White,
    material3Tertiary = Orange500,
    material3OnTertiary = Color.White,
    material3TertiaryContainer = Orange700,
    material3OnTertiaryContainer = Color.White,
    material3Error = Red500,
    material3OnError = Color.White,
    material3ErrorContainer = Red700,
    material3OnErrorContainer = Color.White,
    material3Background = DarkBackground,
    material3OnBackground = Color.White,
    material3Surface = DarkSurface,
    material3OnSurface = Color.White,
    material3SurfaceVariant = DarkSurfaceVariant,
    material3OnSurfaceVariant = Color.White.copy(alpha = 0.7f),
    material3Outline = Color.White.copy(alpha = 0.3f),
    material3OutlineVariant = Color.White.copy(alpha = 0.1f),
    material3Scrim = Color.Black.copy(alpha = 0.3f),
    material3InverseSurface = Color.White,
    material3InverseOnSurface = Color.Black,
    material3InversePrimary = Blue300
)

private val LightExtendedColors = ExtendedColors(
    material3Primary = Blue500,
    material3OnPrimary = Color.White,
    material3PrimaryContainer = Blue100,
    material3OnPrimaryContainer = Blue900,
    material3Secondary = Teal200,
    material3OnSecondary = Color.Black,
    material3SecondaryContainer = Teal50,
    material3OnSecondaryContainer = Teal900,
    material3Tertiary = Orange500,
    material3OnTertiary = Color.White,
    material3TertiaryContainer = Orange100,
    material3OnTertiaryContainer = Orange900,
    material3Error = Red500,
    material3OnError = Color.White,
    material3ErrorContainer = Red100,
    material3OnErrorContainer = Red900,
    material3Background = LightBackground,
    material3OnBackground = Color.Black,
    material3Surface = LightSurface,
    material3OnSurface = Color.Black,
    material3SurfaceVariant = LightSurfaceVariant,
    material3OnSurfaceVariant = Color.Black.copy(alpha = 0.7f),
    material3Outline = Color.Black.copy(alpha = 0.3f),
    material3OutlineVariant = Color.Black.copy(alpha = 0.1f),
    material3Scrim = Color.Black.copy(alpha = 0.3f),
    material3InverseSurface = Color.Black,
    material3InverseOnSurface = Color.White,
    material3InversePrimary = Blue300
)

val LocalExtendedColors = staticCompositionLocalOf {
    LightExtendedColors
}

@Composable
fun BookpediaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    
    val extendedColors = if (darkTheme) {
        DarkExtendedColors
    } else {
        LightExtendedColors
    }

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
} 