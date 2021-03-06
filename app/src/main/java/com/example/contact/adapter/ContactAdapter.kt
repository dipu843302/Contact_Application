package com.example.contact.adapter


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Interface.ItemClickListener
import com.example.contact.R
import com.example.contact.room.ContactRelation
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*


class ContactAdapter(list: List<ContactRelation>, private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var list: List<ContactRelation> = list

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

        @SuppressLint("UseCompatLoadingForDrawables")
        fun setData(contactRelation: ContactRelation) {
            itemView.apply {
                val r = Random()
                val red = r.nextInt(255 - 0 + 1) + 0
                val green = r.nextInt(255 - 0 + 1) + 0
                val blue = r.nextInt(255 - 0 + 1) + 0

                val draw = GradientDrawable()
                draw.shape = GradientDrawable.OVAL
                draw.setColor(Color.rgb(red, green, blue))

                tvName.text = contactRelation.contactEntity?.name

//                //  if(numberEntity.photo.length!=null){
//                    Glide.with(context)
//                        .asBitmap()
//                        .load(numberEntity.photo)
//                        .into(profileImage)
                //profileImage.background =
                 //   resources.getDrawable(resources.getIdentifier("name", "id", numberEntity.photo))
                //  profileImage.background=numberEntity.photo
                //  }else{
                    profileImage.text= contactRelation.contactEntity!!.name[0].toString().uppercase()
                  profileImage.background = draw
                //  }

                tvName.setOnClickListener {
                    itemClickListener.clickListener(contactRelation.contactEntity!!)
                }
            }
        }
    }
}

