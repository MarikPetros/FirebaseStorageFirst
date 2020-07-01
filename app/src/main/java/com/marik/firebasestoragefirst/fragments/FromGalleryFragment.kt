package com.marik.firebasestoragefirst.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.marik.firebasestoragefirst.GlideApp
import com.marik.firebasestoragefirst.R
import kotlinx.android.synthetic.main.from_gallery.*

class FromGalleryFragment : Fragment() {
    private lateinit var filePath: Uri
    private lateinit var mStorageRef: StorageReference
    private lateinit var downloadUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.from_gallery, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize mStorageRef
        mStorageRef = Firebase.storage.reference


        // Set listeners
        button_choose.setOnClickListener {
            // open file chooser
            showFileChooser()
        }
        button_upload.setOnClickListener {
            // upload file to firebase storage
            if (image.drawable == null) {
                Toast.makeText(
                    this@FromGalleryFragment.context,
                    "Photo does not chosen yet!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            uploadFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null && data.data != null) {
            // Get file path
            filePath = data.data!!

            text_placeholder.visibility = View.GONE

        }
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun uploadFile() {
        val chaloRef: StorageReference = mStorageRef.child("images/winter.jpg")

        filePath.let {

            val urlTask = chaloRef.putFile(it)
                .addOnSuccessListener(this.requireActivity()) { _ ->
                    progress_horizontal.visibility = View.GONE
                    text_progress.visibility = View.GONE
                    //  set image from storage
                    image.visibility = View.VISIBLE
                    GlideApp.with(this)
                        .load(chaloRef)
                        .fitCenter()
                        .into(image)

                    Toast.makeText(this.context, "File Uploaded!", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener(this.requireActivity()) { exception ->
                    // Handle unsuccessful uploads
                    progress_horizontal.visibility = View.GONE
                    text_progress.visibility = View.GONE
                    Toast.makeText(this.context, exception.message, Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener(this.requireActivity()) { taskSnapshot ->
//                            val sessionUri: Uri = taskSnapshot.uploadSessionUri!!
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                    progress_horizontal.setProgress(progress.toInt(), true) // setting progress
                    progress_horizontal.visibility = View.VISIBLE
                    text_progress.text =
                        "${progress.toInt()}% uploaded..."  // informing user about progress
                    text_progress.visibility = View.VISIBLE
                }.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    chaloRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        downloadUri = task.result!!
                        // todo save uri if needed
                    } else {
                        // Handle failures
                        Toast.makeText(this.context, "upload failed!", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST: Int = 145
    }
}