package com.elgml.masarif.ui.main.transactions


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.elgml.masarif.R
import com.elgml.masarif.adapters.OperationAdapter
import com.elgml.masarif.pojo.Operation
import com.elgml.masarif.viewmodel.TransactionsViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 */
class TransactionsFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: TransactionsViewModel
    private lateinit var operationAdapter: OperationAdapter

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var allOperationsView: TextView
    private lateinit var incomeOperationsView: TextView
    private lateinit var expenseOperationsView: TextView
    private lateinit var savingOperationsView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        initializer(v)
        initRecyclerView()
        getOperations()
    }

    override fun onClick(v: View?) {
        when {
            v?.id == R.id.allOperationsView -> getOperations()
            v?.id == R.id.incomeOperationsView -> getIncomesOperations()
            v?.id == R.id.expenseOperationsView -> getExpensesOperations()
            v?.id == R.id.savingOperationsView -> getSavingsOperations()
        }
    }

    private fun initializer(v: View) {
        historyRecyclerView = v.findViewById(R.id.historyRecyclerView)
        allOperationsView = v.findViewById(R.id.allOperationsView)
        incomeOperationsView = v.findViewById(R.id.incomeOperationsView)
        expenseOperationsView = v.findViewById(R.id.expenseOperationsView)
        savingOperationsView = v.findViewById(R.id.savingOperationsView)

        viewModel = TransactionsViewModel(activity!!.application)
        operationAdapter = OperationAdapter()

        allOperationsView.setOnClickListener(this)
        incomeOperationsView.setOnClickListener(this)
        expenseOperationsView.setOnClickListener(this)
        savingOperationsView.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        historyRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = operationAdapter
        }
    }

    private fun getOperations() {
        viewModel.getOperations()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Operation>> {
                override fun onSuccess(list: List<Operation>) {
                    operationAdapter.setList(list)
                    operationAdapter.notifyDataSetChanged()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getIncomesOperations() {
        viewModel.getIncomesOperations()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Operation>> {
                override fun onSuccess(list: List<Operation>) {
                    operationAdapter.setList(list)
                    operationAdapter.notifyDataSetChanged()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun getExpensesOperations() {
        viewModel.getExpensesOperations()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Operation>> {
                override fun onSuccess(list: List<Operation>) {
                    operationAdapter.setList(list)
                    operationAdapter.notifyDataSetChanged()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun getSavingsOperations() {
        viewModel.getSavingsOperations()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Operation>> {
                override fun onSuccess(list: List<Operation>) {
                    operationAdapter.setList(list)
                    operationAdapter.notifyDataSetChanged()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

            })
    }

}
