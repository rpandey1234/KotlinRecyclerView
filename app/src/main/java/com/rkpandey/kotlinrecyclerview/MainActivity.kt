package com.rkpandey.kotlinrecyclerview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")

        val contactList = mutableListOf<Contact>()
        val adapter = ContactAdapter(this, contactList)
        rvContacts.adapter = adapter
        rvContacts.layoutManager = LinearLayoutManager(this)

        val model = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        model.getContacts().observe(this, Observer { contacts ->
            // Update the UI with the new contacts
            contactList.clear()
            contactList.addAll(contacts)
            adapter.notifyDataSetChanged()
        })

        model.getIsRefreshing().observe(this, Observer { isRefreshing ->
            swipeContainer.isRefreshing = isRefreshing
        })

        swipeContainer.setOnRefreshListener {
            model.fetchNewContact()
        }
    }
}
