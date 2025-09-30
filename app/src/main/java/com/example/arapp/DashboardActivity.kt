package com.example.arapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // ✅ Optional: populate sample artifacts into database (only if empty)
        val dbHelper = DatabaseHelper(this)
        if (dbHelper.getArtifacts().isEmpty()) {
            dbHelper.insertArtifact(
                name = "Projector",
                description = "",
                catalogueNumber = "2022.1.1",
                physicalDescription = "",
                condition = "",
                restrictions = "",
                source = "Holy Cross of Davao College"
            )

            dbHelper.insertArtifact(
                name = "Artifact 2",
                description = "",
                catalogueNumber = "2022.1.2",
                physicalDescription = "",
                condition = "",
                restrictions = "",
                source = "Holy Cross of Davao College"
            )

            dbHelper.insertArtifact(
                name = "Artifact 3",
                description = "",
                catalogueNumber = "2022.1.3",
                physicalDescription = "",
                condition = "",
                restrictions = "",
                source = "Holy Cross of Davao College"
            )

            dbHelper.insertArtifact(
                name = "Artifact 4",
                description = "",
                catalogueNumber = "2022.1.4",
                physicalDescription = "",
                condition = "",
                restrictions = "",
                source = "Holy Cross of Davao College"
            )

            dbHelper.insertArtifact(
                name = "Artifact 5",
                description = "",
                catalogueNumber = "2022.1.5",
                physicalDescription = "",
                condition = "",
                restrictions = "",
                source = "Holy Cross of Davao College"
            )
        }

        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> loadFragment(DashboardFragment())
                R.id.nav_artifacts -> loadFragment(ArtifactsFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
