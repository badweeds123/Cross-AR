package com.example.arapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnArtifacts = findViewById<Button>(R.id.btnArtifacts)
        val btnArtifactList = findViewById<Button>(R.id.btnArtifactList)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnArtifacts.setOnClickListener {
            startActivity(Intent(this, ArtifactListActivity::class.java))
        }

        btnArtifactList.setOnClickListener {
            startActivity(Intent(this, ArtifactListActivity::class.java))
        }

        btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
