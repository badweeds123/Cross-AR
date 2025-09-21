package com.example.arapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class ArtifactsFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_artifacts_list, container, false)

        dbHelper = DatabaseHelper(requireContext())
        val listView: ListView = view.findViewById(R.id.listViewArtifacts)

        val artifacts = dbHelper.getArtifacts()
        val artifactNames = artifacts.map { it.name }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, artifactNames)
        listView.adapter = adapter

        // ✅ On click, open detail activity
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedArtifact = artifacts[position]

            val intent = Intent(requireContext(), ArtifactDetailActivity::class.java).apply {
                putExtra("artifact_name", selectedArtifact.name)
                putExtra("artifact_description", selectedArtifact.description)
            }
            startActivity(intent)
        }

        return view
    }
}
