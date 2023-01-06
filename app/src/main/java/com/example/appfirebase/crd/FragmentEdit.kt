package com.example.appfirebase.crd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.appfirebase.R
import com.example.appfirebase.databinding.FragmentAgregarBinding
import com.example.appfirebase.usuario.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FragmentEdit : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = Firebase.firestore


        val binding = FragmentAgregarBinding.inflate(layoutInflater)

        val id:String=arguments?.getString("id").toString()

        binding.nombre.setText(arguments?.getString("nombre") )
        binding.telefono.setText(arguments?.getString("telefono") )
        binding.correo.setText(arguments?.getString("correo") )
        binding.domicilio.setText(arguments?.getString("domicilio") )

        binding.btnGuardar.setOnClickListener {
            val user = Usuario(
                id,
                binding.nombre?.text.toString(),
                binding.telefono?.text.toString(),
                binding.correo?.text.toString(),
                binding.domicilio?.text.toString()
            )

            db.collection("users").document(id.toString()).set(user)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(binding.root.context, "Usuario actualizado", Toast.LENGTH_LONG)
                        .show()
                    activity?.onBackPressed()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(binding.root.context, "error al guardar $e", Toast.LENGTH_LONG)
                        .show()
                }
        }
        binding.btnCancelar.setOnClickListener {
            activity?.onBackPressed()
        }
        return binding.root
    }
}