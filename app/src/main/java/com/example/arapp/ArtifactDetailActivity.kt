package com.example.arapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ArtifactDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artifact_detail)

        val name = intent.getStringExtra("name")
        val catalogue = intent.getStringExtra("catalogue")
        val desc = intent.getStringExtra("desc")
        val condition = intent.getStringExtra("condition")
        val restrictions = intent.getStringExtra("restrictions")
        val source = intent.getStringExtra("source")
        val imageRes = intent.getIntExtra("imageRes", 0)

        findViewById<ImageView>(R.id.artifactImage).setImageResource(imageRes)
        findViewById<TextView>(R.id.artifactName).text = name
        findViewById<TextView>(R.id.artifactCatalogue).text = "Catalogue No: $catalogue"
        findViewById<TextView>(R.id.artifactPhysicalDesc).text = "Details: $desc"
        findViewById<TextView>(R.id.artifactCondition).text = "Condition: $condition"
        findViewById<TextView>(R.id.artifactRestrictions).text = "Restrictions: $restrictions"
        findViewById<TextView>(R.id.artifactSource).text = "Source: $source"

        findViewById<Button>(R.id.btnLaunchAR).setOnClickListener {
            val arIntent = Intent(this, MainActivity::class.java)
            arIntent.putExtra("artifact_name", name)
            arIntent.putExtra("artifact_imageRes", imageRes)   // <-- ADD THIS LINE
            startActivity(arIntent)
        }
    }
}
