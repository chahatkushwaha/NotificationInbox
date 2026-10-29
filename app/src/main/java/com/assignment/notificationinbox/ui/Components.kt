package com.assignment.notificationinbox.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.assignment.notificationinbox.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTime(millis: Long): String =
    try { SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(millis)) }
    catch (e: Exception) { "" }

/** Shows a placeholder when the URL is missing, invalid, or fails to load. */
@Composable
fun Banner(url: String?, modifier: Modifier) {
    val placeholder = painterResource(R.drawable.ic_image_placeholder)
    if (url.isNullOrBlank()) {
        Image(placeholder, null, modifier, contentScale = ContentScale.Crop)
    } else {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop,
            placeholder = placeholder,
            error = placeholder,
            fallback = placeholder
        )
    }
}
