package com.example.convidados2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados2.constants.MyConstants
import com.example.convidados2.model.GuestModel
import com.example.convidados2.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GuestRepository(application)

    private val _listGuests = MutableLiveData<List<GuestModel>>()
    val listGuests: MutableLiveData<List<GuestModel>> = _listGuests

    fun getAll(){
        _listGuests.value = repository.getAllGuests()
    }

    fun getPresent(){
        _listGuests.value = repository.getGuests(MyConstants.PRESENCE.ABSENT)
    }

    fun getAbsent(){
        _listGuests.value = repository.getGuests(MyConstants.PRESENCE.PRESENT)
    }

    fun remove(id: Int){
        repository.remove(id)
    }

}