package com.bashkir.githubmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.bashkir.githubmanager.data.GithubManagerViewModel
import com.bashkir.githubmanager.data.UserState
import com.bashkir.githubmanager.data.models.User
import com.bashkir.githubmanager.ui.components.LinkText
import com.bashkir.githubmanager.ui.theme.normalPadding
import com.bashkir.githubmanager.ui.theme.smallPadding
import com.bashkir.githubmanager.ui.theme.titleText

@Composable
fun OwnerScreen(viewModel: GithubManagerViewModel, navController: NavController) {
    val user = viewModel.userState.collectAsState()

    IconButton(onClick = { navController.popBackStack() }) {
        Icon(Icons.Filled.ArrowBack, "Back button")
    }

    when (user.value) {
        is UserState.Loading -> Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        is UserState.Success -> UserScreen((user.value as UserState.Success).user)

        else -> Box(Modifier.fillMaxSize()) {
            Text("Произошла ошибка при получении аккаунта :(", Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun UserScreen(user: User) = Column(
    Modifier
        .fillMaxSize()
        .padding(normalPadding)
) {
    SubcomposeAsyncImage(
        model = user.avatarUrl,
        null,
        Modifier
            .padding(normalPadding)
            .align(CenterHorizontally),
        loading = { CircularProgressIndicator() },
        contentScale = ContentScale.Crop
    )
    SelectionContainer {
        Text(user.login, fontSize = titleText, fontWeight = FontWeight.Bold)
    }
    Spacer(Modifier.height(smallPadding))
    user.bio?.let {
        Text(user.bio, textAlign = TextAlign.Justify)
        Spacer(Modifier.height(smallPadding))
    }

    if (user.site != null && user.site.isNotBlank())
        LinkText(text = "Сайт", link = user.site)

    LinkText(text = "GitHub", link = user.htmlUrl)

    user.twitterUsername?.let {
        SelectionContainer {
            Text("Твиттер: $it")
        }
    }
    Text("Подписчиков: ${user.followers}")
    Text("Подписок: ${user.following}")
}