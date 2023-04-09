package com.vadymhalaziuk.istesttask.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vadymhalaziuk.istesttask.data.local.source.SAVED_ACTIONS_DB_NAMESPACE

@Entity(tableName = SAVED_ACTIONS_DB_NAMESPACE)
data class CooledDownActionLocalDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "lastActionTimestamp") val lastActionTimestamp: Long,
)
