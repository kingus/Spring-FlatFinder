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
import com.peargrammers.flatfinder.databinding.OfferListItemBinding
import com.peargrammers.flatfinder.model.Offer


class OfferAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    private val TAG = OfferAdapter::class.qualifiedName

    private var _binding: OfferListItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {

        _binding = OfferListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OfferViewHolder(binding.root)
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

        binding.offerTitleTextView.text = currentOffer.title
        binding.districtTextView.text = String.format(
            holder.itemView.context.getString(R.string.district),
            currentOffer.district
        )
        binding.priceTextView.text = String.format(
            holder.itemView.context.getString(R.string.price),
            currentOffer.price.toString()
        )

        Glide.with(holder.itemView.context)
            .load(currentOffer.imgUrl)
            .centerCrop()
            .into(binding.offerImage)

    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            binding.emailImageView.setOnClickListener(this)
            binding.heartImageView.setOnClickListener(this)
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