package com.example.contact


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.room.Contact
import kotlinx.android.synthetic.main.item_layout.view.*

class ContactAdapter(list: List<Contact>, private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var list: List<Contact> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = list[position]
        holder.setData(contact)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, private val itemClickListener: ItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        fun setData(contact: Contact) {
            itemView.apply {
                tvName.text = contact.name
                btnName.text=contact.name.get(0).toString()
                tvName.setOnClickListener {
                    itemClickListener.clickListener(contact, adapterPosition)
                }
            }
        }
    }
}

