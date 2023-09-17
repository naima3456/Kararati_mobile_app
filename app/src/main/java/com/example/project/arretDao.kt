package com.example.project
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery

@Dao
interface arretDao {

    @Insert
    fun addarret(vararg arrets: arret_complet)

    @Query("select * from arrets")
    fun getarret():List<arret_complet>

    @Update
    fun updateArret(arret: arret_complet)

    @Query("SELECT (SELECT COUNT(*) FROM arrets) == 0")
    fun isEmpty(): Boolean

    @Query("DELETE FROM arrets")
    fun viderBDD()

    @Query("SELECT * FROM arrets WHERE num_arret=:numArret LIMIT 1")
    fun getArretById(numArret : Int): arret_complet

    @RawQuery()
    fun searchArretsByFilter(query:SimpleSQLiteQuery): List<arret_complet>

}
