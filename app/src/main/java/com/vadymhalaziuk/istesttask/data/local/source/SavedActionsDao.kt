package com.vadymhalaziuk.istesttask.data.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vadymhalaziuk.istesttask.data.local.model.CooledDownActionLocalDto

@Dao
interface SavedActionsDao {
    @Query("SELECT * FROM $SAVED_ACTIONS_DB_NAMESPACE")
    suspend fun getAll(): List<CooledDownActionLocalDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CooledDownActionLocalDto)
}