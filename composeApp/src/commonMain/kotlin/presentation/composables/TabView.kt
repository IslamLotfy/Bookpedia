package presentation.composables

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.LightBlue

@Composable
fun TabView(
    pagerState: PagerState,
    data: List<String>,
    coroutineScope: CoroutineScope,
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
    ) {
        data.forEachIndexed { index, item ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                selectedContentColor = Color.Magenta,
                unselectedContentColor = Color.LightGray,
                text = {
                    Text(
                        item,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    }
}
