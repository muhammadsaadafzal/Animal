package com.example.animal

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddPet : AppCompatActivity() {

//    private lateinit var Binding: AddPetBinding
    
    private val REQUEST_IMAGE_CAPTURE = 1
    private val CAMERA_REQUEST = 1
    private val MY_CAMERA_PERMISSION_CODE = 0
    private lateinit var pet_date: String
    private var imageUri: Uri? = null
    private var imageLink: String? = null
    
    /* private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference */
    private val database = FirebaseDatabase.getInstance().getReference("pet")
    private val storage = FirebaseStorage.getInstance().reference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_pet)
        
        
        val pet_name = findViewById<EditText>(R.id.petName)
        val pet_species = findViewById<EditText>(R.id.species)
        val pet_breed = findViewById<EditText>(R.id.breed)
        
        val galleryBtn = findViewById<Button>(R.id.galleryBtn)
        val captureBtn = findViewById<Button>(R.id.captureBtn)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setBackgroundColor(Color.parseColor("#FF00A5FF"))
        
        
        galleryBtn.setOnClickListener {
            try {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show()
            }
            
        }
        
        /* captureBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                
                
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            } else {
                // take picture
                
                try {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show()
                }
            }
        } */
        
        val date = findViewById<DatePicker>(R.id.date)
        val dcl: DatePicker.OnDateChangedListener =
            DatePicker.OnDateChangedListener { DatePicker, year, month, day ->
                pet_date = "" + day + "-" + month + "-" + year
                /* Toast.makeText(this, "You are changed date is:", Toast.LENGTH_LONG).show() */
            }
        date.setOnDateChangedListener(dcl)
        
        saveBtn.setOnClickListener {
            val image = findViewById<ImageView>(R.id.image)
            val petName = findViewById<EditText>(R.id.petName).text.toString()
            val species = findViewById<EditText>(R.id.species).text.toString()
            val breed = findViewById<EditText>(R.id.breed).text.toString()
            val petType = findViewById<RadioGroup>(R.id.type)
            val cat = findViewById<RadioButton>(R.id.cat)
            val dog = findViewById<RadioButton>(R.id.dog)
            val otherPet = findViewById<RadioButton>(R.id.other)
            
            
            var pet_type: String = ""
            
            if (cat.isChecked) {
                pet_type = pet_type + cat.text
            } else if (dog.isChecked) {
                pet_type = pet_type + dog.text
            } else if (otherPet.isChecked) {
                pet_type = pet_type + otherPet.text
            }
            if (imageUri.toString()
                    .isNotEmpty() && petName.isNotEmpty() && species.isNotEmpty() && breed
                    .isNotEmpty() &&
                pet_type
                    .isNotEmpty() && date.isNotEmpty()
            ) {
                var timeStamp = System.currentTimeMillis()
                val imageRef = storage.child("images").child(timeStamp.toString())
                val file = imageUri?.let { imageRef.putFile(it) }
                file?.addOnSuccessListener { taskSnapshot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        imageLink = uri.toString()
                    }.addOnSuccessListener {
                        val pets = Pet(
                            image = imageLink,
                            name = petName, specie = species, breed = breed, type =
                            pet_type,
                            date
                            = pet_date,
                            time = timeStamp
                        )
                        val newRecord = database.push()
                        newRecord.setValue(pets).addOnSuccessListener {
                            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                    /* Glide.with(this)
                        .load(imageRef)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                        .into(image) */
                }?.addOnFailureListener { exception ->
                    Toast.makeText(this, "Error occurred while uploading image", Toast.LENGTH_SHORT)
                        .show()
                    
                }
                
                
            } else {
                Toast.makeText(this, "Please fill all credentials!", Toast.LENGTH_SHORT).show()
            }
            
            
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        when (requestCode) {
            MY_CAMERA_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0]
                            == PackageManager.PERMISSION_GRANTED)
                ) {
                    // continue to take the picture
                } else {
                    // do something with condition permission not given
                }
                return
            }
            
            else -> {
            }
        }
    }
    
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA
        )
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val image = findViewById<ImageView>(R.id.image)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            imageUri = data?.data!!
            image.setImageURI(Uri.parse(imageUri.toString()));
        }
        
    }
}