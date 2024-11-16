package com.composeapp.learnadaptivelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.composeapp.learnadaptivelayout.ui.theme.LearnAdaptiveLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnAdaptiveLayoutTheme {

                var navController by remember {
                    mutableStateOf(AppRoutes.Home)
                }

                val windowSize = with(LocalDensity.current) {
                    currentWindowSize().toSize().toDpSize()
                }

                val layoutType = if (windowSize.width >= 1000.dp) {
                    NavigationSuiteType.NavigationDrawer
                } else {
                    NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                        currentWindowAdaptiveInfo()
                    )
                }
                NavigationSuiteScaffold(layoutType = layoutType, navigationSuiteItems = {
                    AppRoutes.entries.forEach { route ->
                        item(
                            selected = navController == route,
                            onClick = { navController = route },
                            label = {
                                Text(
                                    text = route.route,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = route.icon,
                                    contentDescription = route.route
                                )
                            }
                        )
                    }
                }){
                    when(navController){
                        AppRoutes.Home -> Screen(title = "Home")
                        AppRoutes.Cart -> Screen(title = "Cart")
                        AppRoutes.Profile -> Screen(title = "Profile")
                        AppRoutes.Settings -> Screen(title = "Settings")
                    }
                }


            }
        }
    }
}


@Composable
fun Screen(
    title: String,
){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title, style = MaterialTheme.typography.headlineLarge)
    }
}

enum class AppRoutes(val route: String, val icon: ImageVector) {
    Home("Home", Icons.Filled.Home),
    Cart("Cart", Icons.Filled.ShoppingCart),
    Profile("Profile", Icons.Filled.AccountCircle),
    Settings("Settings", Icons.Filled.Settings)

}