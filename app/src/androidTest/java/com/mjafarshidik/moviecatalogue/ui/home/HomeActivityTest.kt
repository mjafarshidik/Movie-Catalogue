package com.mjafarshidik.moviecatalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mjafarshidik.moviecatalogue.R
import com.mjafarshidik.moviecatalogue.ui.home.EspressoTestsMatchers.hasDrawable
import com.mjafarshidik.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun addToFavoriteMovies(){
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.addFavorite)).perform(ViewActions.click())
    }

    @Test
    fun addToFavoriteTVShow(){
        onView(withText("TV Show")).perform(ViewActions.click())
        onView(withId(R.id.rvTVShows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.addFavorite)).perform(ViewActions.click())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rvMovies)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.imgDetail)).check(matches(hasDrawable()))
        onView(withId(R.id.cToolBar)).check(matches(isDisplayed()))
        onView(withId(R.id.tvScore)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDateRelease)).check(matches(isDisplayed()))
        onView(withId(R.id.tvOverview)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTVShow() {
        onView(withText("TV Show")).perform(ViewActions.click())
        onView(withId(R.id.rvTVShows)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailTVShows() {
        onView(withText("TV Show")).perform(ViewActions.click())
        onView(withId(R.id.rvTVShows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.imgDetail)).check(matches(hasDrawable()))
        onView(withId(R.id.cToolBar)).check(matches(isDisplayed()))
        onView(withId(R.id.tvScore)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDateRelease)).check(matches(isDisplayed()))
        onView(withId(R.id.tvOverview)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavorite(){
        onView(withId(R.id.btnFavorite)).perform(ViewActions.click())
        onView(withId(R.id.rvMoviesFavorite)).check(matches(isDisplayed()))
        onView(withText("TV Show")).perform(ViewActions.click())
        onView(withId(R.id.rvTVShowsFavorite)).check(matches(isDisplayed()))
    }

    @Test
    fun removeMoviesFavorite(){
        onView(withId(R.id.btnFavorite)).perform(ViewActions.click())
        onView(withId(R.id.rvMoviesFavorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMoviesFavorite)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.addFavorite)).perform(ViewActions.click())
    }

    @Test
    fun removeTVShowsFavorite(){
        onView(withId(R.id.btnFavorite)).perform(ViewActions.click())
        onView(withText("TV Show")).perform(ViewActions.click())
        onView(withId(R.id.rvTVShowsFavorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTVShowsFavorite)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        onView(withId(R.id.addFavorite)).perform(ViewActions.click())
    }
}