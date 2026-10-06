package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Booking
import com.example.data.model.EquipmentItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY id DESC")
    fun getAllBookings(): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE id = :id")
    fun getBookingById(id: Long): Flow<Booking?>

    @Query("SELECT * FROM bookings WHERE status = :status ORDER BY id DESC")
    fun getBookingsByStatus(status: String): Flow<List<Booking>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Booking): Long

    @Update
    suspend fun updateBooking(booking: Booking)

    @Delete
    suspend fun deleteBooking(booking: Booking)

    @Query("SELECT COUNT(*) FROM bookings")
    suspend fun getBookingCount(): Int
}

@Dao
interface EquipmentDao {
    @Query("SELECT * FROM equipment ORDER BY id ASC")
    fun getAllEquipment(): Flow<List<EquipmentItem>>

    @Query("SELECT * FROM equipment WHERE conditionStatus = :status")
    fun getEquipmentByStatus(status: String): Flow<List<EquipmentItem>>

    @Query("SELECT * FROM equipment WHERE category = :category")
    fun getEquipmentByCategory(category: String): Flow<List<EquipmentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipment(item: EquipmentItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<EquipmentItem>)

    @Update
    suspend fun updateEquipment(item: EquipmentItem)

    @Delete
    suspend fun deleteEquipment(item: EquipmentItem)

    @Query("SELECT COUNT(*) FROM equipment")
    suspend fun getEquipmentCount(): Int
}

@Dao
interface OfflineBookingDao {
    @Query("SELECT * FROM offline_bookings ORDER BY id DESC")
    fun getAllOfflineBookings(): Flow<List<com.example.data.model.OfflineBooking>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOfflineBooking(booking: com.example.data.model.OfflineBooking): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bookings: List<com.example.data.model.OfflineBooking>)

    @Update
    suspend fun updateOfflineBooking(booking: com.example.data.model.OfflineBooking)

    @Delete
    suspend fun deleteOfflineBooking(booking: com.example.data.model.OfflineBooking)

    @Query("SELECT COUNT(*) FROM offline_bookings")
    fun getOfflineBookingCount(): Flow<Int>
}
