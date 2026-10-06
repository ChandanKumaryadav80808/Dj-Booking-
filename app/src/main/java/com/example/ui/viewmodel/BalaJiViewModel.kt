package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AppDatabase
import com.example.data.model.Booking
import com.example.data.model.EquipmentItem
import com.example.data.model.OfflineBooking
import com.example.data.model.ServiceCatalog
import com.example.data.repository.BalaJiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BalaJiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BalaJiRepository
    val allBookings: StateFlow<List<Booking>>
    val allEquipment: StateFlow<List<EquipmentItem>>
    val allOfflineBookings: StateFlow<List<OfflineBooking>>
    val offlineBookingCount: StateFlow<Int>

    val bookingFilter = MutableStateFlow("ALL")
    val bookingSearchQuery = MutableStateFlow("")

    val equipmentCategoryFilter = MutableStateFlow("ALL")
    val equipmentStatusFilter = MutableStateFlow("ALL")

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = BalaJiRepository(database.bookingDao(), database.equipmentDao(), database.offlineBookingDao())

        allBookings = repository.allBookings.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        allEquipment = repository.allEquipment.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        allOfflineBookings = repository.allOfflineBookings.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        offlineBookingCount = repository.offlineBookingCount.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    }

    val filteredBookings: StateFlow<List<Booking>> = combine(
        allBookings,
        bookingFilter,
        bookingSearchQuery
    ) { bookings, filter, query ->
        bookings.filter { booking ->
            val matchesFilter = when (filter) {
                "ALL" -> true
                "UPCOMING" -> booking.status in listOf("PENDING", "CONFIRMED", "READY_FOR_DELIVERY")
                "READY" -> booking.status == "READY_FOR_DELIVERY"
                "IN_TRANSIT" -> booking.status == "IN_TRANSIT"
                "COMPLETED" -> booking.status == "COMPLETED"
                else -> true
            }

            val matchesQuery = query.isBlank() ||
                    booking.clientName.contains(query, ignoreCase = true) ||
                    booking.clientPhone.contains(query, ignoreCase = true) ||
                    booking.eventType.contains(query, ignoreCase = true) ||
                    booking.selectedServices.contains(query, ignoreCase = true) ||
                    booking.venueAddress.contains(query, ignoreCase = true)

            matchesFilter && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val filteredEquipment: StateFlow<List<EquipmentItem>> = combine(
        allEquipment,
        equipmentCategoryFilter,
        equipmentStatusFilter
    ) { equipmentList, category, status ->
        equipmentList.filter { item ->
            val matchesCategory = category == "ALL" || item.category == category
            val matchesStatus = status == "ALL" || item.conditionStatus == status
            matchesCategory && matchesStatus
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val fleetReadinessPercentage: StateFlow<Int> = allEquipment.combine(allBookings) { equipmentList, _ ->
        if (equipmentList.isEmpty()) 100
        else {
            val readyCount = equipmentList.count { it.conditionStatus == "READY" }
            ((readyCount.toDouble() / equipmentList.size) * 100).toInt()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 100
    )

    fun createBooking(
        clientName: String,
        clientPhone: String,
        altPhone: String,
        eventType: String,
        eventDate: String,
        timeSlot: String,
        venueAddress: String,
        selectedServices: List<String>,
        estimatedCost: Double,
        advancePaid: Double,
        notes: String
    ) {
        viewModelScope.launch {
            val initialChecklist = ServiceCatalog.STANDARD_CHECKLIST_KEYS.joinToString(",") {
                "${it.first}=false"
            }
            val newBooking = Booking(
                clientName = clientName.trim(),
                clientPhone = clientPhone.trim(),
                altPhone = altPhone.trim(),
                eventType = eventType,
                eventDate = eventDate,
                timeSlot = timeSlot,
                venueAddress = venueAddress.trim(),
                selectedServices = selectedServices.joinToString(", "),
                estimatedCost = estimatedCost,
                advancePaid = advancePaid,
                status = "CONFIRMED",
                checklistProgress = initialChecklist,
                notes = notes.trim()
            )
            repository.insertBooking(newBooking)
        }
    }

    fun updateBookingStatus(booking: Booking, newStatus: String) {
        viewModelScope.launch {
            repository.updateBooking(booking.copy(status = newStatus))
        }
    }

    fun toggleChecklistItem(booking: Booking, checkKey: String) {
        viewModelScope.launch {
            val currentMap = parseChecklist(booking.checklistProgress).toMutableMap()
            val currentVal = currentMap[checkKey] ?: false
            currentMap[checkKey] = !currentVal

            val newString = currentMap.entries.joinToString(",") { "${it.key}=${it.value}" }
            val allChecked = ServiceCatalog.STANDARD_CHECKLIST_KEYS.all { currentMap[it.first] == true }
            val newStatus = if (allChecked && booking.status == "CONFIRMED") "READY_FOR_DELIVERY" else booking.status

            repository.updateBooking(
                booking.copy(
                    checklistProgress = newString,
                    status = newStatus
                )
            )
        }
    }

    fun deleteBooking(booking: Booking) {
        viewModelScope.launch {
            repository.deleteBooking(booking)
        }
    }

    fun updateEquipmentStatus(
        item: EquipmentItem,
        newStatus: String,
        notes: String,
        healthScore: Int
    ) {
        viewModelScope.launch {
            val today = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
            repository.updateEquipment(
                item.copy(
                    conditionStatus = newStatus,
                    maintenanceNotes = notes,
                    healthPercentage = healthScore,
                    lastServiceDate = today,
                    isVerifiedForTimelyDelivery = newStatus == "READY"
                )
            )
        }
    }

    fun addEquipment(
        name: String,
        category: String,
        serialCode: String,
        notes: String
    ) {
        viewModelScope.launch {
            val today = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
            val newItem = EquipmentItem(
                name = name.trim(),
                category = category,
                serialCode = serialCode.trim().ifEmpty { "BJ-${System.currentTimeMillis() % 1000}" },
                conditionStatus = "READY",
                healthPercentage = 100,
                lastServiceDate = today,
                nextServiceDue = "Next Month",
                maintenanceNotes = notes.trim().ifEmpty { "New gear added, fully calibrated & ready for delivery." },
                isVerifiedForTimelyDelivery = true
            )
            repository.insertEquipment(newItem)
        }
    }

    fun deleteEquipment(item: EquipmentItem) {
        viewModelScope.launch {
            repository.deleteEquipment(item)
        }
    }

    fun createOfflineBooking(
        clientName: String,
        clientPhone: String,
        eventType: String,
        eventDate: String,
        timeSlot: String,
        spotLocation: String,
        setupSelected: String,
        totalAmount: Double,
        advanceAmount: Double,
        paymentMode: String,
        notes: String
    ) {
        viewModelScope.launch {
            val count = (allOfflineBookings.value.size + 1)
            val token = "TRL-OFF-" + String.format(Locale.getDefault(), "%02d", count)
            val newOffline = OfflineBooking(
                tokenNumber = token,
                clientName = clientName.trim(),
                clientPhone = clientPhone.trim(),
                eventType = eventType,
                eventDate = eventDate,
                timeSlot = timeSlot,
                spotLocation = spotLocation.trim().ifEmpty { "Taroli Local Area" },
                setupSelected = setupSelected,
                totalAgreedAmount = totalAmount,
                cashAdvanceTaken = advanceAmount,
                paymentMode = paymentMode,
                bookedByStaff = "Offline / Counter (Taroli)",
                deliveryPromiseTime = "2 Hours Before Event",
                status = "CONFIRMED",
                notes = notes.trim()
            )
            repository.insertOfflineBooking(newOffline)
        }
    }

    fun updateOfflineBooking(booking: OfflineBooking) {
        viewModelScope.launch {
            repository.updateOfflineBooking(booking)
        }
    }

    fun deleteOfflineBooking(booking: OfflineBooking) {
        viewModelScope.launch {
            repository.deleteOfflineBooking(booking)
        }
    }

    fun parseChecklist(checklistString: String): Map<String, Boolean> {
        if (checklistString.isBlank()) {
            return ServiceCatalog.STANDARD_CHECKLIST_KEYS.associate { it.first to false }
        }
        val map = mutableMapOf<String, Boolean>()
        checklistString.split(",").forEach { pair ->
            val parts = pair.split("=")
            if (parts.size == 2) {
                map[parts[0].trim()] = parts[1].trim().toBoolean()
            }
        }
        // Ensure all standard keys exist
        ServiceCatalog.STANDARD_CHECKLIST_KEYS.forEach {
            if (!map.containsKey(it.first)) {
                map[it.first] = false
            }
        }
        return map
    }
}
