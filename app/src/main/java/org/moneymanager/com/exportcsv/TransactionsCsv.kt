package org.moneymanager.com.exportcsv

import com.opencsv.bean.CsvBindByName
import org.moneymanager.com.model.Transaction

data class TransactionsCSV(
    @CsvBindByName(column = "title")
    val title: String,
    @CsvBindByName(column = "amount")
    val amount: Double,
    @CsvBindByName(column = "transaction Type")
    val transactionType: String,
    @CsvBindByName(column = "tag")
    val tag: String,
    @CsvBindByName(column = "transaction date")
    val date: String,
    @CsvBindByName(column = "note")
    val note: String,
    @CsvBindByName(column = "log created At")
    val createdAtDate: String
)

fun List<Transaction>.toCsv() = map {
    TransactionsCSV(
        title = it.title,
        amount = it.amount,
        transactionType = it.transactionType,
        tag = it.tag,
        date = it.date,
        note = it.note,
        createdAtDate = it.createdAtDateFormat,
    )
}
