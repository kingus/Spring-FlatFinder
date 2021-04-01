package com.peargrammers.flatfinder.adapter

import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.model.Offer
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel
import kotlinx.android.synthetic.main.offer_list_item.view.*

class OfferAdapter(
    private val viewModel: OfferViewModel
) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_list_item, parent, false)
        return OfferViewHolder(view)
    }

    override fun getItemCount(): Int {
        return viewModel.getOffers().size
    }


    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val currentOffer = viewModel.getOffers()[position]
        holder.itemView.offerTitleTextView.text = currentOffer.title
        holder.itemView.districtTextView.text = String.format(
            holder.itemView.context.getString(R.string.district),
            currentOffer.district
        )
        holder.itemView.priceTextView.text = String.format(
            holder.itemView.context.getString(R.string.price),
            currentOffer.price.toString()
        )

        holder.itemView.offerLinearLayout.setOnClickListener {
        }

    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}