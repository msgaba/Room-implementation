package com.example.notes.screens.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.notes.databinding.CustomDialogBinding
import com.example.notes.models.Dialog
import com.example.notes.util.KEY_DIALOG_OBJECT
import com.example.notes.util.visibleOnCondition
import com.google.gson.Gson

/**
 * Created by Ankita
 */
class CustomDialog : DialogFragment() {

    private lateinit var binding: CustomDialogBinding

    var dialogObj = Dialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(KEY_DIALOG_OBJECT))
                dialogObj = Gson().fromJson(it.getString(KEY_DIALOG_OBJECT), Dialog::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomDialogBinding.inflate(layoutInflater)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        initView()
        viewClicks()
        return binding.root
    }

    private fun initView() {
        binding.view.apply {
            title.text = dialogObj.title
            button1.visibleOnCondition(dialogObj.isButton1Visible)
            button1.text = dialogObj.button1Title
            button2.visibleOnCondition(dialogObj.isButton2Visible)
            button2.text = dialogObj.button2Title
            editTitle.visibleOnCondition(dialogObj.isEditViewVisible)
            editTitle.setText(dialogObj.editViewText)
        }
    }

    private fun viewClicks() {
        binding.view.apply {
            button1.setOnClickListener {
                dialogActionListener.onButton1Click(
                    if (dialogObj.isEditViewVisible) editTitle.text.toString()
                    else null
                )
                dismiss()
            }
            button2.setOnClickListener {
                dialogActionListener.onButton2Click(null)
                dismiss()
            }
        }
    }

    companion object {

        lateinit var dialogActionListener: DialogActionListener

        fun showDialog(
            activity: FragmentActivity,
            obj: Dialog,
            listener: DialogActionListener
        ): CustomDialog {
            dialogActionListener = listener
            val fragment = CustomDialog()
            val args = Bundle()
            args.putString(KEY_DIALOG_OBJECT, Gson().toJson(obj))
            fragment.arguments = args
            fragment.show(activity.supportFragmentManager, CustomDialog::class.java.name)
            return fragment
        }
    }
}
