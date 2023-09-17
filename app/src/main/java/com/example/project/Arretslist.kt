package com.example.project
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.project.databinding.ActivityArretslistBinding
import kotlin.collections.ArrayList

class Arretslist : AppCompatActivity() {

    private lateinit var binding: ActivityArretslistBinding
    private lateinit var arretCompletList : MutableLiveData<ArrayList<arret_complet>>
    private lateinit var arretDao: arretDao
    private var chosenRoom : String? = null
    private var chosenYear : String? = null
    private var chosenSource : String? = null
    private var archivedArret : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        // Binding activity with interface xml
        super.onCreate(savedInstanceState)
        binding = ActivityArretslistBinding.inflate(layoutInflater)
        // init offline Dao Database
        arretDao = AppDatabase.buildDatabase(this)?.getArretDao()!!

        arretCompletList = MutableLiveData<ArrayList<arret_complet>>()
        binding.arretsList.layoutManager = LinearLayoutManager(this)

        setContentView(binding.root)

        // Room Choices Button
        binding.roomChoicesBtn.setOnClickListener{
            searchArrestsByFilter(null)
            val popupMenu = PopupMenu(this,it)
            popupMenu.menuInflater.inflate(R.menu.room_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{ item ->
                    changeRoomTo(item.itemId)
            }
            popupMenu.show()
        }

        // Year Choices Button
        binding.yearChoicesBtn.setOnClickListener{
            searchArrestsByFilter(null)
            val popupMenu = PopupMenu(this,it)
            popupMenu.menuInflater.inflate(R.menu.year_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{ item ->
                changeYearTo(item.itemId)
            }
            popupMenu.show()
        }

        // Source Choices Button
        binding.sourceChoicesBtn.setOnClickListener{
            searchArrestsByFilter(null)
            val popupMenu = PopupMenu(this,it)
            popupMenu.menuInflater.inflate(R.menu.sources_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener{ item ->
                changeSourceTo(item.itemId)
            }
            popupMenu.show()
        }

        // for search bar
        binding.searchBar.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchBar.clearFocus()
                searchArrestsByFilter(textToBeSearched =  query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchArrestsByFilter(textToBeSearched =  newText)
                return false
            }

        })

        // initial arrets list
        initArretsList()

         // Bottom navigation Button
        binding.bottomnavigationview.setOnItemSelectedListener {

            binding.searchBar.clearFocus()
            binding.searchBar.setQuery("", false)

             when (it.itemId) {
                R.id.fav -> {
                    archivedArret = 1
                    searchArrestsByFilter(null)
                }
                else -> {
                    archivedArret = 0
                    searchArrestsByFilter(null)
                }
            }


            true
        }


        // to observe the list of arrets
        arretCompletList.observe(
            this
        ) {

            if(it.isNullOrEmpty()){
                binding.arretsList.isVisible = false
                binding.emptyText.isVisible = true
            }else{
                binding.arretsList.isVisible = true
                binding.emptyText.isVisible = false
                binding.arretsList.adapter = MyAdapter(arretCompletList.value!!, arretDao)
            }
        }

    }




    private fun initArretsList(){
        // the first time opening the app
        searchArrestsByFilter(null)
    }

    private fun changeRoomTo(idProvince:Int):Boolean{

       chosenRoom = when(idProvince){
            R.id.civilRoom ->  getString(R.string.civil_room)
            R.id.offenseRoom -> getString(R.string.offense_room)
            R.id.stateRoom -> getString(R.string.state_room)
            R.id.marineRoom -> getString(R.string.marine_room)
            R.id.arretsRoom -> getString(R.string.arrets_room)
            else -> getString(R.string.no_room)
        }

        binding.roomChoicesBtn.text = chosenRoom
        if(chosenRoom == getString(R.string.no_room)) chosenRoom = null

        searchArrestsByFilter(null)
        return true
    }

    private fun changeYearTo(idYear:Int):Boolean{

        chosenYear = when(idYear){
            R.id.year2000 ->  getString(R.string._2000)
            R.id.year2001 -> getString(R.string._2001)
            R.id.year2002 -> getString(R.string._2002)
            R.id.year2003 -> getString(R.string._2003)
            else -> getString(R.string.no_year)
        }

        binding.yearChoicesBtn.text = chosenYear
        if(chosenYear == getString(R.string.no_year) ) chosenYear = null

        searchArrestsByFilter(null)
        return true
    }

    private fun changeSourceTo(idSource:Int):Boolean{

        chosenSource = when(idSource){
            R.id.justiceSource ->  getString(R.string.justice_source)
            R.id.newspaperSource -> getString(R.string.newspaper_source)
            else -> getString(R.string.no_source)
        }

        binding.sourceChoicesBtn.text = chosenSource
        if(chosenSource == getString(R.string.no_source)) chosenSource = null

        searchArrestsByFilter(null)
        return true
    }

    private fun searchArrestsByFilter(textToBeSearched: String?){
        val statements = ArrayList<String>()
        var query = "SELECT * FROM arrets"

        if(chosenRoom != null) statements.add("chambre_arret = '$chosenRoom'")
        if (chosenYear != null) statements.add("date_arret LIKE '%$chosenYear%'")
        if(chosenSource!=null) statements.add("reference_arret LIKE '%$chosenSource%'")
        if(archivedArret == 1) statements.add("isArchived=$archivedArret")

        if(textToBeSearched != null){
            var sequenceQuery = " ( "
            val wordList = textToBeSearched.trim().replace("\\s+".toRegex(), " ").split("\\s".toRegex())
            val statement = ArrayList<String>()
            // list of variable that want to search remove one of them if you won't
            val listVariables = listOf(
                "chambre_arret", "reference_arret", "annee_arret",
                "numRef_arret", "numPageRef_arret", "date_arret",
                "parties_arret", "principe_arret", "decision_arret",
                "decisionOper_arret", "composition_arret"
            )

            for (variable in listVariables){
                for (word in wordList){
                    statement.add("$variable LIKE '%$word%'")
                }
            }
            sequenceQuery += statement.joinToString(separator = " or ")

            sequenceQuery += " ) "

            statements.add(sequenceQuery)
        }


        if(statements.isNotEmpty()){
            query += " WHERE "
            query += if(statements.size>1) statements.joinToString(separator = " and ") else statements[0]
        }

        arretCompletList.value =  arretDao.searchArretsByFilter(query = SimpleSQLiteQuery(query)) as ArrayList<arret_complet>

    }



}