package com.shelvd.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shelvd.ui.screens.scanBook.ScannBookRoute
import com.shelvd.ui.screens.shelves.ShelvesRoute


enum class BottomNavRoutes(val route: String) {
    Home("home"),
    Scan ("scan")
}

@Composable
fun BottomNav(navController: NavHostController){

    NavHost(navController=navController, startDestination = BottomNavRoutes.Home.route ){
        composable(BottomNavRoutes.Home.route) { ShelvesRoute() }
        composable(BottomNavRoutes.Scan.route) { ScannBookRoute() }
    }
}