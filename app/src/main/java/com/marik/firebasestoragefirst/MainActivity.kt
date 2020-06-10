package com.marik.firebasestoragefirst

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.marik.firebasestoragefirst.fragments.FromGalleryFragment
import kotlinx.android.synthetic.main.from_gallery.*


class MainActivity : AppCompatActivity(), FromGalleryFragment.UploadFragmentListener {

    private lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Restore state if there is a saved state
//        savedInstanceState?.let { restoreState(it) }

        init()

    }

    private fun init() {
        // Set initial fragment
        val fromGallery = FromGalleryFragment()
        replaceFragment(fromGallery)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

/*
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        // If there's an upload in progress, save the reference so you can query it later
        mStorageRef.let {
            outState.putString("reference", it.toString())
        }
        image.drawable?.let {
            outState.putParcelable("image", (it.current as BitmapDrawable).bitmap)
        }
    }

    private fun restoreState(savedInstanceState: Bundle) {

        // If there was an upload in progress, get its reference and create a new StorageReference
        val stringRef = savedInstanceState.getString("reference") ?: return

        mStorageRef = Firebase.storage.getReferenceFromUrl(stringRef)
        val bitmap = savedInstanceState.getParcelable<Bitmap>("image")

        //-------------------------------------------------------------------------------------------
        text_placeholder.visibility = View.GONE
        image.setImageBitmap(bitmap)
        image.visibility = View.VISIBLE

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading ...")
        progressDialog.show()
        //------------------------------------------------------------------------------------------------

        // Find all UploadTasks under this StorageReference (in this example, there should be one)

        val tasks = mStorageRef.activeUploadTasks

        if (tasks.size > 0) {
            // Get the task monitoring the upload
            val task = tasks[0]

            // Add new listeners to the task using an Activity scope
            task
                .addOnSuccessListener(this) {
                    // Success!
                    downloadUrl = it.storage.downloadUrl.result!! // storage-y chaloRef-n a
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, "File Uploaded!", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener(this) { exception ->
                    // Handle unsuccessful uploads
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener(this) { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                    progressDialog.setMessage("$progress% uploaded...")
                }
        }
    }
*/

    override fun getFilePath(): Uri {
        return filePath
    }

}

