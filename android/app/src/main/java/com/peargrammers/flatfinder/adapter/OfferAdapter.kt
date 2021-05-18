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
import com.peargrammers.flatfinder.model.Offer
import kotlinx.android.synthetic.main.offer_list_item.view.*


class OfferAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    private val TAG = OfferAdapter::class.qualifiedName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_list_item, parent, false)
        return OfferViewHolder(view)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.offerUrl == newItem.offerUrl
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
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
        Log.d("currentOffer", currentOffer.id.toString())

        holder.itemView.offerTitleTextView.text = currentOffer.title
        holder.itemView.districtTextView.text = String.format(
            holder.itemView.context.getString(R.string.district),
            currentOffer.district
        )
        holder.itemView.priceTextView.text = String.format(
            holder.itemView.context.getString(R.string.price),
            currentOffer.price.toString()
        )

//        holder.itemView.heartImageView.setOnClickListener {
//            holder.itemView.heartImageView.setBackgroundColor(holder.itemView.context.getColor(R.color.red))
//        }

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

            Log.d(TAG, "onClick")
            Log.d("position", position.toString())

            if (position != RecyclerView.NO_POSITION) {
                Log.d("if position", position.toString())
                listener.onItemClick(currentOffer, v)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(offer: Offer?, view: View?)
    }

}