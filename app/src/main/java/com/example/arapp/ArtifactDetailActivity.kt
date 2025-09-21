package com.example.arapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ArtifactDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artifact_detail)

        // Get artifact data from intent
        val artifactName = intent.getStringExtra("artifact_name") ?: "Unknown Artifact"
        val artifactDescription = intent.getStringExtra("artifact_description") ?: "No description available."

        // Bind UI
        val tvName = findViewById<TextView>(R.id.tvArtifactName)
        val tvDescription = findViewById<TextView>(R.id.tvArtifactDescription)
        val btnViewAR = findViewById<Button>(R.id.btnViewAR)

        tvName.text = artifactName
        tvDescription.text = artifactDescription

        // Open AR view
        btnViewAR.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("artifact_name", artifactName)
            startActivity(intent)
        }
    }
}
