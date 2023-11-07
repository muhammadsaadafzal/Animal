package com.example.animal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class All_Pets : AppCompatActivity() {
    
    //    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var petsAdapter: petsAdapter
    private lateinit var petList: ArrayList<Pet>
    private val database = FirebaseDatabase.getInstance().getReference("pet")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_pets)
        val addbtn = findViewById<FloatingActionButton>(R.id.addnewbtn)
        addbtn.setOnClickListener {
            val i = Intent(this, AddPet::class.java)
            startActivity(i)
        }

//        recyclerView = findViewById(R.id.pet_list)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.hasFixedSize()
//        recyclerView.adapter = petsAdapter
        petList = arrayListOf<Pet>()

//        getPetData()
    }
    
    private fun getPetData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    
                    for (petSnap in snapshot.children) {
                        Toast.makeText(
                            applicationContext,
                            "running: $petSnap",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        val pet = petSnap.getValue(Pet::class.java)
                        petList.add(pet!!)
                    }
                    recyclerView.adapter = petsAdapter(petList)
                }
            }
            
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "error: $error", Toast.LENGTH_LONG).show()
            }
            
        })
    }
    
}