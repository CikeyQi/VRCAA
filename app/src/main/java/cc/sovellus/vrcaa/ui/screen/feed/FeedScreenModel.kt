package cc.sovellus.vrcaa.ui.screen.feed

import cafe.adriel.voyager.core.model.ScreenModel
import cc.sovellus.vrcaa.manager.FeedManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FeedScreenModel : ScreenModel {
    private var feedStateFlow = MutableStateFlow(listOf<FeedManager.Feed>())
    var feed = feedStateFlow.asStateFlow()

    private val listener = object : FeedManager.FeedListener {
        override fun onReceiveUpdate(list: MutableList<FeedManager.Feed>) {
            feedStateFlow.update {
                list.map { it.copy() }
            }
        }
    }

    init {
        FeedManager.setFeedListener(listener)

        val feed = FeedManager.getFeed()
        if (feed.isNotEmpty()) feedStateFlow.value = feed
    }
}