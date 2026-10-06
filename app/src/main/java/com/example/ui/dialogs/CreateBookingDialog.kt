package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ServiceCatalog
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NeonOrange

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateBookingDialog(
    initialSelectedService: String? = null,
    onDismiss: () -> Unit,
    onConfirm: (
        name: String,
        phone: String,
        altPhone: String,
        eventType: String,
        date: String,
        timeSlot: String,
        venue: String,
        services: List<String>,
        cost: Double,
        advance: Double,
        notes: String
    ) -> Unit
) {
    var clientName by remember { mutableStateOf("") }
    var clientPhone by remember { mutableStateOf("") }
    var altPhone by remember { mutableStateOf("") }
    var selectedEventType by remember { mutableStateOf(ServiceCatalog.EVENT_TYPES.first()) }
    var eventTypeExpanded by remember { mutableStateOf(false) }

    var eventDate by remember { mutableStateOf("15 Oct 2026") }
    var selectedTimeSlot by remember { mutableStateOf(ServiceCatalog.TIME_SLOTS.first()) }
    var timeSlotExpanded by remember { mutableStateOf(false) }

    var venueAddress by remember { mutableStateOf("") }

    val selectedServices = remember {
        mutableStateListOf<String>().apply {
            if (!initialSelectedService.isNullOrBlank()) {
                add(initialSelectedService)
            } else {
                add("Dancer DJ")
            }
        }
    }

    // Auto-calculate suggested price based on services
    val calculatedPrice = remember(selectedServices.toList()) {
        selectedServices.sumOf { sName ->
            ServiceCatalog.PACKAGES.find { it.title == sName }?.startingPrice ?: 15000.0
        }
    }

    var totalCostText by remember(calculatedPrice) {
        mutableStateOf(calculatedPrice.toInt().toString())
    }
    var advancePaidText by remember { mutableStateOf("5000") }
    var notes by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "New Event Booking",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "BalaJi DJ • Timely Delivery Assured",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_booking_dialog")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Service Packages Selection
                Text(
                    text = "Select Service(s) *",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ServiceCatalog.PACKAGES.forEach { pkg ->
                        val isSelected = selectedServices.contains(pkg.title)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) {
                                    if (selectedServices.size > 1) selectedServices.remove(pkg.title)
                                } else {
                                    selectedServices.add(pkg.title)
                                }
                            },
                            label = { Text(pkg.title) },
                            leadingIcon = if (isSelected) {
                                {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            } else null,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = GoldPrimary,
                                selectedLabelColor = Color.Black,
                                selectedLeadingIconColor = Color.Black
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Client Details
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Client Full Name *") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_client_name"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = clientPhone,
                        onValueChange = { clientPhone = it },
                        label = { Text("Phone Number *") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_client_phone"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = altPhone,
                        onValueChange = { altPhone = it },
                        label = { Text("Alt. Phone") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_alt_phone"),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Event Type Dropdown
                ExposedDropdownMenuBox(
                    expanded = eventTypeExpanded,
                    onExpandedChange = { eventTypeExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedEventType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Event Type *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = eventTypeExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = eventTypeExpanded,
                        onDismissRequest = { eventTypeExpanded = false }
                    ) {
                        ServiceCatalog.EVENT_TYPES.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedEventType = type
                                    eventTypeExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Date & Time Slot
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = eventDate,
                        onValueChange = { eventDate = it },
                        label = { Text("Event Date *") },
                        leadingIcon = {
                            Icon(Icons.Default.CalendarToday, contentDescription = null)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_event_date"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    ExposedDropdownMenuBox(
                        expanded = timeSlotExpanded,
                        onExpandedChange = { timeSlotExpanded = it },
                        modifier = Modifier.weight(1.2f)
                    ) {
                        OutlinedTextField(
                            value = selectedTimeSlot,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Time Slot") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = timeSlotExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = timeSlotExpanded,
                            onDismissRequest = { timeSlotExpanded = false }
                        ) {
                            ServiceCatalog.TIME_SLOTS.forEach { slot ->
                                DropdownMenuItem(
                                    text = { Text(slot) },
                                    onClick = {
                                        selectedTimeSlot = slot
                                        timeSlotExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Venue Address
                OutlinedTextField(
                    value = venueAddress,
                    onValueChange = { venueAddress = it },
                    label = { Text("Venue Address & Delivery Location *") },
                    leadingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_venue_address"),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Price and Advance
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = totalCostText,
                        onValueChange = { totalCostText = it },
                        label = { Text("Total Quote (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_total_cost"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = advancePaidText,
                        onValueChange = { advancePaidText = it },
                        label = { Text("Advance Paid (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_advance_paid"),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Special Notes / Requirements
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Special Requests / Delivery Timing Notes") },
                    placeholder = { Text("e.g. Need generator fuel backup, Rath entry at 8:30 PM") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_notes"),
                    maxLines = 2
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.outlinedButtonColors()
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            if (clientName.isBlank()) {
                                errorMessage = "Please enter client name"
                                return@Button
                            }
                            if (clientPhone.isBlank()) {
                                errorMessage = "Please enter client phone number"
                                return@Button
                            }
                            if (venueAddress.isBlank()) {
                                errorMessage = "Please enter venue delivery address"
                                return@Button
                            }
                            val cost = totalCostText.toDoubleOrNull() ?: calculatedPrice
                            val advance = advancePaidText.toDoubleOrNull() ?: 0.0

                            onConfirm(
                                clientName,
                                clientPhone,
                                altPhone,
                                selectedEventType,
                                eventDate,
                                selectedTimeSlot,
                                venueAddress,
                                selectedServices.toList(),
                                cost,
                                advance,
                                notes
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.testTag("confirm_booking_button")
                    ) {
                        Text(
                            text = "Confirm Booking",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
