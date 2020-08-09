package com.elgml.masarif.data

import androidx.room.*
import com.elgml.masarif.pojo.Operation
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface OperationDao {

    @Insert
    fun insertOperation(operation: Operation): Completable

    @Query("SELECT * FROM table_operation_name")
    fun getOperations(): Single<List<Operation>>

    @Query("SELECT * FROM table_operation_name WHERE operation LIKE 1")
    fun getIncomesOperations(): Single<List<Operation>>


    @Query("SELECT * FROM table_operation_name WHERE operation LIKE 2")
    fun getExpensesOperations(): Single<List<Operation>>


    @Query("SELECT * FROM table_operation_name WHERE operation LIKE 3")
    fun getSavingsOperations(): Single<List<Operation>>

}