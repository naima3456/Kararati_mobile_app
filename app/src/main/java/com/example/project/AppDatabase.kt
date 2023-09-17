package com.example.project

import android.content.Context
import androidx.room.*

@TypeConverters(Converters::class)
@Database(entities = [arret_complet::class ] , version = 1)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun getArretDao():arretDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context,AppDatabase::class.java,
                        "arrets_db").allowMainThreadQueries().build() }

            return INSTANCE
        }
    }

}