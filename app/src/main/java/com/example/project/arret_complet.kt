package com.example.project

import androidx.room.*
import java.util.Date


@Entity(tableName = "arrets")
data class arret_complet(
    @PrimaryKey
    var num_arret: Int,
    var chambre_arret: String,
    var reference_arret: String, var annee_arret: String,
    var numRef_arret: String, var numPageRef_arret: String,
    var date_arret: String,
    var parties_arret: String,
    var principe_arret: String, var decision_arret: String,
    var decisionOper_arret: String, var composition_arret: String,
    var isArchived:Boolean = false
)


