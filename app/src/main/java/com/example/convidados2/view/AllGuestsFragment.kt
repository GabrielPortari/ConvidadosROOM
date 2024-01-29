package com.example.convidados2.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados2.constants.MyConstants
import com.example.convidados2.databinding.FragmentAllGuestsBinding
import com.example.convidados2.model.GuestModel
import com.example.convidados2.view.adapter.GuestsAdapter
import com.example.convidados2.view.listener.OnGuestListener
import com.example.convidados2.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        viewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        //configuração da recycler view e adapter
        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)
        binding.recyclerGuests.adapter = adapter

        //click listener
        val listener = object : OnGuestListener{
            override fun onClick(id: Int) {
                val intent =  Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(MyConstants.KEY.ID_KEY, id)

                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.remove(id)
                viewModel.getAll()
            }
        }

        adapter.attachListener(listener)

        viewModel.getAll()
        observe()

        return binding.root
    }

    private fun observe(){
        //Observer da lista de guests
        viewModel.listGuests.observe(viewLifecycleOwner){
            adapter.updatedGuests(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }
}