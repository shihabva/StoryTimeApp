package com.example.storytimeadventures

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.storytimeadventures.ui.screens.StoryListScreen
import com.example.storytimeadventures.ui.screens.WebViewScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun StoryApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "storyList") {
        composable("storyList") {
            // VERY IMPORTANT: Replace YOUR_GITHUB_USERNAME with your actual username.
            val libraryUrl = "https://raw.githubusercontent.com/YOUR_GITHUB_USERNAME/MyStorybookAssets/main/library.json"
            StoryListScreen(
                libraryUrl = libraryUrl,
                onStoryClick = { storyUrl ->
                    val encodedUrl = URLEncoder.encode(storyUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate("webView/$encodedUrl")
                }
            )
        }
        composable(
            "webView/{storyUrl}",
            arguments = listOf(navArgument("storyUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val storyUrl = backStackEntry.arguments?.getString("storyUrl") ?: ""
            WebViewScreen(url = storyUrl)
        }
    }
}
