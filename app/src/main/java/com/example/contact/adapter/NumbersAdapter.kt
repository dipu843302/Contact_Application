package com.example.contact.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Interface.CallClickListener
import com.example.contact.R
import com.example.contact.room.NumberEntity
import kotlinx.android.synthetic.main.number_layout.view.*

class NumbersAdapter(
    val mutableList: MutableList<NumberEntity>,
    val callClickListener: CallClickListener
) :
    RecyclerView.Adapter<NumbersAdapter.NumbersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.number_layout, parent, false)
        return NumbersViewHolder(view, callClickListener)
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        val numbers = mutableList[position]
        holder.setNumbers(numbers)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    class NumbersViewHolder(itemView: View, val callClickListener: CallClickListener) :
        RecyclerView.ViewHolder(itemView) {
        fun setNumbers(numberEntity: NumberEntity) {
            itemView.apply {
                tvNumber3.text = numberEntity.number1

                call3.setOnClickListener {
                    callClickListener.ClickForCall(numberEntity)
                }
                tvNumber3.setOnClickListener {
                    callClickListener.ClickForCall(numberEntity)
                }
                message_send.setOnClickListener {
                    callClickListener.sendMessage(numberEntity)
                }

            }
        }
    }
}