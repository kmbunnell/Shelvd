package com.shelvd.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.shelvd.R
import com.shelvd.ui.navigation.BottomNav
import com.shelvd.ui.navigation.BottomNavRoutes
import com.shelvd.ui.theme.ShelvdTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShelvdTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar(
                            actions = {
                                IconButton(onClick = { navController.navigate(BottomNavRoutes.Home.route) })
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = stringResource(R.string.home)
                                    )

                                }
                                IconButton(onClick = { navController.navigate(BottomNavRoutes.Scan.route) })
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Create,
                                        contentDescription = stringResource(R.string.add_book)
                                    )
                                }

                            })
                    }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding).fillMaxSize())
                    {
                        BottomNav(navController)
                    }
                }
            }
        }
    }
}
