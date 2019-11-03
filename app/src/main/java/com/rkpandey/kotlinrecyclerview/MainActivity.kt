package com.rkpandey.kotlinrecyclerview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")

        val contacts = mutableListOf<Contact>()
        val contactAdapter = ContactAdapter(this, contacts)
        rvContacts.adapter = contactAdapter
        rvContacts.layoutManager = LinearLayoutManager(this)

        val model = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        model.getContacts().observe(this, Observer { contactsSnapshot ->
            // Update the UI
            Log.i(TAG, "Received contacts from view model")
            contacts.clear()
            contacts.addAll(contactsSnapshot)
            contactAdapter.notifyDataSetChanged()
        })
        model.getIsRefreshing().observe(this, Observer { isRefreshing ->
            Log.i(TAG, "Received isRefreshing from view model")
            swipeContainer.isRefreshing = isRefreshing
        })

        swipeContainer.setOnRefreshListener {
            // Show the refreshing UI and fetch new data
            model.fetchNewContact()
        }
    }
}
