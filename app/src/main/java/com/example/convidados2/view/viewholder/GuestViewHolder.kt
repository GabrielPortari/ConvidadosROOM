package com.example.convidados2.view.viewholder

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados2.databinding.RowGuestBinding
import com.example.convidados2.model.GuestModel
import com.example.convidados2.view.listener.OnGuestListener

class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) : RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel){
        bind.textGuestName.text = guest.name

        bind.textGuestName.setOnClickListener{
            listener.onClick(guest.id)
        }

        bind.textGuestName.setOnLongClickListener{

            AlertDialog.Builder(itemView.context)
                .setTitle("Remover")
                .setMessage("Realmente deseja remover este convidado?")
                .setPositiveButton("Sim") { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNegativeButton("Não", null)
                .create().show()


            true
        }


    }

}