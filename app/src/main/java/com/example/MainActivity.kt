package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Booking
import com.example.data.model.EquipmentItem
import com.example.data.model.EventVideo
import com.example.data.model.GalleryItem
import com.example.data.model.ServiceCatalog
import com.example.data.model.ServicePackage
import com.example.ui.components.BalaJiTopBar
import com.example.ui.components.CommunicationHelper
import com.example.ui.dialogs.AddEquipmentDialog
import com.example.ui.dialogs.BookingDetailDialog
import com.example.ui.dialogs.CreateBookingDialog
import com.example.ui.dialogs.GalleryItemDetailDialog
import com.example.ui.dialogs.LogMaintenanceDialog
import com.example.ui.dialogs.OfflineSideBookingDialog
import com.example.ui.dialogs.PackageDetailDialog
import com.example.ui.dialogs.VideoWatchDialog
import com.example.ui.screens.AboutContactScreen
import com.example.ui.screens.BookingsScreen
import com.example.ui.screens.EquipmentScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.BalaJiDjTheme
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.BalaJiViewModel

import com.example.ui.screens.EquipmentGalleryScreen
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.outlined.Collections

enum class AppNavTab(val title: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home),
    GALLERY("Gallery", Icons.Filled.Collections, Icons.Outlined.Collections),
    BOOKINGS("Bookings", Icons.Filled.Event, Icons.Outlined.Event),
    EQUIPMENT("Fleet Health", Icons.Filled.Build, Icons.Outlined.Build),
    CONTACT("Contact", Icons.Filled.Call, Icons.Outlined.Call)
}

class MainActivity : ComponentActivity() {

    private val viewModel: BalaJiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BalaJiDjTheme {
                BalaJiDjApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun BalaJiDjApp(viewModel: BalaJiViewModel) {
    val context = LocalContext.current
    var currentTab by remember { mutableStateOf(AppNavTab.HOME) }

    // Dialog States
    var showCreateBookingDialog by remember { mutableStateOf(false) }
    var preselectedServiceForBooking by remember { mutableStateOf<String?>(null) }
    var viewingBooking by remember { mutableStateOf<Booking?>(null) }
    var viewingPackage by remember { mutableStateOf<ServicePackage?>(null) }
    var viewingGalleryItem by remember { mutableStateOf<GalleryItem?>(null) }
    var servicingEquipment by remember { mutableStateOf<EquipmentItem?>(null) }
    var showAddEquipmentDialog by remember { mutableStateOf(false) }
    var showOfflineBookingDialog by remember { mutableStateOf(false) }
    var viewingVideo by remember { mutableStateOf<EventVideo?>(null) }

    // ViewModel State collection
    val allBookings by viewModel.allBookings.collectAsStateWithLifecycle()
    val filteredBookings by viewModel.filteredBookings.collectAsStateWithLifecycle()
    val filteredEquipment by viewModel.filteredEquipment.collectAsStateWithLifecycle()
    val allOfflineBookings by viewModel.allOfflineBookings.collectAsStateWithLifecycle()
    val offlineBookingCount by viewModel.offlineBookingCount.collectAsStateWithLifecycle()
    val fleetReadiness by viewModel.fleetReadinessPercentage.collectAsStateWithLifecycle()

    val bookingFilter by viewModel.bookingFilter.collectAsStateWithLifecycle()
    val bookingSearchQuery by viewModel.bookingSearchQuery.collectAsStateWithLifecycle()

    val equipmentCategoryFilter by viewModel.equipmentCategoryFilter.collectAsStateWithLifecycle()
    val equipmentStatusFilter by viewModel.equipmentStatusFilter.collectAsStateWithLifecycle()

    // Handle back button
    BackHandler(enabled = currentTab != AppNavTab.HOME) {
        currentTab = AppNavTab.HOME
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BalaJiTopBar(
                readinessPercentage = fleetReadiness,
                offlineBookingCount = offlineBookingCount,
                onOfflineBookingClick = { showOfflineBookingDialog = true },
                onCallClick = { CommunicationHelper.callBalaJi(context) },
                onWhatsAppClick = { CommunicationHelper.openWhatsApp(context) },
                onYouTubeClick = { CommunicationHelper.openYouTube(context) }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_nav_bar")
            ) {
                AppNavTab.entries.forEach { tab ->
                    val isSelected = currentTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title
                            )
                        },
                        label = { Text(tab.title, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = GoldPrimary.copy(alpha = 0.25f),
                            selectedIconColor = GoldPrimary,
                            selectedTextColor = GoldPrimary
                        ),
                        modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                    )
                }
            }
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (currentTab == AppNavTab.BOOKINGS) {
                    FloatingActionButton(
                        onClick = {
                            preselectedServiceForBooking = null
                            showCreateBookingDialog = true
                        },
                        containerColor = GoldPrimary.copy(alpha = 0.3f),
                        contentColor = GoldPrimary,
                        modifier = Modifier.testTag("fab_new_booking")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "New Booking")
                    }
                } else if (currentTab == AppNavTab.EQUIPMENT) {
                    FloatingActionButton(
                        onClick = { showAddEquipmentDialog = true },
                        containerColor = GoldPrimary.copy(alpha = 0.3f),
                        contentColor = GoldPrimary,
                        modifier = Modifier.testTag("fab_add_equipment")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Equipment")
                    }
                }

