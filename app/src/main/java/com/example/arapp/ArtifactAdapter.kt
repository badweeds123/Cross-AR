package com.example.arapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArtifactAdapter(
    private val artifacts: List<Artifact>,
    private val onItemClick: (Artifact) -> Unit
) : RecyclerView.Adapter<ArtifactAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumb: ImageView = view.findViewById(R.id.imageThumb)
        val textName: TextView = view.findViewById(R.id.textName)
        init {
            view.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick(artifacts[pos])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artifact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = artifacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artifact = artifacts[position]
        holder.textName.text = artifact.name
        holder.imageThumb.setImageResource(artifact.imageResId)
    }
}
