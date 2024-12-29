package cc.sovellus.vrcaa.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cc.sovellus.vrcaa.R
import cc.sovellus.vrcaa.api.vrchat.http.models.User
import cc.sovellus.vrcaa.helper.StatusHelper
import cc.sovellus.vrcaa.helper.TrustHelper
import cc.sovellus.vrcaa.ui.components.card.ProfileCard
import cc.sovellus.vrcaa.ui.components.misc.Description
import cc.sovellus.vrcaa.ui.components.misc.SubHeader
import cc.sovellus.vrcaa.ui.screen.misc.LoadingIndicatorScreen
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

class ProfileScreen : Screen {

    override val key = uniqueScreenKey

    @Composable
    override fun Content() {

        val model = rememberScreenModel { ProfileScreenModel() }

        val state by model.state.collectAsState()

        when (val result = state) {
            is ProfileScreenModel.ProfileState.Loading -> LoadingIndicatorScreen().Content()
            is ProfileScreenModel.ProfileState.Result -> RenderProfile(result.profile)
            else -> {}
        }
    }

    @Composable
    private fun RenderProfile(profile: User) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileCard(
                thumbnailUrl = profile.profilePicOverride.ifEmpty { profile.currentAvatarImageUrl },
                iconUrl = profile.userIcon.ifEmpty {
                    profile.profilePicOverride.ifEmpty { profile.currentAvatarImageUrl }
                },
                displayName = profile.displayName,
                statusDescription = profile.statusDescription.ifEmpty {
                    StatusHelper.getStatusFromString(profile.status).toString()
                },
                trustRankColor = TrustHelper.getTrustRankFromTags(profile.tags).toColor(),
                statusColor = StatusHelper.getStatusFromString(profile.status).toColor(),
                tags = profile.tags,
                badges = profile.badges
            )

            Spacer(modifier = Modifier.height(16.dp))

            ElevatedCard(
                modifier = Modifier
                    .widthIn(Dp.Unspecified, 520.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SubHeader(title = stringResource(R.string.profile_label_biography))
                    Description(text = profile.bio)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    if (profile.lastActivity.isNotEmpty()) {
                        val userTimeZone = TimeZone.getDefault().toZoneId()
                        val formatter =
                            DateTimeFormatter.ofLocalizedDateTime(java.time.format.FormatStyle.SHORT)
                                .withLocale(Locale.getDefault())

                        val lastActivity = ZonedDateTime.parse(profile.lastActivity)
                            .withZoneSameInstant(userTimeZone).format(formatter)

                        SubHeader(title = stringResource(R.string.profile_label_last_activity))
                        Description(text = lastActivity)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    }

                    SubHeader(title = stringResource(R.string.profile_label_date_joined))
                    Description(text = profile.dateJoined)
                }
            }
        }
    }
}