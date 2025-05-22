package features.booklist.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.bookmark_favorite
import bookpedia.composeapp.generated.resources.bookmark_unfavorite

/**
 * Custom bookmark icon that displays either a filled or outlined bookmark
 * depending on the favorite status
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun BookmarkIcon(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
) {
    // Use bookmark resources from composeResources
    val iconResource = remember(isFavorite) {
        if (isFavorite) Res.drawable.bookmark_favorite else Res.drawable.bookmark_unfavorite
    }

    Image(
        painter = painterResource(iconResource),
        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
        modifier = modifier
    )
}