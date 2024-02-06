package com.example.convidados2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados2.constants.MyConstants
import com.example.convidados2.model.GuestModel
import com.example.convidados2.databinding.ActivityGuestFormBinding
import com.example.convidados2.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)


        // Click listener do botão, chama o metodo insert
        binding.buttonSave.setOnClickListener{
            val name = binding.editName.text.toString()
            val isPresent = binding.radioPresent.isChecked
            val guest = GuestModel().apply {
                this.id = guestId
                this.name = name
                this.presence = isPresent
            }

            if(guestId == 0 ) {
                viewModel.insert(guest)
            }else{
                viewModel.update(guest)
            }

            finish()
        }
        binding.radioPresent.isChecked = true

        observe()
        loadData()
    }

    private fun observe(){
        //Observer da lista de guests
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if(it.presence){
                binding.radioPresent.isChecked = true
            }else{
                binding.radioPresent.isChecked = true
            }
        })

        viewModel.confirmation.observe(this, Observer{
            if(it != "Falha"){
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle != null){
            guestId = bundle.getInt(MyConstants.KEY.ID_KEY)
            viewModel.get(guestId)
        }
    }

}