package cc.sovellus.vrcaa.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Badge
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cc.sovellus.vrcaa.R
import cc.sovellus.vrcaa.api.vrchat.http.models.World
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WorldCard(
    world: World
) {
    var foundWindows by remember { mutableStateOf(false) }
    var foundAndroid by remember { mutableStateOf(false) }
    var foundDarwin by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        world.unityPackages.forEach { pkg ->
            when (pkg.platform) {
                "android" -> {
                    foundAndroid = true
                }

                "standalonewindows" -> {
                    foundWindows = true
                }

                "ios" -> {
                    foundDarwin = true
                }
            }
        }
    }

    ElevatedCard(
        modifier = Modifier
            .height(260.dp)
            .widthIn(Dp.Unspecified, 520.dp)
            .fillMaxWidth()
    ) {
        GlideImage(
            model = world.thumbnailImageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.image_placeholder),
            failure = placeholder(R.drawable.image_placeholder)
        )
        Box(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = world.name,
                    modifier = Modifier.padding(start = 12.dp),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.world_author_label).format(world.authorName),
                    modifier = Modifier.padding(start = 12.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 4.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (foundWindows) {
                    Badge(
                        containerColor = Color(0, 168, 252),
                        modifier = Modifier
                            .height(height = 26.dp)
                            .widthIn(min = 52.dp)
                            .padding(start = 2.dp, top = 8.dp),
                        content = {
                            Text(
                                text = "Windows"
                            )
                        }
                    )
                }

                if (foundAndroid) {
                    Badge(
                        containerColor = Color(103, 215, 129),
                        modifier = Modifier
                            .height(height = 26.dp)
                            .widthIn(min = 52.dp)
                            .padding(start = 2.dp, top = 8.dp),
                        content = {
                            Text(
                                text = "Android"
                            )
                        }
                    )
                }

                if (foundDarwin) {
                    Badge(
                        containerColor = Color(121, 136, 151),
                        modifier = Modifier
                            .height(height = 26.dp)
                            .widthIn(min = 52.dp)
                            .padding(start = 2.dp, top = 8.dp),
                        content = {
                            Text(
                                text = "iOS"
                            )
                        }
                    )
                }
            }
        }
    }
}
