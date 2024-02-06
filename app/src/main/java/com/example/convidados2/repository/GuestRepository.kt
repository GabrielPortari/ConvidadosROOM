package com.example.convidados2.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados2.constants.MyConstants
import com.example.convidados2.model.GuestModel

/* MANIPULAÇÃO DE DADOS USANDO O GUEST DATABASE */

class GuestRepository(context: Context){

    private val guestDatabase = GuestDatabase.getDatabase(context).guestDAO()


    //Inserir  guest no banco de dados
    fun insert(guest: GuestModel): Boolean{
        return guestDatabase.insert(guest) > 0 //retorna true caso inserido ao menos 1 guest
    }

    //Atualizar guest no banco de dados
    fun update(guest: GuestModel): Boolean{
        return guestDatabase.update(guest) > 0
    }

    //Remover  guest no banco de dados
    fun remove(id: Int){
        val guest = get(id)
        guestDatabase.delete(guest)
    }

    //Recuperar um único guest
    fun get(id: Int): GuestModel{
        return guestDatabase.get(id)
    }

    //Retornar a lista com todos os guests do banco de dados
    fun getAllGuests(): List<GuestModel>{
        return guestDatabase.getAll()
    }

    fun getGuests(presenceType: Boolean): List<GuestModel> {
        val isPresent = if (presenceType) 1 else 0
        return guestDatabase.getGuests(isPresent)
    }
}