package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.Booking
import com.example.data.model.EquipmentItem
import com.example.data.model.OfflineBooking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Booking::class, EquipmentItem::class, OfflineBooking::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun offlineBookingDao(): OfflineBookingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "balaji_dj_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        prepopulateData(database.equipmentDao(), database.bookingDao(), database.offlineBookingDao())
                    }
                }
            }
        }

        private suspend fun prepopulateData(equipmentDao: EquipmentDao, bookingDao: BookingDao, offlineDao: OfflineBookingDao) {
            val initialEquipment = listOf(
                EquipmentItem(
                    name = "JBL SRX Dual 18\" Subwoofers (Set of 6)",
                    category = "Sound & Bass",
                    serialCode = "BJ-SUB-801",
                    conditionStatus = "READY",
                    healthPercentage = 98,
                    lastServiceDate = "02 Oct 2026",
                    nextServiceDue = "02 Nov 2026",
                    currentAssignment = "Warehouse - Sound Stage 1",
                    maintenanceNotes = "Voice coils tested, dampeners tight, 100% punch verified for Dhamal DJ.",
                    isVerifiedForTimelyDelivery = true
                ),
                EquipmentItem(
                    name = "Crown I-Tech 12000HD Power Amplifiers",
                    category = "Sound & Bass",
                    serialCode = "BJ-AMP-402",
                    conditionStatus = "READY",
                    healthPercentage = 95,
                    lastServiceDate = "28 Sep 2026",
                    nextServiceDue = "28 Oct 2026",
                    currentAssignment = "Main Mobile Sound Rack",
                    maintenanceNotes = "Cooling fans serviced and thermal paste checked.",
                    isVerifiedForTimelyDelivery = true
                ),
                EquipmentItem(
                    name = "Decorated Royal Wedding Rath (Chariot #1)",
                    category = "Decorated Rath",
                    serialCode = "BJ-RATH-01",
                    conditionStatus = "READY",
                    healthPercentage = 96,
                    lastServiceDate = "01 Oct 2026",
                    nextServiceDue = "15 Oct 2026",
                    currentAssignment = "Assigned: Verma Royal Wedding",
                    maintenanceNotes = "Golden frame polished, fresh LED ribbons wired, dual battery inverter charged.",
                    isVerifiedForTimelyDelivery = true
                ),
                EquipmentItem(
                    name = "Swar-Dhamal Orchestra Mobile Trolley Platform",
                    category = "Orchestra Trolley",
                    serialCode = "BJ-TRL-03",
                    conditionStatus = "READY",
                    healthPercentage = 94,
                    lastServiceDate = "29 Sep 2026",
                    nextServiceDue = "20 Oct 2026",
                    currentAssignment = "Ready for Processions",
                    maintenanceNotes = "Trolley axle grease and tire pressure checked. Singer stage railing locked.",
                    isVerifiedForTimelyDelivery = true
                ),
                EquipmentItem(
                    name = "Live Jagran Setup - Roland Octapad & Harmonium Kit",
                    category = "Live Jagran Setup",
                    serialCode = "BJ-JAG-105",
                    conditionStatus = "READY",
                    healthPercentage = 99,
                    lastServiceDate = "04 Oct 2026",
                    nextServiceDue = "04 Nov 2026",
                    currentAssignment = "Warehouse Spiritual Kit Section",
                    maintenanceNotes = "Dholak skin tuned, SPD-20 pads calibrated, Shure Beta 58 mics tested.",
                    isVerifiedForTimelyDelivery = true
                ),
                EquipmentItem(
                    name = "Sharpy 10R Beam Moving Head Lights (Set of 8)",
                    category = "Lighting & Truss",
                    serialCode = "BJ-LGT-210",
                    conditionStatus = "READY",
                    healthPercentage = 92,
                    lastServiceDate = "25 Sep 2026",
                    nextServiceDue = "25 Oct 2026",
                    currentAssignment = "Lighting Truss Case A & B",
                    maintenanceNotes = "DMX 512 wireless addressing checked, lamp burn hours at 14%.",
                    isVerifiedForTimelyDelivery = true
                ),
                EquipmentItem(
                    name = "Kirloskar 45 kVA Silent Soundproof Diesel Generator",
                    category = "Power & Generator",
                    serialCode = "BJ-GEN-77",
                    conditionStatus = "MAINTENANCE_DUE",
                    healthPercentage = 84,
                    lastServiceDate = "10 Sep 2026",
                    nextServiceDue = "08 Oct 2026",
                    currentAssignment = "Generator Bay Yard",
                    maintenanceNotes = "Oil filter replacement scheduled tomorrow before weekend wedding bookings.",
                    isVerifiedForTimelyDelivery = false
                ),
                EquipmentItem(
                    name = "Cryo CO2 Jet Blast Cannons & Cold Pyro Firing System",
                    category = "SFX",
                    serialCode = "BJ-SFX-301",
                    conditionStatus = "READY",
                    healthPercentage = 97,
                    lastServiceDate = "03 Oct 2026",
                    nextServiceDue = "03 Nov 2026",
                    currentAssignment = "Dancer DJ & Stage Box",
                    maintenanceNotes = "High-pressure hoses checked, wireless remote firing tested.",
                    isVerifiedForTimelyDelivery = true
                )
            )
            equipmentDao.insertAll(initialEquipment)

            val initialBookings = listOf(
                Booking(
                    clientName = "Rajesh Verma",
                    clientPhone = "9693870195",
                    altPhone = "9876543210",
                    eventType = "Wedding (Baraat & Sangeet)",
                    eventDate = "14 Oct 2026",
                    timeSlot = "Evening (7:00 PM - 1:00 AM)",
                    venueAddress = "Shree Krishna Marriage Lawn, Station Road",
                    selectedServices = "Dhamal DJ, Decorated Rath",
                    estimatedCost = 45000.0,
                    advancePaid = 15000.0,
                    status = "CONFIRMED",
                    checklistProgress = "sound=true,genset=true,rath=true,driver=true,dispatch=false",
                    notes = "High bass baraat procession required. Rath entry at 8:30 PM sharp."
                ),
                Booking(
                    clientName = "Amit Kumar Singh",
                    clientPhone = "9431200000",
                    altPhone = "9693870195",
                    eventType = "Mata Ki Chowki / Jagran",
                    eventDate = "18 Oct 2026",
                    timeSlot = "Full Night (9:00 PM - 5:00 AM)",
                    venueAddress = "Hanuman Mandir Community Hall, Main Chowk",
                    selectedServices = "Live Jagran Setup, Sound System",
                    estimatedCost = 28000.0,
                    advancePaid = 10000.0,
                    status = "CONFIRMED",
                    checklistProgress = "sound=true,genset=true,rath=false,driver=false,dispatch=false",
                    notes = "Sacred devotional setup with harmonium, dholak and 4 vocal mics."
                ),
                Booking(
                    clientName = "Sunil Sharma",
                    clientPhone = "9122334455",
                    altPhone = "9693870195",
                    eventType = "Birthday Celebration",
                    eventDate = "22 Oct 2026",
                    timeSlot = "Evening (6:00 PM - 11:00 PM)",
                    venueAddress = "Royal Banquet Club, Bypass Circle",
                    selectedServices = "Dancer DJ",
                    estimatedCost = 18000.0,
                    advancePaid = 5000.0,
                    status = "READY_FOR_DELIVERY",
                    checklistProgress = "sound=true,genset=true,rath=false,driver=true,dispatch=false",
                    notes = "Dance floor setup with moving head lights and smoke blast."
                ),
                Booking(
                    clientName = "Maa Durga Samiti",
                    clientPhone = "9800112233",
                    altPhone = "9693870195",
                    eventType = "Cultural Celebrations & Shobha Yatra",
                    eventDate = "25 Oct 2026",
                    timeSlot = "Full Day (10:00 AM - 8:00 PM)",
                    venueAddress = "Town Hall to River Ghat Route",
                    selectedServices = "Orchestra Trolley, Dhamal DJ",
                    estimatedCost = 55000.0,
                    advancePaid = 20000.0,
                    status = "PENDING",
                    checklistProgress = "sound=false,genset=false,rath=false,driver=false,dispatch=false",
                    notes = "Grand roadshow with live musicians on trolley and high-volume mobile sound."
                )
            )
            for (booking in initialBookings) {
                bookingDao.insertBooking(booking)
            }

            val initialOfflineBookings = listOf(
                OfflineBooking(
                    tokenNumber = "TRL-OFF-01",
                    clientName = "Gopal Yadav",
                    clientPhone = "9823456789",
                    eventType = "Wedding (Baraat Entry)",
                    eventDate = "19 Oct 2026",
                    timeSlot = "Evening (7:00 PM)",
                    spotLocation = "Taroli Purani Basti, Near Shiv Mandir",
                    setupSelected = "Decorated Rath + Dhamal DJ",
                    totalAgreedAmount = 38000.0,
                    cashAdvanceTaken = 10000.0,
                    paymentMode = "Cash",
                    bookedByStaff = "BalaJi Sound Office (Taroli)",
                    deliveryPromiseTime = "5:00 PM Sharp (2 Hrs Prior)",
                    status = "CONFIRMED",
                    notes = "Offline spot booking. Rath flower decoration with red roses requested."
                ),
                OfflineBooking(
                    tokenNumber = "TRL-OFF-02",
                    clientName = "Pandit Radheshyam Ji",
                    clientPhone = "9412345670",
                    eventType = "Mata Ki Chowki",
                    eventDate = "24 Oct 2026",
                    timeSlot = "Night (8:00 PM - 3:00 AM)",
                    spotLocation = "Taroli Community Dharmshala Hall",
                    setupSelected = "Live Jagran Setup",
                    totalAgreedAmount = 18000.0,
                    cashAdvanceTaken = 5000.0,
                    paymentMode = "UPI / PhonePe",
                    bookedByStaff = "Counter Booking (Taroli)",
                    deliveryPromiseTime = "6:00 PM Sound Check",
                    status = "CONFIRMED",
                    notes = "Offline advance received in person. Low-pitch floor monitors requested."
                )
            )
            offlineDao.insertAll(initialOfflineBookings)
        }
    }
}
