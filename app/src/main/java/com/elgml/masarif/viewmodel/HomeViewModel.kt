package com.elgml.masarif.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.elgml.masarif.pojo.Operation
import com.elgml.masarif.repositories.OperationRepository
import io.reactivex.Completable

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var operationRepository: OperationRepository = OperationRepository(application)

    fun insertOperation(operation: Operation): Completable {
        return operationRepository.insertOperation(operation)
    }
}