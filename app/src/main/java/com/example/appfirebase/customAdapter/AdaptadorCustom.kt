package com.example.appfirebase.customAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appfirebase.R
import com.example.appfirebase.usuario.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AdaptadorCustom(private var context: Context, var usuaioss: ArrayList<Usuario>?) :
    RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {
    var alumnosOriginales = usuaioss
    private val db = Firebase.firestore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.item_agenda, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        viewHolder.textView.text = usuaioss!![position].nombre
        viewHolder.textView2.text = "telefono: ${usuaioss!![position].telefono}"
        viewHolder.textView3.text = "correo: " + usuaioss!![position].correo
        viewHolder.textView4.text = "domicilio: " + usuaioss!![position].domicilio
        viewHolder.borrar.setOnClickListener {

            db.collection("users").document(usuaioss!![position].id.toString())
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "se borro", Toast.LENGTH_LONG).show()
                    //context.startActivity(Intent(context, Inicio::class.java))
                    notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "error al guardar $e", Toast.LENGTH_LONG).show()
                }
        }
        viewHolder.actualizar.setOnClickListener {
            val bundle = bundleOf(
                "nombre" to usuaioss!![position].nombre,
                "telefono" to usuaioss!![position].telefono,
                "correo" to usuaioss!![position].correo,
                "domicilio" to usuaioss!![position].domicilio,
                "id" to usuaioss!![position].id)
            it.findNavController().navigate(R.id.action_fragmentInicio_to_fragmentEdit, bundle)
            //(context as Inicio::class.java).finish()
            
        }
    }

    override fun getItemCount(): Int {
        return usuaioss!!.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(strSearch: String) {
        if (strSearch.isEmpty()) {

            usuaioss = alumnosOriginales
        } else {
            val collect: ArrayList<Usuario> =
                alumnosOriginales!!.filter { s ->
                    s.nombre?.toLowerCase()!!.contains(strSearch.toLowerCase())
                } as ArrayList<Usuario>
            /*.filter(ss-> ss.)
            .collect(Collectors.toList<Any>())*/
            usuaioss = collect
        }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textView2: TextView
        val textView3: TextView
        val textView4: TextView
        val borrar: ImageButton
        val actualizar: ImageButton

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById<View>(R.id.textEDITnombre) as TextView
            textView2 = view.findViewById<View>(R.id.textEDITtelefono) as TextView
            textView3 = view.findViewById<View>(R.id.textEDITcorreo) as TextView
            textView4 = view.findViewById<View>(R.id.textEDITdomicilio) as TextView
            borrar = view.findViewById(R.id.btnDelete) as ImageButton
            actualizar = view.findViewById(R.id.btnRename) as ImageButton
        }
    }
}
