package com.example.arapp

import android.os.Bundle
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

        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as? ArFragment

        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (!availability.isSupported) {
            Toast.makeText(this, "AR not supported on this device.", Toast.LENGTH_LONG).show()
            // Delay exit to avoid leaked window
            window.decorView.post { finish() }
            return
        }

        arFragment?.arSceneView?.post {
            val session = arFragment?.arSceneView?.session
            if (session != null) {
                val config = Config(session)
                config.lightEstimationMode = Config.LightEstimationMode.AMBIENT_INTENSITY
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
                .setView(this, R.layout.ar_image_with_text) // custom AR layout
                .build()
                .thenAccept { renderable ->
                    val camera = fragment.arSceneView.scene.camera
                    val forward = camera.forward
                    val cameraPosition = camera.worldPosition
                    val positionInFront = Vector3.add(cameraPosition, forward.scaled(1f))

                    val anchorNode = AnchorNode()
                    anchorNode.worldPosition = positionInFront
                    anchorNode.setParent(fragment.arSceneView.scene)

                    val node = TransformableNode(fragment.transformationSystem)
                    node.setParent(anchorNode)
                    node.renderable = renderable
                    node.select()
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
