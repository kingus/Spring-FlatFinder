package com.peargrammers.flatfinder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.model.UserOffer
import kotlinx.android.synthetic.main.offer_list_item.view.*

class UserOfferAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UserOfferAdapter.OfferViewHolder>() {
    private val TAG = UserOfferAdapter::class.qualifiedName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_list_item, parent, false)
        return OfferViewHolder(view)
    }

    private val differCallback = object : DiffUtil.ItemCallback<UserOffer>() {
        override fun areItemsTheSame(oldItem: UserOffer, newItem: UserOffer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserOffer, newItem: UserOffer): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        Log.d("getItemCount", differ.currentList.size.toString())
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val currentOffer = differ.currentList[position]
        Log.d(
            "currentOffer",
            currentOffer.id.toString() + "isFav " + currentOffer.isFavourite.toString()
        )

        holder.itemView.offerTitleTextView.text = currentOffer.title
        holder.itemView.districtTextView.text = String.format(
            holder.itemView.context.getString(R.string.district),
            currentOffer.district
        )
        holder.itemView.priceTextView.text = String.format(
            holder.itemView.context.getString(R.string.price),
            currentOffer.price.toString()
        )

        when {
            currentOffer.isFavourite -> holder.itemView.heartImageView.setColorFilter(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )

            else -> holder.itemView.heartImageView.setColorFilter(holder.itemView.context.getColor(R.color.grey))
        }

        Glide.with(holder.itemView.context)
            .load(currentOffer.imgUrl)
            .centerCrop()
            .into(holder.itemView.offerImage)

    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.emailImageView.setOnClickListener(this)
            itemView.heartImageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val currentOffer = differ.currentList[position]

            when (v?.id) {

                R.id.emailImageView -> {
                    v.emailImageView.setColorFilter(
                        itemView.context.getColor(
                            R.color.red
                        )
                    )
                }

                R.id.heartImageView -> {
                    currentOffer.isFavourite = !currentOffer.isFavourite
                    Log.d(TAG, "heartImageView onClick")
                    when {
                        currentOffer.isFavourite -> v.heartImageView.setColorFilter(
                            itemView.context.getColor(
                                R.color.red
                            )
                        )

                        else -> itemView.heartImageView.setColorFilter(itemView.context.getColor(R.color.grey))
                    }
                }
            }

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(currentOffer, v)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(offer: UserOffer, view: View?)
    }

}