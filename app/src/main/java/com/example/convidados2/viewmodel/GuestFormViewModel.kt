package com.example.convidados2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.convidados2.model.GuestModel
import com.example.convidados2.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository  = GuestRepository.getInstance(application)

    private val _guest = MutableLiveData<GuestModel>()
    val guest: MutableLiveData<GuestModel> = _guest

    private val _confirmation = MutableLiveData<String>()
    val confirmation: MutableLiveData<String> = _confirmation

    fun insert(guest: GuestModel){
        if(repository.insert(guest)){
            _confirmation.value = "Inserção feita com sucesso"
        }else{
            _confirmation.value = "Falha ao inserir"
        }
    }

    fun update(guest: GuestModel){
        if(repository.update(guest)){
            _confirmation.value = "Atualização feita com sucesso"
        }else{
            _confirmation.value = "Falha ao atualizar"
        }
    }

    fun get(id: Int){
        _guest.value = repository.get(id)
    }

}