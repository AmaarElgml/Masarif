package com.elgml.masarif.ui.main.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.elgml.masarif.R
import com.elgml.masarif.pojo.Operation
import com.elgml.masarif.ui.main.home.HomeFragment.Companion.MY_BUDGET
import com.elgml.masarif.ui.main.home.HomeFragment.Companion.REMAINING
import com.elgml.masarif.ui.main.home.HomeFragment.Companion.SAVINGS
import com.elgml.masarif.ui.main.home.HomeFragment.Companion.SHARED_PREFERENCES_VALUES
import com.elgml.masarif.ui.main.home.HomeFragment.Companion.SPENT
import com.elgml.masarif.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class AddOperationSheet(private val v: View?) : BottomSheetDialogFragment(), View.OnClickListener {

    // UI Views
    private lateinit var confirmButton: Button
    private lateinit var discardButton: Button
    private lateinit var typeTextView: TextView
    private lateinit var categoryEditText: TextView
    private lateinit var valueEditText: TextView

    // ViewModel and type
    private lateinit var homeViewModel: HomeViewModel
    private var type: Int? = null

    // Shared Preferences
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_operation_sheet, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        initializer(v)
        getAddType()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.confirmButton) {
            confirmOperation()
        } else if (v?.id == R.id.discardButton) {
            dismiss()
        }
    }

    private fun initializer(v: View) {
        confirmButton = v.findViewById(R.id.confirmButton)
        discardButton = v.findViewById(R.id.discardButton)
        typeTextView = v.findViewById(R.id.typeTextView)
        categoryEditText = v.findViewById(R.id.categoryEditText)
        valueEditText = v.findViewById(R.id.valueEditText)

        homeViewModel = HomeViewModel(activity!!.application)
        confirmButton.setOnClickListener(this)
        discardButton.setOnClickListener(this)

        sharedPreferences =
            context!!.getSharedPreferences(SHARED_PREFERENCES_VALUES, Context.MODE_PRIVATE)

    }

    private fun getAddType() {
        val bundle = arguments
        type = bundle?.getInt("AddType")

        when (type) {
            1 -> {
                typeTextView.text = getString(R.string.add_income)
                categoryEditText.visibility = View.VISIBLE
                categoryEditText.hint = getString(R.string.source)
            }
            2 -> {
                typeTextView.text = getString(R.string.add_expense)
                categoryEditText.visibility = View.VISIBLE
                categoryEditText.hint = getString(R.string.category)
            }
            3 -> {
                typeTextView.text = getString(R.string.add_saving)
                categoryEditText.visibility = View.GONE
            }
        }
    }

    private fun confirmOperation() {
        val valueStr = valueEditText.text.toString()

        if (TextUtils.isEmpty(valueStr)) {
            valueEditText.error = getString(R.string.value_is_required)
        } else {
            val category = categoryEditText.text.toString()
            val value = Integer.parseInt(valueStr + "")
            val c = Calendar.getInstance()
            val operation = Operation(
                type, value,
                category,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            homeViewModel.insertOperation(operation)
                .subscribeOn(Schedulers.computation())
                .subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        setData(value, type)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }

    private fun setData(value: Int, type: Int?) {
        val editor = sharedPreferences.edit()
        when (type) {
            1 -> {    // Add Income
                editor.putInt(MY_BUDGET, sharedPreferences.getInt(MY_BUDGET, 0) + value)
                editor.putInt(REMAINING, sharedPreferences.getInt(REMAINING, 0) + value)
            }
            2 -> {    // Add Expense
                editor.putInt(SPENT, sharedPreferences.getInt(SPENT, 0) + value)
                editor.putInt(REMAINING, sharedPreferences.getInt(REMAINING, 0) - value)
            }
            3 -> {    // Add Savings
                editor.putInt(SAVINGS, sharedPreferences.getInt(SAVINGS, 0) + value)
                editor.putInt(REMAINING, sharedPreferences.getInt(REMAINING, 0) - value)
            }
        }
        editor.apply()

        activity?.runOnUiThread {
            val myBudgetView: TextView = v!!.findViewById(R.id.myBudgetView)
            val spentView: TextView = v.findViewById(R.id.spentView)
            val savedView: TextView = v.findViewById(R.id.savedView)
            val remainingView: TextView = v.findViewById(R.id.remainingView)
            myBudgetView.text = "${sharedPreferences.getInt(MY_BUDGET, 0)} EGP"
            spentView.text = "${sharedPreferences.getInt(SPENT, 0)} EGP"
            savedView.text = "${sharedPreferences.getInt(SAVINGS, 0)} EGP"
            remainingView.text = "${sharedPreferences.getInt(REMAINING, 0)} EGP"
        }
        dismiss()
    }

}