package com.bashkir.githubmanager.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun LinkText(text: String, link: String, modifier: Modifier = Modifier) {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {

        pushStringAnnotation(tag = "URL", annotation = link)

        withStyle(style = SpanStyle(color = Color.Blue,
            fontWeight = FontWeight.Bold)
        ) {
            append(text)
        }

        pop()
    }
    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedLinkString,
        onClick = {
            annotatedLinkString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}