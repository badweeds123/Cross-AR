package com.example.arapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class ArtifactListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artifact_list)

        val recycler = findViewById<RecyclerView>(R.id.recyclerArtifacts)
        recycler.layoutManager = LinearLayoutManager(this)

        val artifacts = listOf(
            Artifact(
                name = "Projector",
                description = "Vintage projector from early 20th century.",
                catalogueNumber = "2022.1.1",
                physicalDescription = "Measurement of the case: gap – right side = 36 x 23 x 11 cm; left side = 36 x 23 x 10 cm",
                condition = "Not functional",
                restrictions = "Avoid water; handle face up; avoid falling",
                source = "Holy Cross of Davao College",
                imageResId = R.drawable.default_artifact_image
            )
            // Add more artifacts
        )


        val adapter = ArtifactAdapter(artifacts) { artifact ->
            val intent = Intent(this, ArtifactDetailActivity::class.java).apply {
                putExtra("name", artifact.name)
                putExtra("catalogue", artifact.catalogueNumber)
                putExtra("desc", artifact.physicalDescription)
                putExtra("condition", artifact.condition)
                putExtra("restrictions", artifact.restrictions)
                putExtra("source", artifact.source)
                putExtra("imageRes", artifact.imageResId)
            }
            startActivity(intent)
        }
        recycler.adapter = adapter
    }
}

