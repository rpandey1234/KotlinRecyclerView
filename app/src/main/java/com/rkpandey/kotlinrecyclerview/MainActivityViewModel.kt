package com.rkpandey.kotlinrecyclerview

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "MainActivityViewModel"

class MainActivityViewModel : ViewModel() {
    private val contactsLiveData : MutableLiveData<MutableList<Contact>>
    private val isRefreshingLiveData : MutableLiveData<Boolean>

    init {
        Log.i(TAG, "init")
        contactsLiveData = MutableLiveData()
        contactsLiveData.value = createContacts()

        isRefreshingLiveData = MutableLiveData()
        isRefreshingLiveData.value = false
    }

    fun getContacts() : LiveData<MutableList<Contact>> {
        return contactsLiveData
//        return MutableLiveData<List<Contact>>(contactsLiveData.value)
    }

    fun getIsRefreshing() : LiveData<Boolean> {
        return isRefreshingLiveData
    }

    private fun createContacts(): MutableList<Contact> {
        Log.i(TAG, "createContacts")
        val contacts = mutableListOf<Contact>()
        for (i in 1..150) contacts.add(Contact("Person #$i", i))
        return contacts
    }

    fun fetchNewContact() {
        Log.i(TAG, "fetchNewContact")
        isRefreshingLiveData.value = true
        Handler().postDelayed({
            val currentContacts = contactsLiveData.value
            currentContacts?.add(0, Contact("Julius Campbell", 52))
            contactsLiveData.value = currentContacts
            isRefreshingLiveData.value = false
        }, 2_000)

    }

}