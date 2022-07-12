package com.bashkir.githubmanager.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.bashkir.githubmanager.BuildConfig
import com.bashkir.githubmanager.ui.theme.normalPadding
import com.bashkir.githubmanager.ui.theme.titleText

@Composable
fun AboutScreen() = Column(Modifier.padding(normalPadding)) {
    Text(
        "О программе",
        Modifier.align(CenterHorizontally),
        fontSize = titleText,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(normalPadding))
    Text("Версия: ${BuildConfig.VERSION_NAME} ${BuildConfig.VERSION_CODE}")
    Text("Функционал: ${BuildConfig.FLAVOR}")
    Text("ID приложения: ${BuildConfig.APPLICATION_ID}")
    if (BuildConfig.DEBUG)
        Text("Версия для тестирования")
}