package com.example.project
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.Int


class MyAdapter(private val arret_list: ArrayList<arret_complet>,
                private val arretDao: arretDao,
): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val textNum : TextView = itemView.findViewById(R.id.textArretNumber)
        val textSource : TextView = itemView.findViewById(R.id.textArretSource)
        val textDate : TextView = itemView.findViewById(R.id.textArretDate)
        val archivedIcon: ImageView = itemView.findViewById(R.id.archivedButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent,false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = arret_list[position]

        holder.textNum.text = currentItem.num_arret.toString()
        holder.textSource.text = currentItem.chambre_arret
        holder.textDate.text = currentItem.date_arret

        holder.archivedIcon.setImageResource( if(currentItem.isArchived) R.drawable.arret_favorate else R.drawable.favarret )

        holder.archivedIcon.setOnClickListener {
            currentItem.isArchived = !currentItem.isArchived
            arretDao.updateArret(currentItem)
            holder.archivedIcon.setImageResource( if(currentItem.isArchived) R.drawable.arret_favorate else R.drawable.favarret )
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context,arretActivity::class.java)
            intent.putExtra("numArret",currentItem.num_arret)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return arret_list.size
    }



}
