package cc.sovellus.vrcaa.ui.components.layout

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cc.sovellus.vrcaa.R
import cc.sovellus.vrcaa.api.http.models.LimitedUser
import cc.sovellus.vrcaa.helper.StatusHelper
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FriendItem(friend: LimitedUser, callback: () -> Unit) {
    ListItem(
        headlineContent = {
            Text(friend.statusDescription.ifEmpty {
                StatusHelper.getStatusFromString(friend.status).toString()
            }, maxLines = 1)
        },
        overlineContent = { Text(friend.displayName) },
        supportingContent = {
            Text(text = if (friend.location == "offline" &&  StatusHelper.getStatusFromString(friend.status) != StatusHelper.Status.Offline) { "Active on website." } else { friend.location }, maxLines = 1)
        },
        leadingContent = {
            GlideImage(
                model = friend.userIcon.ifEmpty { friend.currentAvatarImageUrl },
                contentDescription = stringResource(R.string.preview_image_description),
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center,
                loading = placeholder(R.drawable.image_placeholder),
                failure = placeholder(R.drawable.image_placeholder)
            )
        },
        trailingContent = {
            Badge(
                containerColor = StatusHelper.getStatusFromString(friend.status).toColor(), modifier = Modifier.size(16.dp)
            )
        },
        modifier = Modifier.clickable(
            onClick = {
                callback()
            }
        )
    )
}