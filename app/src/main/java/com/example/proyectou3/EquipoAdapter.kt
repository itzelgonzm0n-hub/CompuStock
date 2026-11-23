package com.example.proyectou3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectou3.databinding.ItemEquipoBinding

class EquipoAdapter(
    private val lista: ArrayList<Equipo>
) : RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>() {

    class EquipoViewHolder(
        val binding: ItemEquipoBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquipoViewHolder {

        val binding = ItemEquipoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EquipoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EquipoViewHolder,
        position: Int
    ) {
        val equipo = lista[position]

        holder.binding.tvNombre.text = equipo.nombre
        holder.binding.tvTipo.text = "Tipo: ${equipo.tipo}"
        holder.binding.tvMarca.text = "Marca: ${equipo.marca}"
        holder.binding.tvSerie.text = "Serie: ${equipo.serie}"
        holder.binding.tvEstado.text = "Estado: ${equipo.estado}"
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}