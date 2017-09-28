package com.example.astux7.weatheralert.data
import android.os.Build.VERSION_CODES.LOLLIPOP
import com.example.astux7.weatheralert.BuildConfig
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricGradleTestRunner::class)

@Config(constants = BuildConfig::class, sdk = intArrayOf(LOLLIPOP), packageName = "com.example.astux7.weatheralert.data")
class LocationDatabaseHandlerTest {
    lateinit var dbHelper: LocationDatabaseHandler
    @Before
    fun setUp() {
        dbHelper = LocationDatabaseHandler(RuntimeEnvironment.application)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    fun createLocation() {

    }

}