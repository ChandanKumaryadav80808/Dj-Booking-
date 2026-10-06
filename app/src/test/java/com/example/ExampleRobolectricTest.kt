package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.ServiceCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("BalaJi DJ", appName)
    }

    @Test
    fun `verify service catalog offerings and contact number`() {
        assertEquals("9693870195", ServiceCatalog.PHONE_NUMBER)
        assertEquals("https://youtube.com/@balajidjrathsoundtaroli?si=debBerP0LRjsIeFw", ServiceCatalog.YOUTUBE_URL)
        assertEquals("@balajidjrathsoundtaroli", ServiceCatalog.YOUTUBE_HANDLE)
        assertEquals("Taroli", ServiceCatalog.LOCATION)

        val packageTitles = ServiceCatalog.PACKAGES.map { it.title }
        assertTrue(packageTitles.contains("Dancer DJ"))
        assertTrue(packageTitles.contains("Dhamal DJ"))
        assertTrue(packageTitles.contains("Decorated Rath"))
        assertTrue(packageTitles.contains("Orchestra Trolley"))
        assertTrue(packageTitles.contains("Live Jagran Setup"))
    }

    @Test
    fun `verify equipment gallery items and event suitability`() {
        val galleryItems = com.example.data.model.GalleryCatalog.ITEMS
        val itemIds = galleryItems.map { it.id }

        assertTrue("Decorated Rath should be in gallery", itemIds.contains("decorated_rath"))
        assertTrue("Orchestra Trolley should be in gallery", itemIds.contains("orchestra_trolley"))
        assertTrue("Live Jagran Setup should be in gallery", itemIds.contains("live_jagran_setup"))

        val rath = galleryItems.first { it.id == "decorated_rath" }
        assertTrue("Rath must have wedding event suitability", rath.eventSuitability.any { it.eventName.contains("Wedding") })
        assertTrue("Rath must have technical specs", rath.technicalSpecs.isNotEmpty())

        val trolley = galleryItems.first { it.id == "orchestra_trolley" }
        assertTrue("Trolley must have cultural/yatra event suitability", trolley.eventSuitability.any { it.eventName.contains("Cultural") || it.eventName.contains("Yatra") })
        assertTrue("Trolley must have key features", trolley.keyFeatures.isNotEmpty())

        val jagran = galleryItems.first { it.id == "live_jagran_setup" }
        assertTrue("Jagran must have Chowki/Jagran suitability", jagran.eventSuitability.any { it.eventName.contains("Jagran") || it.eventName.contains("Chowki") })
        assertTrue("Jagran must have technical specs", jagran.technicalSpecs.isNotEmpty())
    }

    @Test
    fun `verify video catalog event and tutorial videos`() {
        val videos = com.example.data.model.VideoCatalog.VIDEOS
        assertTrue("Videos should not be empty", videos.isNotEmpty())

        val eventVideos = videos.filter { !it.isTutorial }
        val tutorialVideos = videos.filter { it.isTutorial }

        assertTrue("Should have live event videos", eventVideos.isNotEmpty())
        assertTrue("Should have tutorial setup videos", tutorialVideos.isNotEmpty())

        videos.forEach { video ->
            assertTrue("Video should link to official YouTube", video.videoUrl.contains("youtube.com"))
            assertTrue("Video should have duration", video.duration.isNotBlank())
        }
    }

    @Test
    fun `verify offline side booking token and balance calculation`() {
        val offlineBooking = com.example.data.model.OfflineBooking(
            id = 1,
            tokenNumber = "BJ-OFF-101",
            clientName = "Ramesh Kumar",
            clientPhone = "9876543210",
            eventType = "Wedding (Baraat)",
            eventDate = "25 Oct 2026",
            timeSlot = "Evening",
            spotLocation = "Taroli Gate",
            setupSelected = "Decorated Rath + Dhamal DJ",
            totalAgreedAmount = 35000.0,
            cashAdvanceTaken = 10000.0,
            paymentMode = "Cash"
        )

        assertEquals(25000.0, offlineBooking.balanceAmount, 0.01)
        assertEquals("BJ-OFF-101", offlineBooking.tokenNumber)
        assertEquals("Cash", offlineBooking.paymentMode)
    }
}
