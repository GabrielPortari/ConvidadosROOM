package com.example.convidados2.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados2.constants.MyConstants
import com.example.convidados2.model.GuestModel

/* MANIPULAÇÃO DE DADOS USANDO O GUEST DATABASE */

class GuestRepository private constructor(context: Context){

    private val guestDatabase = GuestDatabase(context)

    //Singleton
    companion object{
        private lateinit var repository: GuestRepository
        fun getInstance(context: Context): GuestRepository {
            if(!Companion::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    //Inserir  guest no banco de dados
    fun insert(guest: GuestModel): Boolean{
        return try {
            val db = guestDatabase.writableDatabase

            val name = guest.name
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(MyConstants.DATABASE.COLUMNS.NAME, name)
            values.put(MyConstants.DATABASE.COLUMNS.PRESENCE, presence)

            db.insert(MyConstants.DATABASE.TABLE_NAME, null, values)
            true
        }catch (e: Exception){
            false
        }
    }

    //Atualizar guest no banco de dados
    fun update(guest: GuestModel): Boolean{
        return try{
            val db = guestDatabase.writableDatabase

            val name = guest.name
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(MyConstants.DATABASE.COLUMNS.NAME, name)
            values.put(MyConstants.DATABASE.COLUMNS.PRESENCE, presence)

            val selection = MyConstants.DATABASE.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())
            db.update(MyConstants.DATABASE.TABLE_NAME, values, selection, args)

            true
        }catch(e: Exception){
            false
        }
    }

    //Remover  guest no banco de dados
    fun remove(id: Int): Boolean{
        return try {
            val db = guestDatabase.writableDatabase

            val selection = MyConstants.DATABASE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(MyConstants.DATABASE.TABLE_NAME, selection, args)
            true
        }catch (e: Exception){
            false
        }
    }

    //Recuperar um único guest
    fun get(id: Int): GuestModel?{
        var guest: GuestModel? = null

        try {
            val db = guestDatabase.readableDatabase

            // projeção das colunas que serão recuperadas
            val projection = arrayOf(
                MyConstants.DATABASE.COLUMNS.ID,
                MyConstants.DATABASE.COLUMNS.NAME,
                MyConstants.DATABASE.COLUMNS.PRESENCE
            )

            val selection = MyConstants.DATABASE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                MyConstants.DATABASE.TABLE_NAME, projection,
                selection, args, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.COLUMNS.PRESENCE))

                    guest = GuestModel(id, name, presence == 1)
                }
            }

            cursor.close()
        }catch (e : Exception){
            return guest
        }
        return guest
    }

    //Retornar a lista com todos os guests do banco de dados
    fun getAllGuests(): List<GuestModel>{
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase

            // projeção das colunas que serão recuperadas
            val projection = arrayOf(
                MyConstants.DATABASE.COLUMNS.ID,
                MyConstants.DATABASE.COLUMNS.NAME,
                MyConstants.DATABASE.COLUMNS.PRESENCE
            )
            val cursor = db.query(
                MyConstants.DATABASE.TABLE_NAME, projection,
                null, null, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.COLUMNS.PRESENCE))

                    val guest = GuestModel(id, name, presence == 1)
                    guestList.add(guest)
                }
            }

            cursor.close()
        }catch (e : Exception){
            return guestList
        }
        return guestList
    }

    fun getGuests(presenceType: Boolean): List<GuestModel>{
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase

            // projeção das colunas que serão recuperadas
            val projection = arrayOf(
                MyConstants.DATABASE.COLUMNS.ID,
                MyConstants.DATABASE.COLUMNS.NAME,
                MyConstants.DATABASE.COLUMNS.PRESENCE
            )

            val isPresent = if(presenceType) "1" else "0"
            val selection = MyConstants.DATABASE.COLUMNS.PRESENCE + " = ?"
            val args = arrayOf(isPresent)

            //val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)
            val cursor = db.query(
                MyConstants.DATABASE.TABLE_NAME, projection,
                selection, args, null, null, null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(MyConstants.DATABASE.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(MyConstants.DATABASE.COLUMNS.PRESENCE))

                    val guest = GuestModel(id, name, presence == 1)
                    guestList.add(guest)
                }
            }

            cursor.close()
        }catch (e : Exception){
            return guestList
        }
        return guestList
    }
}