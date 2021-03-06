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
import com.peargrammers.flatfinder.model.UserOffer

class UserOfferAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UserOfferAdapter.OfferViewHolder>() {
    private val TAG = UserOfferAdapter::class.qualifiedName

    private var _binding: OfferListItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserOfferAdapter.OfferViewHolder {

        _binding = OfferListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OfferViewHolder(binding.root)
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
        differ.currentList.forEach {
            Log.d("item", it.title)

        }
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.setIsRecyclable(false);

        val currentOffer = differ.currentList[position]
        Log.d(
            "currentOffer",
            currentOffer.id.toString() + "isFav " + currentOffer.isFavourite.toString()
        )

        binding.offerTitleTextView.text = currentOffer.title
        binding.districtTextView.text = String.format(
            holder.itemView.context.getString(R.string.district),
            currentOffer.district
        )
        binding.priceTextView.text = String.format(
            holder.itemView.context.getString(R.string.price),
            currentOffer.price.toString()
        )

        when {
            currentOffer.isFavourite -> binding.heartImageView.setColorFilter(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )

            else -> binding.heartImageView.setColorFilter(holder.itemView.context.getColor(R.color.grey))
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

        override fun onClick(v: View?) {
            val position = adapterPosition
            val currentOffer = differ.currentList[position]

            when (v?.id) {

                R.id.emailImageView -> {
                    binding.emailImageView.setColorFilter(
                        itemView.context.getColor(
                            R.color.red
                        )
                    )
                }

                R.id.heartImageView -> {
                    currentOffer.isFavourite = !currentOffer.isFavourite
                    Log.d(TAG, "heartImageView onClick")
                    when {
                        currentOffer.isFavourite -> binding.heartImageView.setColorFilter(
                            itemView.context.getColor(
                                R.color.red
                            )
                        )

                        else -> binding.heartImageView.setColorFilter(itemView.context.getColor(R.color.grey))
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