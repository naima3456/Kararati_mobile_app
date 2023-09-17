package com.example.project

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.settings.*
import org.json.JSONArray
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class Settings : AppCompatActivity() {
    //global variables
    lateinit var obj: arret_complet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        //variables globales
        var Num_arr = arrayListOf<Int>()
        var chamb_arr = arrayListOf<String>()
        var ref_arr = arrayListOf<String>()
        var an_ref_arr = arrayListOf<Int>()
        var num_ref_arr = arrayListOf<String>()
        var num_page_arr = arrayListOf<String>()
        var date_arr = arrayListOf<String>()
        var part_arr = arrayListOf<String>()
        var principe_arr = arrayListOf<String>()
        var decision_arr = arrayListOf<String>()
        var decision_op_arr = arrayListOf<String>()
        var composition_arr = arrayListOf<String>()

        var json : String? = null

        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH)
        obj = arret_complet(0,"a", "a", "a", "a","a", "01-01-2023","a", "a", "a", "a","a")
        //ouvrir le fihcier json
        val inputStream:InputStream = assets.open("arrets_cours_suppreme.json")
        json = inputStream.bufferedReader().use { it.readText() }
        var jsonarr = JSONArray(json) //array of json objects


        val instance = AppDatabase.buildDatabase(this)


        //Database stuffs
        var helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase

        db?.execSQL("DROP TABLE IF EXISTS ARRETS")
        helper.onCreate(db)


        var rs = db.rawQuery(" SELECT * FROM ARRETS", null) //curseur
        var cv =
            ContentValues() //This is the class that is responsible of storing the data inside the database

        charger_bdd.setOnClickListener {


            if (instance?.getArretDao()?.isEmpty() == false) {
                Toast.makeText(
                    this,
                    "البيانات التي تريد تحميلها في قاعدة البيانات موجودة بالفعل",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val len = jsonarr.length()

                for (i in 0..len - 1) {


                    var jsonobj = jsonarr.getJSONObject(i)
                    Num_arr.add(jsonobj.getInt("NumAr"))
                    obj.num_arret = Num_arr[i]
                    chamb_arr.add(jsonobj.getString("ChambreAr"))
                    obj.chambre_arret = chamb_arr[i]
                    ref_arr.add(jsonobj.getString("ReferenceAr"))
                    obj.reference_arret = ref_arr[i]
                    an_ref_arr.add(jsonobj.getInt("AnneeRefAr"))
                    obj.annee_arret = an_ref_arr[i].toString()
                    num_ref_arr.add(jsonobj.getString("numRef"))
                    obj.numRef_arret = num_ref_arr[i]
                    num_page_arr.add(jsonobj.getString("numPageRef"))
                    obj.numPageRef_arret = num_page_arr[i]
                    date_arr.add(jsonobj.getString("DateAr"))
                    obj.date_arret = date_arr[i]
                    part_arr.add(jsonobj.getString("PartiesAr"))
                    obj.parties_arret = part_arr[i]
                    principe_arr.add(jsonobj.getString("PrincipeAr"))
                    obj.principe_arret = principe_arr[i]
                    decision_arr.add(jsonobj.getString("DecisionAr"))
                    obj.decision_arret = decision_arr[i]
                    decision_op_arr.add(jsonobj.getString("DecisionOperAr"))
                    obj.decisionOper_arret = decision_op_arr[i]
                    composition_arr.add(jsonobj.getString("CompositionAr"))
                    obj.composition_arret = composition_arr[i]

                    instance?.getArretDao()?.addarret(obj)


                }
                Toast.makeText(this, "تم تحميل البيانات بنجاح", Toast.LENGTH_SHORT).show()

            }
        }
        vider_bdd.setOnClickListener {

            if (instance?.getArretDao()?.isEmpty() == true) {
                Toast.makeText(
                    this,
                    "قاعدة البيانات فارغة بالفعل",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                instance?.getArretDao()?.viderBDD()
                Toast.makeText(this, "قاعدة البيانات فارغة الآن", Toast.LENGTH_SHORT).show()
            }
        }

//
//            go_list.setOnClickListener {
//                startActivity(Intent(this@settings, Arretslist::class.java))
//
//
//            }


    }


}
