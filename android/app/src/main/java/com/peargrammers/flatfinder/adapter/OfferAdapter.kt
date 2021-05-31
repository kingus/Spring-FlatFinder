package com.peargrammers.flatfinder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.databinding.OfferListItemBinding
import com.peargrammers.flatfinder.model.UserOffer
import kotlin.math.roundToInt

class OfferAdapter(
    var items: List<UserOffer>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    private val TAG = OfferAdapter::class.qualifiedName
    private var _binding: OfferListItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        _binding = OfferListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val currentOffer = items[position]

        binding.offerTitleTextView.text = currentOffer.title

        binding.districtTextView.text = String.format(
            holder.itemView.context.getString(R.string.district_param),
            currentOffer.district.capitalize()
        )

        Log.d("DISTRICT ", currentOffer.district)

        binding.priceTextView.text = String.format(
            holder.itemView.context.getString(R.string.price),
            currentOffer.price.roundToInt()
        )

        when {
            currentOffer.isFavourite -> binding.heartImageView.setColorFilter(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )
            else -> binding.heartImageView.setColorFilter(holder.itemView.context.getColor(R.color.grey))
        }

        when {
            currentOffer.isEmailSent -> binding.emailImageView.setColorFilter(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )
            else -> binding.emailImageView.setColorFilter(holder.itemView.context.getColor(R.color.grey))
        }

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
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {

            val position = adapterPosition
            val currentOffer = items[position]

            when (v.id) {

                R.id.emailImageView -> {
                    Log.d("emailImageView onClick", currentOffer.title)
                    currentOffer.isEmailSent = !currentOffer.isEmailSent
                    notifyDataSetChanged()
                }

                R.id.heartImageView -> {
                    Log.d(TAG, "heartImageView onClick")
                    currentOffer.isFavourite = !currentOffer.isFavourite
                    notifyDataSetChanged()
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
