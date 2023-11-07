package com.example.animal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class petsAdapter(private val itemList: ArrayList<Pet>) : RecyclerView.Adapter<petsAdapter
.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petName: TextView = view.findViewById(R.id.petName)
        val petSpecie: TextView = view.findViewById(R.id.species)
        val petImage: ImageView = view.findViewById(R.id.image)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.pets_list, parent,
            false
        )
        return MyViewHolder(itemView)
        
    }
    
    override fun getItemCount(): Int {
        return itemList.size
    }
    
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.petName.text = currentItem.name
        holder.petSpecie.text = currentItem.specie
//        holder.petImage.setImageResource(currentItem.image)
        Picasso.get().load(currentItem.image).into(holder.petImage)
    }
}