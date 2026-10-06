package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clientName: String,
    val clientPhone: String,
    val altPhone: String = "",
    val eventType: String,
    val eventDate: String,
    val timeSlot: String,
    val venueAddress: String,
    val selectedServices: String, // Comma-separated names
    val estimatedCost: Double,
    val advancePaid: Double,
    val status: String, // "PENDING", "CONFIRMED", "READY_FOR_DELIVERY", "IN_TRANSIT", "COMPLETED", "CANCELLED"
    val checklistProgress: String = "", // e.g., "sound=true,genset=true,rath=false,driver=true,dispatch=false"
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
) {
    val balanceAmount: Double
        get() = (estimatedCost - advancePaid).coerceAtLeast(0.0)

    val isReadyForDelivery: Boolean
        get() = status == "READY_FOR_DELIVERY" || status == "IN_TRANSIT" || status == "COMPLETED"
}

@Entity(tableName = "equipment")
data class EquipmentItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: String, // "Sound & Bass", "Lighting & Truss", "Decorated Rath", "Orchestra Trolley", "Live Jagran Setup", "Power & Generator", "SFX"
    val serialCode: String,
    val conditionStatus: String, // "READY", "MAINTENANCE_DUE", "UNDER_SERVICE"
    val healthPercentage: Int = 95,
    val lastServiceDate: String,
    val nextServiceDue: String,
    val currentAssignment: String = "In Warehouse - Ready",
    val maintenanceNotes: String = "Inspected and ready for dispatch.",
    val isVerifiedForTimelyDelivery: Boolean = true
)

data class ServicePackage(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val startingPrice: Double,
    val equipmentIncluded: List<String>,
    val highlights: List<String>,
    val bannerDrawableRes: Int
)

data class DeliveryChecklistItem(
    val key: String,
    val title: String,
    val description: String,
    val isChecked: Boolean
)

@Entity(tableName = "offline_bookings")
data class OfflineBooking(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tokenNumber: String,
    val clientName: String,
    val clientPhone: String,
    val eventType: String,
    val eventDate: String,
    val timeSlot: String,
    val spotLocation: String,
    val setupSelected: String,
    val totalAgreedAmount: Double,
    val cashAdvanceTaken: Double,
    val paymentMode: String = "Cash", // "Cash", "UPI", "Bank"
    val bookedByStaff: String = "Counter / Spot (Taroli)",
    val deliveryPromiseTime: String = "2 Hours Before Event",
    val status: String = "CONFIRMED", // "CONFIRMED", "READY_FOR_DELIVERY", "COMPLETED", "CANCELLED"
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
) {
    val balanceAmount: Double
        get() = (totalAgreedAmount - cashAdvanceTaken).coerceAtLeast(0.0)
}

data class EventVideo(
    val id: String,
    val title: String,
    val hindiTitle: String,
    val category: String, // "Event Live Videos", "Setup Tutorials"
    val videoUrl: String,
    val thumbnailRes: Int,
    val duration: String,
    val viewsCount: String,
    val description: String,
    val isTutorial: Boolean = false
)
