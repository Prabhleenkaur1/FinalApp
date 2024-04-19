package com.example.finalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Homepage : AppCompatActivity() {
    private lateinit var buttonViewBucketList: Button
    private lateinit var buttonAddBucketListItem: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        enableEdgeToEdge()

        // Initialize the button
        buttonViewBucketList = findViewById(R.id.buttonViewBucketList)
        buttonAddBucketListItem = findViewById(R.id.buttonAddBucketListItem)

        // Set the click listener to the button
        buttonViewBucketList.setOnClickListener {
            // Navigate to ViewBucketListActivity
            val intent = Intent(this, ViewList::class.java)
            startActivity(intent)
        }
        buttonAddBucketListItem.setOnClickListener {
            // Navigate to LoginActivity
            val intentAdd = Intent(this, AddBucketList::class.java)
            startActivity(intentAdd)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
