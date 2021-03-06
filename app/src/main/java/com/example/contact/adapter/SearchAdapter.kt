package com.example.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Interface.ItemClickListener
import com.example.contact.R
import com.example.contact.room.ContactRelation

class SearchAdapter(list: List<ContactRelation>, private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<SearchViewHolder>(){

    private var list: List<ContactRelation> = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_2, parent, false)
        return SearchViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val contact = list[position]
        holder.setData2(contact)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}


