package com.example.contact.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.ItemClickListener
import com.example.contact.room.NumberEntity
import kotlinx.android.synthetic.main.item_layout_2.view.*
import java.util.*

class SearchViewHolder(itemView: View, private val itemClickListener: ItemClickListener) :
    RecyclerView.ViewHolder(itemView) {

    fun setData2(numberEntity: NumberEntity) {
        itemView.apply {
            val r = Random()
            val red = r.nextInt(255 - 0 + 1) + 0
            val green = r.nextInt(255 - 0 + 1) + 0
            val blue = r.nextInt(255 - 0 + 1) + 0

            val draw = GradientDrawable()
            draw.shape = GradientDrawable.OVAL
            draw.setColor(Color.rgb(red, green, blue))

            tvName2.text = numberEntity.name
            btnName2.text= numberEntity.name[0].toString().uppercase()
            btnName2.background = draw
            textNumber.text=numberEntity.number1
            tvName2.setOnClickListener {
                itemClickListener.clickListener(numberEntity, adapterPosition)
            }
        }
    }
}
