package com.elgml.masarif.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elgml.masarif.R
import com.elgml.masarif.pojo.Operation
import kotlinx.android.synthetic.main.operation_item.view.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class OperationAdapter : RecyclerView.Adapter<OperationAdapter.ViewHolder>() {
    private var operationsList: List<Operation> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.operation_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(operationsList[position])
    }

    override fun getItemCount(): Int {
        return operationsList.size
    }

    fun setList(list: List<Operation>) {
        this.operationsList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val valueView = itemView.valueView
        private val categoryView = itemView.categoryView
        private val timeView = itemView.timeView
        private val operationView = itemView.operationView

        fun bindViews(operation: Operation) {
            when (operation.operation) {
                1 -> {
                    operationView.text = itemView.context.getString(R.string.income)
                    operationView.setBackgroundResource(R.drawable.income_background)
                    categoryView.visibility = View.VISIBLE
                }
                2 -> {
                    operationView.text = itemView.context.getString(R.string.expenses)
                    operationView.setBackgroundResource(R.drawable.expense_background)
                    categoryView.visibility = View.VISIBLE
                }
                3 -> {
                    operationView.text = itemView.context.getString(R.string.savings)
                    operationView.setBackgroundResource(R.drawable.saving_background)
                    categoryView.visibility = View.GONE
                }
            }

            valueView.text = "${operation.value} EGP"
            categoryView.text = operation.category

            val c: Calendar = Calendar.getInstance()
            c.set(Calendar.YEAR, operation.year)
            c.set(Calendar.MONTH, operation.month)
            c.set(Calendar.DAY_OF_MONTH, operation.day)

            val dataStringFormat =
                DateFormat.getDateInstance(DateFormat.SHORT).format(c.time)
            timeView.text = dataStringFormat
        }
    }
}