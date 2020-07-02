package com.marik.firebasestoragefirst

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.marik.firebasestoragefirst.fragments.FromGalleryFragment
import com.marik.firebasestoragefirst.fragments.MyPhotosFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Restore state if there is a saved state
//        savedInstanceState?.let { restoreState(it) }

        init()

    }

    private fun init() {
        val adapter = FPAdapter(supportFragmentManager, 1)
        viewpager.adapter = adapter
        viewpager.currentItem = 0

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
    }*/
}

class FPAdapter(fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FromGalleryFragment()
            1 -> MyPhotosFragment()
            else -> FromGalleryFragment()
        }
    }

    override fun getCount(): Int {
        return  2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) "Choose image"
        else "My photos"
    }


}

