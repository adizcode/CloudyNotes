package com.github.adizcode.notes2cloud9.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.adizcode.notes2cloud9.ui.main.MyNotes

fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable(NavGraphs.MAIN.route) { MyNotes() }
}