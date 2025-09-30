package com.example.arapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Config
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    private var arFragment: ArFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // find the AR fragment from the layout
        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as? ArFragment

        // check AR availability
        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (!availability.isSupported) {
            Toast.makeText(this, "AR not supported on this device.", Toast.LENGTH_LONG).show()
            window.decorView.post { finish() }
            return
        }

        // configure AR session and place object
        arFragment?.arSceneView?.post {
            val session = arFragment?.arSceneView?.session
            if (session != null) {
                val config = Config(session).apply {
                    // ✅ disable HDR light estimation to avoid the crash
                    lightEstimationMode = Config.LightEstimationMode.AMBIENT_INTENSITY
                }
                session.configure(config)
                placeObjectInFront()
            } else {
                Toast.makeText(this, "Failed to start AR session.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun placeObjectInFront() {
        arFragment?.let { fragment ->
            ViewRenderable.builder()
                .setView(this, R.layout.ar_image_with_text)
                .build()
                .thenAccept { renderable ->

                    // artifact data passed from ArtifactDetailActivity
                    val artifactName = intent.getStringExtra("artifact_name") ?: "Artifact"
                    val artifactImageRes = intent.getIntExtra(
                        "artifact_imageRes",
                        R.drawable.default_artifact_image
                    )

                    // position one meter in front of camera
                    val camera = fragment.arSceneView.scene.camera
                    val forward = camera.forward
                    val cameraPos = camera.worldPosition
                    val positionInFront = Vector3.add(cameraPos, forward.scaled(1f))

                    // anchor node for the object
                    val anchorNode = AnchorNode().apply {
                        worldPosition = positionInFront
                        parent = fragment.arSceneView.scene
                    }

                    // create a transformable node as child of anchor
                    val transformableNode = TransformableNode(fragment.transformationSystem).apply {
                        parent = anchorNode
                        this.renderable = renderable
                        select()
                    }

                    // update AR overlay views
                    renderable.view.findViewById<TextView>(R.id.arText).text = artifactName
                    renderable.view.findViewById<ImageView>(R.id.arImage)
                        .setImageResource(artifactImageRes)
                }
                .exceptionally { throwable ->
                    Toast.makeText(
                        this,
                        "Failed to load AR content: ${throwable.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    null
                }
        }
    }

    override fun onResume() {
        super.onResume()
        arFragment?.arSceneView?.resume()
    }

    override fun onPause() {
        super.onPause()
        arFragment?.arSceneView?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        arFragment?.arSceneView?.destroy()
    }
}
