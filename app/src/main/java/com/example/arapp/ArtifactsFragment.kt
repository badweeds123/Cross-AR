package com.example.arapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ArtifactsFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the correct layout
        val view = inflater.inflate(R.layout.fragment_artifacts, container, false)

        dbHelper = DatabaseHelper(requireContext())

        // ListView that shows all artifact names
        val listView: ListView = view.findViewById(R.id.listViewArtifacts)

        val artifacts = dbHelper.getArtifacts()
        val artifactNames = artifacts.map { it.name }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            artifactNames
        )
        listView.adapter = adapter

        // When an item is tapped, open the detail screen
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedArtifact = artifacts[position]

            val intent = Intent(requireContext(), ArtifactDetailActivity::class.java).apply {
                putExtra("artifact_name", selectedArtifact.name)
                putExtra("artifact_description", selectedArtifact.description)
            }
            startActivity(intent)
        }

        // ✅ Set the header text safely
        val descriptionView = view.findViewById<TextView>(R.id.description)
        descriptionView.text = "📜 Artifacts Page"

        return view
    }
}
