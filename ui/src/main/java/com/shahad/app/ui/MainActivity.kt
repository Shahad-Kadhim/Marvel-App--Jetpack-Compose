package com.shahad.app.ui

import android.annotation.SuppressLint
import androidx.compose.material.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shahad.app.core.Constants
import com.shahad.app.ui.theme.MarvelAppTheme
import com.shahad.app.viewmodels.FavoriteViewModel
import com.shahad.app.viewmodels.HomeViewModel
import com.shahad.app.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun MainScreen(navController: NavHostController) {
        val items = listOf(Screen.HomeScreen, Screen.SearchScreen, Screen.FavouriteScreen)
        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(screen.iconId),
                                    contentDescription = null
                                )
                            },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) {
            NavHost(navController = navController, startDestination = Constants.HOME_ROUTE) {
                composable(
                    route = Screen.HomeScreen.route,
                    content = {
                        val viewModel: HomeViewModel by viewModels()
                        Home(navController,viewModel)
                    }
                )

                composable(
                    route = Screen.SearchScreen.route,
                    content = {
                        val viewModel: SearchViewModel by viewModels()
                        Search(navController,viewModel)
                    }
                )

                composable(
                    route = Screen.FavouriteScreen.route,
                    content = {
                        val viewModel: FavoriteViewModel by viewModels()
                        FavoriteScreen(navController = navController, viewModel = viewModel)
                    }
                )

            }

        }

    }
}
