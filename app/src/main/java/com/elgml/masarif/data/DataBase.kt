package com.elgml.masarif.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elgml.masarif.pojo.Operation

@Database(version = 7, entities = [Operation::class], exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun operationDao(): OperationDao

    companion object {
        fun getInstance(application: Application): DataBase {
            return Room.databaseBuilder(
                application.applicationContext,
                DataBase::class.java,
                "operation_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}