package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.data.model.Booking
import com.example.data.model.ServiceCatalog
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object CommunicationHelper {

    fun callBalaJi(context: Context, phoneNumber: String = ServiceCatalog.PHONE_NUMBER) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open dialer: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(
        context: Context,
        phoneNumber: String = ServiceCatalog.PHONE_NUMBER_INTL,
        message: String = "Hello BalaJi DJ, I want to inquire about event booking."
    ) {
        try {
            val encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
            val cleanPhone = phoneNumber.replace("+", "").replace(" ", "").replace("-", "")
            val uri = Uri.parse("https://wa.me/$cleanPhone?text=$encodedMessage")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open WhatsApp: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openYouTube(
        context: Context,
        url: String = ServiceCatalog.YOUTUBE_URL
    ) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open YouTube: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareBookingDetails(context: Context, booking: Booking) {
        try {
            val text = """
                🎉 *BALAJI DJ EVENT BOOKING SLIP* 🎉
                ━━━━━━━━━━━━━━━━━━━━━
                👤 *Client:* ${booking.clientName}
                📞 *Phone:* ${booking.clientPhone}
                📅 *Date:* ${booking.eventDate} (${booking.timeSlot})
                🎊 *Event Type:* ${booking.eventType}
                📍 *Venue:* ${booking.venueAddress}
                
                🎵 *Booked Services:*
                ${booking.selectedServices}
                
                💰 *Total Cost:* ₹${booking.estimatedCost.toInt()}
                💵 *Advance Paid:* ₹${booking.advancePaid.toInt()}
                💳 *Balance Due:* ₹${booking.balanceAmount.toInt()}
                
                🚦 *Status:* ${booking.status}
                🚚 *Delivery Readiness:* ${if (booking.isReadyForDelivery) "READY FOR TIMELY DELIVERY" else "IN PREPARATION"}
                
                ━━━━━━━━━━━━━━━━━━━━━
                *BalaJi DJ & Sound Services*
                📞 Contact: ${ServiceCatalog.PHONE_NUMBER}
                Dancer DJ | Dhamal DJ | Decorated Rath | Orchestra Trolley | Live Jagran Setup
            """.trimIndent()

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "BalaJi DJ Booking - ${booking.clientName}")
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(intent, "Share Booking Slip"))
        } catch (e: Exception) {
            Toast.makeText(context, "Sharing failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareText(context: Context, subject: String, text: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(intent, subject))
        } catch (e: Exception) {
            Toast.makeText(context, "Sharing failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