                // Floating Action Button that triggers intent to dial 9693870195 directly
                ExtendedFloatingActionButton(
                    onClick = {
                        CommunicationHelper.callBalaJi(context, ServiceCatalog.PHONE_NUMBER)
                    },
                    containerColor = GoldPrimary,
                    contentColor = Color.Black,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Dial 9693870195 to book services",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    text = {
                        Text(
                            text = "Call 9693870195",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier.testTag("fab_quick_dial_9693870195")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppNavTab.HOME -> {
                    HomeScreen(
                        fleetReadiness = fleetReadiness,
                        upcomingBookings = allBookings.filter { it.status in listOf("PENDING", "CONFIRMED", "READY_FOR_DELIVERY") },
                        offlineBookingCount = offlineBookingCount,
                        onOpenBookingDialog = { preselected ->
                            preselectedServiceForBooking = preselected
                            showCreateBookingDialog = true
                        },
                        onViewPackageDetail = { pkg ->
                            viewingPackage = pkg
                        },
                        onNavigateToBookings = { currentTab = AppNavTab.BOOKINGS },
                        onNavigateToEquipment = { currentTab = AppNavTab.EQUIPMENT },
                        onNavigateToGallery = { currentTab = AppNavTab.GALLERY },
                        onOpenOfflineBooking = { showOfflineBookingDialog = true },
                        onSelectVideo = { video -> viewingVideo = video }
                    )
                }
                AppNavTab.GALLERY -> {
                    EquipmentGalleryScreen(
                        onSelectItem = { item -> viewingGalleryItem = item },
                        onBookItem = { serviceTitle ->
                            preselectedServiceForBooking = serviceTitle
                            showCreateBookingDialog = true
                        },
                        onSelectVideo = { video -> viewingVideo = video }
                    )
                }
                AppNavTab.BOOKINGS -> {
                    BookingsScreen(
                        bookings = filteredBookings,
                        currentFilter = bookingFilter,
                        searchQuery = bookingSearchQuery,
                        onFilterChange = { viewModel.bookingFilter.value = it },
                        onSearchChange = { viewModel.bookingSearchQuery.value = it },
                        onBookingClick = { booking -> viewingBooking = booking },
                        onCreateBookingClick = {
                            preselectedServiceForBooking = null
                            showCreateBookingDialog = true
                        }
                    )
                }
                AppNavTab.EQUIPMENT -> {
                    EquipmentScreen(
                        equipmentList = filteredEquipment,
                        readinessPercentage = fleetReadiness,
                        selectedCategory = equipmentCategoryFilter,
                        selectedStatus = equipmentStatusFilter,
                        onCategoryChange = { viewModel.equipmentCategoryFilter.value = it },
                        onStatusChange = { viewModel.equipmentStatusFilter.value = it },
                        onQuickVerifyReady = { item ->
                            viewModel.updateEquipmentStatus(item, "READY", "Verified ready for immediate dispatch.", 100)
                        },
                        onLogMaintenanceClick = { item -> servicingEquipment = item },
                        onAddEquipmentClick = { showAddEquipmentDialog = true }
                    )
                }
                AppNavTab.CONTACT -> {
                    AboutContactScreen()
                }
            }
        }
    }

    // Dialogs
    if (showCreateBookingDialog) {
        CreateBookingDialog(
            initialSelectedService = preselectedServiceForBooking,
            onDismiss = {
                showCreateBookingDialog = false
                preselectedServiceForBooking = null
            },
            onConfirm = { name, phone, altPhone, eventType, date, timeSlot, venue, services, cost, advance, notes ->
                viewModel.createBooking(
                    clientName = name,
                    clientPhone = phone,
                    altPhone = altPhone,
                    eventType = eventType,
                    eventDate = date,
                    timeSlot = timeSlot,
                    venueAddress = venue,
                    selectedServices = services,
                    estimatedCost = cost,
                    advancePaid = advance,
                    notes = notes
                )
                showCreateBookingDialog = false
                preselectedServiceForBooking = null
                currentTab = AppNavTab.BOOKINGS
            }
        )
    }

    viewingBooking?.let { booking ->
        val checklistMap = viewModel.parseChecklist(booking.checklistProgress)
        BookingDetailDialog(
            booking = booking,
            checklistMap = checklistMap,
            onDismiss = { viewingBooking = null },
            onToggleChecklist = { checkKey ->
                viewModel.toggleChecklistItem(booking, checkKey)
                // Refresh local viewing reference with updated state if needed
                viewingBooking = allBookings.find { it.id == booking.id } ?: booking
            },
            onUpdateStatus = { newStatus ->
                viewModel.updateBookingStatus(booking, newStatus)
                viewingBooking = viewingBooking?.copy(status = newStatus)
            },
            onDelete = {
                viewModel.deleteBooking(booking)
                viewingBooking = null
            }
        )
    }

    viewingPackage?.let { pkg ->
        PackageDetailDialog(
            pkg = pkg,
            onDismiss = { viewingPackage = null },
            onBookNow = { serviceTitle ->
                preselectedServiceForBooking = serviceTitle
                showCreateBookingDialog = true
            }
        )
    }

    viewingGalleryItem?.let { item ->
        GalleryItemDetailDialog(
            item = item,
            onDismiss = { viewingGalleryItem = null },
            onBookNow = { serviceTitle ->
                preselectedServiceForBooking = serviceTitle
                showCreateBookingDialog = true
            }
        )
    }

    servicingEquipment?.let { item ->
        LogMaintenanceDialog(
            item = item,
            onDismiss = { servicingEquipment = null },
            onSave = { newStatus, notes, healthScore ->
                viewModel.updateEquipmentStatus(item, newStatus, notes, healthScore)
                servicingEquipment = null
            }
        )
    }

    if (showAddEquipmentDialog) {
        AddEquipmentDialog(
            onDismiss = { showAddEquipmentDialog = false },
            onAdd = { name, category, serialCode, notes ->
                viewModel.addEquipment(name, category, serialCode, notes)
            }
        )
    }

    if (showOfflineBookingDialog) {
        OfflineSideBookingDialog(
            offlineBookings = allOfflineBookings,
            onDismiss = { showOfflineBookingDialog = false },
            onCreateOfflineBooking = { name, phone, eventType, date, timeSlot, loc, setup, tot, adv, mode, notes ->
                viewModel.createOfflineBooking(name, phone, eventType, date, timeSlot, loc, setup, tot, adv, mode, notes)
            },
            onUpdateStatus = { booking, newStatus ->
                viewModel.updateOfflineBooking(booking.copy(status = newStatus))
            },
            onDelete = { booking ->
                viewModel.deleteOfflineBooking(booking)
            }
        )
    }

    viewingVideo?.let { video ->
        VideoWatchDialog(
            video = video,
            onDismiss = { viewingVideo = null },
            onBookSetup = { serviceTitle ->
                viewingVideo = null
                preselectedServiceForBooking = serviceTitle
                showCreateBookingDialog = true
            }
        )
    }
}
