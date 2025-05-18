package features.booklist.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.booklist.data.remote.TrendingCategory
import features.booklist.data.remote.TrendingTimeRange

@Composable
fun TrendingTabs(
    selectedCategory: TrendingCategory,
    selectedTimeRange: TrendingTimeRange,
    onCategorySelected: (TrendingCategory) -> Unit,
    onTimeRangeSelected: (TrendingTimeRange) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Time Range Tabs
        ScrollableTabRow(
            selectedTabIndex = TrendingTimeRange.entries.indexOf(selectedTimeRange),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[TrendingTimeRange.entries.indexOf(selectedTimeRange)]),
                    height = 3.dp,
                    color = MaterialTheme.colors.primary
                )
            }
        ) {
            TrendingTimeRange.entries.forEach { timeRange ->
                val selected = timeRange == selectedTimeRange
                val textColor by animateColorAsState(
                    targetValue = if (selected) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )

                Tab(
                    selected = selected,
                    onClick = { onTimeRangeSelected(timeRange) },
                    text = {
                        Text(
                            text = when (timeRange) {
                                TrendingTimeRange.THIS_WEEK -> "This Week"
                                TrendingTimeRange.THIS_MONTH -> "This Month"
                                TrendingTimeRange.THIS_YEAR -> "This Year"
                            },
                            color = textColor,
                            style = MaterialTheme.typography.button.copy(
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Category Chips
        ScrollableTabRow(
            selectedTabIndex = TrendingCategory.entries.indexOf(selectedCategory),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[TrendingCategory.entries.indexOf(selectedCategory)]),
                    height = 3.dp,
                    color = MaterialTheme.colors.primary
                )
            }
        ) {
            TrendingCategory.entries.forEach { category ->
                val selected = category == selectedCategory
                val textColor by animateColorAsState(
                    targetValue = if (selected) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )

                Tab(
                    selected = selected,
                    onClick = { onCategorySelected(category) },
                    text = {
                        Text(
                            text = when (category) {
                                TrendingCategory.FICTION -> "Fiction"
                                TrendingCategory.NON_FICTION -> "Non-Fiction"
                                TrendingCategory.BUSINESS -> "Business"
                                TrendingCategory.TECHNOLOGY -> "Technology"
                                TrendingCategory.SCIENCE -> "Science"
                                TrendingCategory.ROMANCE -> "Romance"
                                TrendingCategory.MYSTERY -> "Mystery"
                                TrendingCategory.BIOGRAPHY -> "Biography"
                            },
                            color = textColor,
                            style = MaterialTheme.typography.button.copy(
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    }
                )
            }
        }
    }
}