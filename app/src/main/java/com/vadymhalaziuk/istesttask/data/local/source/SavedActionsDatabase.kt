package com.vadymhalaziuk.istesttask.data.local.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vadymhalaziuk.istesttask.data.local.model.CooledDownActionLocalDto
import com.vadymhalaziuk.istesttask.data.local.source.SavedActionsDatabase.Companion.VERSION

@Database(version = VERSION, entities = [CooledDownActionLocalDto::class])
abstract class SavedActionsDatabase : RoomDatabase() {

    abstract val dao: SavedActionsDao

    companion object {
        const val VERSION = 1
    }
}