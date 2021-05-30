package com.peargrammers.flatfinder.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.dao.UserOffersRequest
import com.peargrammers.flatfinder.model.UserOffer
import com.peargrammers.flatfinder.ui.activity.HomeActivity
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModel

class ChangeNoteDialog(private val offer: UserOffer, private val token : String) : DialogFragment() {
    lateinit var offerViewModel: OfferViewModel
    var note: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        offerViewModel = (activity as HomeActivity).offerViewModel

        activity?.let {
            val dialog = MaterialAlertDialogBuilder(it, R.style.MyThemeOverlayAlertDialog)

                .setView(R.layout.offer_note_dialog)
                .setPositiveButton(R.string.save_button) { _, _ ->
                    Log.d("TOKEN ", token)
                    offer.note = note
                    offerViewModel.updateOffer(offer)
                    offerViewModel.updateUserOffers(token, offer.id, UserOffersRequest(note))
                }
                .setNegativeButton(R.string.cancel_button) { _, _ -> }
                .show()

            dialog.findViewById<TextView>(R.id.offerTitleTextView)?.text = offer.title
            dialog.findViewById<EditText>(R.id.noteEditText)?.setText(offer.note)
            dialog.findViewById<EditText>(R.id.noteEditText)?.addTextChangedListener { it ->
                note = it.toString()
            }

            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}