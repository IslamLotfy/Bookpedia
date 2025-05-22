import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.ic_fav_books
import bookpedia.composeapp.generated.resources.ic_fav_books_filled
import core.theme.BookpediaTheme
import features.booklist.presentation.BookDetailsScreen
import features.booklist.presentation.BookListScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    BookpediaTheme {
        KoinContext {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            
            Scaffold(
                bottomBar = {
                    // Only show bottom navigation when not on details screen
                    val showBottomBar = currentDestination?.route?.startsWith("bookDetails") != true
                    if (showBottomBar) {
                        BottomNavigation(
                            backgroundColor = MaterialTheme.colors.surface,
                            contentColor = MaterialTheme.colors.primary
                        ) {
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Home"
                                    )
                                },
                                label = { Text("Home") },
                                selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
                                onClick = {
                                    navController.navigate("home") {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )

                            val isSelected = currentDestination?.hierarchy?.any { it.route == "favorites" } == true

                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(if (isSelected) Res.drawable.ic_fav_books_filled else Res.drawable.ic_fav_books),
                                        contentDescription = "Favorites",
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = { Text("Favorites") },
                                selected = isSelected,
                                onClick = {
                                    navController.navigate("favorites") {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                selectedContentColor = MaterialTheme.colors.primary,
                                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(
                        route = "home",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        }
                    ) {
                        BookListScreen(
                            viewModel = koinViewModel(),
                            onNavigateToDetails = { bookId ->
                                navController.navigate("bookDetails/$bookId")
                            }
                        )
                    }

                    composable(
                        route = "favorites",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        }
                    ) {
                        BookListScreen(
                            viewModel = koinViewModel(),
                            showFavoritesOnly = true,
                            onNavigateToDetails = { bookId ->
                                navController.navigate("bookDetails/$bookId")
                            }
                        )
                    }

                    // Book details screen with bookId as parameter
                    composable(
                        route = "bookDetails/{bookId}",
                        arguments = listOf(
                            navArgument("bookId") {
                                type = NavType.StringType
                            }
                        ),
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        }
                    ) { backStackEntry ->
                        val bookId = backStackEntry.arguments?.getString("bookId")
                            ?: return@composable

                        BookDetailsScreen(
                            bookId = bookId,
                            onBackPressed = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}