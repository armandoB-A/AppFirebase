package com.example.appfirebase.inicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfirebase.R
import com.example.appfirebase.customAdapter.AdaptadorCustom
import com.example.appfirebase.databinding.FragmentInicioBinding
import com.example.appfirebase.usuario.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentInicio : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val db = Firebase.firestore

        val binding= FragmentInicioBinding.inflate(layoutInflater)

        db.collection("users")
            .get()
            .addOnSuccessListener {
                var usuario = arrayListOf<Usuario>()

                it.documents.forEach { its ->
                    usuario.add(
                        Usuario(
                            its.get("id").toString(),
                            its.get("nombre").toString(),
                            its.get("telefono").toString(),
                            its.get("correo").toString(),
                            its.get("domicilio").toString()
                        )
                    )
                }
                val customAdapterAl = AdaptadorCustom(binding.root.context, usuario)

                binding.reciclerViewProductos.adapter = customAdapterAl
                binding.reciclerViewProductos.layoutManager = LinearLayoutManager(binding.root.context)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(binding.root.context, "fallo al traer datos", Toast.LENGTH_SHORT)
                    .show()
            }
        binding.fab.setOnClickListener {
            it.findNavController().navigate(R.id.action_fragmentInicio_to_fragmetAgregar)
        }
        return binding.root
    }

}