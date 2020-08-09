package com.elgml.masarif.ui.main.home


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.elgml.masarif.R
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener {

    // UI Views
    private lateinit var dayView: TextView
    private lateinit var myBudgetView: TextView
    private lateinit var spentView: TextView
    private lateinit var savedView: TextView
    private lateinit var remainingView: TextView
    private lateinit var addIncomeView: TextView
    private lateinit var addExpensesView: TextView
    private lateinit var addSavingsView: TextView

    // Shared Preferences
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val SHARED_PREFERENCES_VALUES =
            "com.elgml.masarif.ui.main.home.SHARED_PREFERENCES_VALUES"
        const val MY_BUDGET = "com.elgml.masarif.ui.main.home.MY_BUDGET"
        const val SPENT = "com.elgml.masarif.ui.main.home.SPENT"
        const val SAVINGS = "com.elgml.masarif.ui.main.home.SAVINGS"
        const val REMAINING = "com.elgml.masarif.ui.main.home.REMAINING"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        initializer(v)
        setViewsData()
    }

    override fun onClick(v: View?) {
        when {
            v?.id == R.id.addIncomeView -> addOperation(1)
            v?.id == R.id.addExpensesView -> addOperation(2)
            v?.id == R.id.addSavingsView -> addOperation(3)
        }
    }

    private fun initializer(v: View) {
        dayView = v.findViewById(R.id.dayView)
        myBudgetView = v.findViewById(R.id.myBudgetView)
        spentView = v.findViewById(R.id.spentView)
        savedView = v.findViewById(R.id.savedView)
        remainingView = v.findViewById(R.id.remainingView)

        addIncomeView = v.findViewById(R.id.addIncomeView)
        addExpensesView = v.findViewById(R.id.addExpensesView)
        addSavingsView = v.findViewById(R.id.addSavingsView)

        addIncomeView.setOnClickListener(this)
        addExpensesView.setOnClickListener(this)
        addSavingsView.setOnClickListener(this)

        sharedPreferences =
            context!!.getSharedPreferences(SHARED_PREFERENCES_VALUES, Context.MODE_PRIVATE)
    }

    private fun setViewsData() {
        val c: Calendar = Calendar.getInstance()
        val dataStringFormat =
            DateFormat.getDateInstance(DateFormat.SHORT).format(c.time)

        dayView.text = dataStringFormat
        myBudgetView.text = "${sharedPreferences.getInt(MY_BUDGET, 0)} EGP"
        spentView.text = "${sharedPreferences.getInt(SPENT, 0)} EGP"
        savedView.text = "${sharedPreferences.getInt(SAVINGS, 0)} EGP"
        remainingView.text = "${sharedPreferences.getInt(REMAINING, 0)} EGP"
    }

    private fun addOperation(type: Int) {
        val addOperationSheet = AddOperationSheet(view)
        val data = Bundle()
        data.putInt("AddType", type)
        addOperationSheet.arguments = data
        addOperationSheet.show(childFragmentManager, "AddOperationSheet")
    }

}
