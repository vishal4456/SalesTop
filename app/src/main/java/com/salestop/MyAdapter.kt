package com.salestop

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salestop.databinding.UserItemBinding

class MyAdapter( private val userList: ArrayList<Friend>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder)
        {
            with(userList[position])
            {
                binding.txFirst.text = firstName
                binding.txLast.text = lastName


            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}