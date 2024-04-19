package com.example.finalapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddBucketList : AppCompatActivity() {

    private lateinit var editTextDestinationName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var spinnerRanking: Spinner
    private lateinit var buttonAddDestination: Button
    private val firestoreDb = FirebaseFirestore.getInstance() // Firestore instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bucket_list2)

        // Initialize the views
        editTextDestinationName = findViewById(R.id.editTextDestinationName)
        editTextDescription = findViewById(R.id.editTextDescription)
        spinnerRanking = findViewById(R.id.spinnerRanking)
        buttonAddDestination = findViewById(R.id.buttonAddDestination)

        // Setup spinner for ranking
        ArrayAdapter.createFromResource(
            this,
            R.array.ranking_array, // The ranking array needs to be defined in 'res/values/strings.xml'
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRanking.adapter = adapter
        }

        buttonAddDestination.setOnClickListener {
            val destinationName = editTextDestinationName.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            val ranking = spinnerRanking.selectedItem.toString()

            // Check if all fields are populated
            if (destinationName.isEmpty() || description.isEmpty() || ranking == "Select Ranking") {
                Toast.makeText(this, "All fields need to be populated", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Save data to Firestore
            val bucketListItem = hashMapOf(
                "destinationName" to destinationName,
                "description" to description,
                "ranking" to ranking
            )

            firestoreDb.collection("data")
                .add(bucketListItem)
                .addOnSuccessListener {
                    Toast.makeText(this, "Item added successfully", Toast.LENGTH_LONG).show()
                    // Navigate back to the HomePageActivity
                    startActivity(Intent(this, Homepage::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add item", Toast.LENGTH_LONG).show()
                }
        }
    }
}
