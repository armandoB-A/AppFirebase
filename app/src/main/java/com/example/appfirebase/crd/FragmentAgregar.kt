package com.example.appfirebase.crd

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.appfirebase.R
import com.example.appfirebase.databinding.FragmentAgregarBinding
import com.example.appfirebase.inicio.Inicio
import com.example.appfirebase.usuario.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentAgregar : Fragment() {

    private val db = Firebase.firestore
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding= FragmentAgregarBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.btnGuardar.setOnClickListener {
            val user = Usuario(
                (0..1000).random().toString(),
                binding.nombre?.text.toString(),
                binding.telefono?.text.toString(),
                binding.correo?.text.toString(),
                binding.domicilio?.text.toString()
            )

            db.collection("users").document(user.id.toString()).set(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(binding.root.context, "Usuario guardado", Toast.LENGTH_LONG).show()
                    binding.nombre?.setText("")
                    binding.telefono?.setText("")
                    binding.correo?.setText("")
                    binding.domicilio?.setText("")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(binding.root.context, "error al guardar $e", Toast.LENGTH_LONG).show()
                }
        }
        binding.btnCancelar.setOnClickListener {
                activity?.onBackPressed()
        }
        
        return binding.root
    }


}