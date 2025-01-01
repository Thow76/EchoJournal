import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.echojournal.R



// =============================
// Font Definitions
// =============================

// Google Font Provider Configuration
val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Inter FontFamily with Regular and Medium weights
val Inter = FontFamily(
    Font(
        googleFont = GoogleFont("Inter"),
        fontProvider = googleFontProvider,
        weight = FontWeight.Normal // Normal
    ),
    Font(
        googleFont = GoogleFont("Inter"),
        fontProvider = googleFontProvider,
        weight = FontWeight.Medium // Medium
    )
)

// =============================
// Typography Definitions
// =============================

val AppTypography = Typography(
    // =============================
    // Headline Styles
    // =============================

    headlineLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 26.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),

    // =============================
    // Body Styles
    // =============================

    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal, // Regular
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal, // Regular
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),

    // =============================
    // Button Styles
    // =============================

    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    // =============================
    // Label Styles
    // =============================

    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp // Interpreted as a specific line height
    )
)

// =============================
// Additional Text Styles
// =============================

object AdditionalTextStyles {
    val headlineXSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp, // Updated to match specification
        lineHeight = 18.sp // Updated to match specification
    )
}
