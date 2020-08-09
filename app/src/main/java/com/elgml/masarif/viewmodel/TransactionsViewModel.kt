package com.elgml.masarif.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.elgml.masarif.pojo.Operation
import com.elgml.masarif.repositories.OperationRepository
import io.reactivex.Single

class TransactionsViewModel(application: Application) : AndroidViewModel(application) {

    private var operationRepository: OperationRepository = OperationRepository(application)
    private var allOperations: Single<List<Operation>>
    private var incomesOperations: Single<List<Operation>>
    private var expensesOperations: Single<List<Operation>>
    private var savingsOperations: Single<List<Operation>>

    init {
        allOperations = operationRepository.getOperations()
        incomesOperations = operationRepository.getIncomesOperations()
        expensesOperations = operationRepository.getExpensesOperations()
        savingsOperations = operationRepository.getSavingsOperations()
    }

    fun getOperations(): Single<List<Operation>> {
        return allOperations
    }

    fun getIncomesOperations(): Single<List<Operation>> {
        return incomesOperations
    }

    fun getExpensesOperations(): Single<List<Operation>> {
        return expensesOperations
    }

    fun getSavingsOperations(): Single<List<Operation>> {
        return savingsOperations
    }

}