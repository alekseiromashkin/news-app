package com.arammeem.android.apps.newsapp.feature.sources.presentation

import androidx.fragment.app.FragmentManager

internal sealed class SourcesEvents
internal class OpenArticlesScreen(
    val fragmentManager: FragmentManager,
    val source: String,
    val title: String,
) : SourcesEvents()
