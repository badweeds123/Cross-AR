package com.example.arapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ArtifactListActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artifacts_list)

        dbHelper = DatabaseHelper(this)

        val listView: ListView = findViewById(R.id.listViewArtifacts)

        val artifacts = dbHelper.getArtifacts()

        val artifactNames = artifacts.map { it.name }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, artifactNames)
        listView.adapter = adapter
    }
}
