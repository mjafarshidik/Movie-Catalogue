package com.mjafarshidik.moviecatalogue.ui.home

import android.view.View
import org.hamcrest.Matcher

object EspressoTestsMatchers {
    fun hasDrawable(): Matcher<View> {
        return DrawableMatcher(DrawableMatcher.ANY)
    }
}