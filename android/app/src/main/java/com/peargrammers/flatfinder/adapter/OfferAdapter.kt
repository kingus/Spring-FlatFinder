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

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

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
        Log.d("currentOffer", "currentOffer")

        holder.itemView.offerTitleTextView.text = currentOffer.title
        holder.itemView.districtTextView.text = String.format(
            holder.itemView.context.getString(R.string.district),
            currentOffer.district
        )
        holder.itemView.priceTextView.text = String.format(
            holder.itemView.context.getString(R.string.price),
            currentOffer.price.toString()
        )

        Glide.with(holder.itemView.context)
            .load(currentOffer.imgUrl)
            .centerCrop()
            .into(holder.itemView.offerImage);

        holder.itemView.offerLinearLayout.setOnClickListener {
        }

    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}