package hr.algebra.dnd5e.dao

import android.content.ContentValues
import android.database.Cursor


// selection city = ? and mayor = ?
// selectionArgs [0] = "Zagreb"
// selectionArgs [1] = "Banderas"
interface Repository {
    fun delete(table: String, selection: String?, selectionArgs: Array<String>?): Int

    fun update(
        table: String,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int

    fun query(
        table: String,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor

    fun insert(table: String,values: ContentValues?): Long


}