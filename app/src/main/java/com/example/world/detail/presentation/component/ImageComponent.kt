package com.example.world.detail.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter

/**
 * Image component
 *
 * @param modifier to modify
 * @param imageUrl the url of image to be loaded
 * @param imageLoader image loader instance to load the image
 * @param contentDescription description of the image
 * @param contentScale to scale the image as fit, crop , fill heights etc
 */
@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageLoader: ImageLoader,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Crop
) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl, imageLoader = imageLoader),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}