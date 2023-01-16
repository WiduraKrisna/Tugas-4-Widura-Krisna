package com.example.tugas4lid

import android.os.Bundle
import android.util.Log
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val ledToggleButton: ToggleButton = findViewById(R.id.ledToggleButton)

        val database = Firebase.database
        val databaseReference = database.getReference("led")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue<Int>()

                ledToggleButton.isChecked = value != 0
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("RealtimeDB", "Failed to read value.", error.toException())
            }
        })

        ledToggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                databaseReference.setValue(1)
            } else {
                databaseReference.setValue(0)
            }
        }
    }
}