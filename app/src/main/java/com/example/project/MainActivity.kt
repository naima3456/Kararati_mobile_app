package com.example.project

import android.content.ContentValues
import android.content.Intent
import android.icu.text.MessageFormat.format
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.android.synthetic.main.activity_arret.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.InputStream
import java.lang.String.format
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    //variables globales

    lateinit var obj: arret_complet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var json : String? = null
        val gotosettings = findViewById<TextView>(R.id.gotosettings)

        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH)
        obj = arret_complet(0,"a", "a", "a", "a","a", "01-01-2023","a", "a", "a", "a","a")
        //ouvrir le fihcier json
        val inputStream:InputStream = assets.open("arrets_cours_suppreme.json")
        json = inputStream.bufferedReader().use { it.readText() }
        var jsonarr = JSONArray(json) //array of json objects


//        val instance = AppDatabase.buildDatabase(this)

//        charger_bdd.setOnClickListener {
//
//
//            if (instance?.getArretDao()?.isEmpty() == false) {
//                Toast.makeText(
//                    this,
//                    "البيانات التي تريد تحميلها في قاعدة البيانات موجودة بالفعل",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//
//                val len = jsonarr.length()
//
//                for (i in 0..len - 1) {
//
//
//                    var jsonobj = jsonarr.getJSONObject(i)
//                    Num_arr.add(jsonobj.getInt("NumAr"))
//                    obj.num_arret = Num_arr[i]
//                    chamb_arr.add(jsonobj.getString("ChambreAr"))
//                    obj.chambre_arret = chamb_arr[i]
//                    ref_arr.add(jsonobj.getString("ReferenceAr"))
//                    obj.reference_arret = ref_arr[i]
//                    an_ref_arr.add(jsonobj.getString("AnneeRefAr"))
//                    obj.annee_arret = an_ref_arr[i]
//                    num_ref_arr.add(jsonobj.getString("numRef"))
//                    obj.numRef_arret = num_ref_arr[i]
//                    num_page_arr.add(jsonobj.getString("numPageRef"))
//                    obj.numPageRef_arret = num_page_arr[i]
//                    date_arr.add(jsonobj.getString("DateAr"))
//                    //val formatter = SimpleDateFormat("dd-MM-yyyy")
//                    //val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//                    val date= formatter.parse(date_arr[i])
//                    obj.date_arret = date
//                    part_arr.add(jsonobj.getString("PartiesAr"))
//                    obj.parties_arret = part_arr[i]
//                    principe_arr.add(jsonobj.getString("PrincipeAr"))
//                    obj.principe_arret = principe_arr[i]
//                    decision_arr.add(jsonobj.getString("DecisionAr"))
//                    obj.decision_arret = decision_arr[i]
//                    decision_op_arr.add(jsonobj.getString("DecisionOperAr"))
//                    obj.decisionOper_arret = decision_op_arr[i]
//                    composition_arr.add(jsonobj.getString("CompositionAr"))
//                    obj.composition_arret = composition_arr[i]
//
//                    instance?.getArretDao()?.addarret(obj)
//
//
//                }
//                Toast.makeText(this, "تم تحميل البيانات بنجاح", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//         vider_bdd.setOnClickListener {
//
//            if (instance?.getArretDao()?.isEmpty() == true) {
//                Toast.makeText(
//                    this,
//                    "قاعدة البيانات فارغة بالفعل",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                instance?.getArretDao()?.viderBDD()
//                Toast.makeText(this, "قاعدة البيانات فارغة الآن", Toast.LENGTH_SHORT).show()
//            }
//        }

        gotosettings.setOnClickListener{

            val intentt = Intent(this, Settings::class.java)
            startActivity(intentt)
        }
            go_list.setOnClickListener {
                startActivity(Intent(this@MainActivity, Arretslist::class.java))


            }








    }





}