package com.example.data.repository

import com.example.data.database.BookingDao
import com.example.data.database.EquipmentDao
import com.example.data.database.OfflineBookingDao
import com.example.data.model.Booking
import com.example.data.model.EquipmentItem
import com.example.data.model.OfflineBooking
import kotlinx.coroutines.flow.Flow

class BalaJiRepository(
    private val bookingDao: BookingDao,
    private val equipmentDao: EquipmentDao,
    private val offlineBookingDao: OfflineBookingDao
) {
    val allBookings: Flow<List<Booking>> = bookingDao.getAllBookings()
    val allEquipment: Flow<List<EquipmentItem>> = equipmentDao.getAllEquipment()
    val allOfflineBookings: Flow<List<OfflineBooking>> = offlineBookingDao.getAllOfflineBookings()
    val offlineBookingCount: Flow<Int> = offlineBookingDao.getOfflineBookingCount()

    fun getBookingById(id: Long): Flow<Booking?> = bookingDao.getBookingById(id)

    suspend fun insertBooking(booking: Booking): Long = bookingDao.insertBooking(booking)
    suspend fun updateBooking(booking: Booking) = bookingDao.updateBooking(booking)
    suspend fun deleteBooking(booking: Booking) = bookingDao.deleteBooking(booking)

    suspend fun insertEquipment(item: EquipmentItem): Long = equipmentDao.insertEquipment(item)
    suspend fun updateEquipment(item: EquipmentItem) = equipmentDao.updateEquipment(item)
    suspend fun deleteEquipment(item: EquipmentItem) = equipmentDao.deleteEquipment(item)

    suspend fun insertOfflineBooking(booking: OfflineBooking): Long = offlineBookingDao.insertOfflineBooking(booking)
    suspend fun updateOfflineBooking(booking: OfflineBooking) = offlineBookingDao.updateOfflineBooking(booking)
    suspend fun deleteOfflineBooking(booking: OfflineBooking) = offlineBookingDao.deleteOfflineBooking(booking)
}
