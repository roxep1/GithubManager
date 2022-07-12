package com.bashkir.githubmanager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.bashkir.githubmanager.base.formatCutToString
import com.bashkir.githubmanager.data.models.Owner
import com.bashkir.githubmanager.data.models.Repository
import com.bashkir.githubmanager.ui.theme.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import java.time.LocalDateTime

@Composable
fun RepositoryCard(repository: Repository, onClick: () -> Unit) =
    Card(
        Modifier
            .fillMaxWidth()
            .padding(top = normalPadding)
            .padding(horizontal = smallPadding),
        RoundedCornerShape(10.dp),
        elevation = normalElevation
    ) {
        Column(Modifier.padding(horizontal = normalPadding, vertical = smallPadding)) {
            Row {
                SubcomposeAsyncImage(
                    model = repository.owner.avatarUrl,
                    null,
                    Modifier.padding(smallPadding),
                    loading = { CircularProgressIndicator() },
                    contentScale = ContentScale.Crop
                )

                Column {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically
                    ) {
                        Text(repository.name, fontSize = titleText, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = CenterVertically) {
                            Text(
                                repository.starsCount.toString(),
                                Modifier.padding(horizontal = smallPadding),
                                fontSize = normalText
                            )
                            Icon(Icons.Outlined.Star, null)
                        }
                    }
                    if (repository.description != null)
                        Text(
                            repository.description,
                            fontSize = normalText,
                            textAlign = TextAlign.Justify
                        )
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Изменено: ${repository.updatedAt.formatCutToString()}",
                    style = graySmallText
                )
                if (repository.language != null)
                    Text("Язык: ${repository.language}", style = graySmallText)
                else Spacer(Modifier)
            }
        }
    }

@Composable
fun RepositoryCardPlaceholder() = Card(
    Modifier
        .fillMaxWidth()
        .padding(vertical = smallPadding, horizontal = normalPadding)
        .placeholder(
            visible = true,
            color = Color.LightGray,
            highlight = PlaceholderHighlight.fade(Color.White),
            shape = RoundedCornerShape(10.dp)
        ),
    elevation = normalElevation
) {
    Spacer(Modifier.height(80.dp))
}