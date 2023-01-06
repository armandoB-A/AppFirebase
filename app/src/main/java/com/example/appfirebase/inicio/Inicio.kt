package com.example.appfirebase.inicio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appfirebase.databinding.ActivityInicioBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Inicio : AppCompatActivity() {
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}