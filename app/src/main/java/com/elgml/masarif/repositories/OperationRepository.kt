package com.elgml.masarif.repositories

import android.app.Application
import com.elgml.masarif.data.DataBase
import com.elgml.masarif.data.OperationDao
import com.elgml.masarif.pojo.Operation
import io.reactivex.Completable
import io.reactivex.Single

class OperationRepository(application: Application) {

    private var operationDao: OperationDao = DataBase.getInstance(application).operationDao()
    private var allOperations: Single<List<Operation>>
    private var incomesOperations: Single<List<Operation>>
    private var expensesOperations: Single<List<Operation>>
    private var savingsOperations: Single<List<Operation>>

    init {
        allOperations = operationDao.getOperations()
        incomesOperations = operationDao.getIncomesOperations()
        expensesOperations = operationDao.getExpensesOperations()
        savingsOperations = operationDao.getSavingsOperations()
    }

    fun insertOperation(operation: Operation): Completable {
        return operationDao.insertOperation(operation)
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