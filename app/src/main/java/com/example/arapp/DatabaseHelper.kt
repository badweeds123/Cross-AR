package com.example.arapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "arapp.db"
        private const val DATABASE_VERSION = 3

        private const val TABLE_USERS = "users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        private const val TABLE_ARTIFACTS = "artifacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_CATALOGUE = "catalogue_number"
        private const val COLUMN_PHYSICAL_DESC = "physical_description"
        private const val COLUMN_CONDITION = "condition"
        private const val COLUMN_RESTRICTIONS = "restrictions"
        private const val COLUMN_SOURCE = "source"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT UNIQUE NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        val createArtifactsTable = """
            CREATE TABLE $TABLE_ARTIFACTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_CATALOGUE TEXT NOT NULL,
                $COLUMN_PHYSICAL_DESC TEXT NOT NULL,
                $COLUMN_CONDITION TEXT NOT NULL,
                $COLUMN_RESTRICTIONS TEXT NOT NULL,
                $COLUMN_SOURCE TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createUsersTable)
        db.execSQL(createArtifactsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ARTIFACTS")
        onCreate(db)
    }

    fun registerUser(username: String, password: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME=?",
            arrayOf(username)
        )
        if (cursor.count > 0) {
            cursor.close()
            return false
        }
        cursor.close()

        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        val result = db.insert(TABLE_USERS, null, values)
        return result != -1L
    }

    fun loginUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?",
            arrayOf(username, password)
        )
        val isLoggedIn = cursor.count > 0
        cursor.close()
        return isLoggedIn
    }

    fun insertArtifact(
        name: String,
        description: String,
        catalogueNumber: String,
        physicalDescription: String,
        condition: String,
        restrictions: String,
        source: String
    ): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_CATALOGUE, catalogueNumber)
            put(COLUMN_PHYSICAL_DESC, physicalDescription)
            put(COLUMN_CONDITION, condition)
            put(COLUMN_RESTRICTIONS, restrictions)
            put(COLUMN_SOURCE, source)
        }
        return db.insert(TABLE_ARTIFACTS, null, values)
    }

    fun getArtifacts(): List<Artifact> {
        val artifacts = mutableListOf<Artifact>()
        val db = readableDatabase

        db.rawQuery("SELECT * FROM $TABLE_ARTIFACTS", null).use { cursor ->
            while (cursor.moveToNext()) {
                artifacts.add(
                    Artifact(
                        name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        catalogueNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATALOGUE)),
                        physicalDescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHYSICAL_DESC)),
                        condition = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONDITION)),
                        restrictions = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESTRICTIONS)),
                        source = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE)),
                        // ✅ assign a default drawable directly here
                        imageResId = R.drawable.default_artifact_image
                    )
                )
            }
        }
        return artifacts
    }
}
