package com.mapbox.maps.testapp.examples

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.PRIMARY_KEY
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

const val TEST_TABLE_NAME = "TEST_TABLE"
const val COLUMN_1 = "column_1"

class DatabaseHelper(context: Context) : ManagedSQLiteOpenHelper(context, "test_database", null, 1) {
  companion object {
    private var instance: DatabaseHelper? = null

    @Synchronized
    fun getInstance(ctx: Context): DatabaseHelper {
      if (instance == null) {
        instance = DatabaseHelper(ctx)
      }
      return instance!!
    }
  }

  override fun onCreate(database: SQLiteDatabase?) {
    database!!.createTable(
      TEST_TABLE_NAME,
      true,
      COLUMN_1 to TEXT + PRIMARY_KEY
    )
  }

  override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    // No upgrades
  }

}

// Access property for Context
val Context.dbHelper: DatabaseHelper
  get() = DatabaseHelper.getInstance(applicationContext)