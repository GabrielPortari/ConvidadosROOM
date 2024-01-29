package com.example.convidados2.constants

class MyConstants private constructor(){

     object DATABASE{
        const val TABLE_NAME = "Guest"
         object COLUMNS{
             const val ID = "id"
             const val NAME = "name"
             const val PRESENCE = "presence"
         }
    }

    object KEY{
        const val ID_KEY = "guestid"
    }

    object PRESENCE{
        const val PRESENT = true
        const val ABSENT = false
    }
}