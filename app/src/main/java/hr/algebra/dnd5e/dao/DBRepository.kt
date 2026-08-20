package hr.algebra.dnd5e.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.dnd5e.model.Character

private const val DB_NAME = "dnd5e.db"
private const val DB_VERSION = 3
private const val TABLE_NAME = "characters"

private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${Character::_id.name} integer primary key autoincrement, " +
        "${Character::name.name} text not null, " +
        "${Character::race.name} text not null, " +
        "${Character::characterClass.name} text not null, " +
        "${Character::subclass.name} text not null, " +
        "${Character::level.name} integer not null, " +
        "${Character::maxHp.name} integer not null, " +
        "${Character::currentHp.name} integer not null, " +
        "${Character::armorClass.name} integer not null, " +
        "${Character::speed.name} integer not null, " +
        "${Character::strength.name} integer not null, " +
        "${Character::dexterity.name} integer not null, " +
        "${Character::constitution.name} integer not null, " +
        "${Character::intelligence.name} integer not null, " +
        "${Character::wisdom.name} integer not null, " +
        "${Character::charisma.name} integer not null, " +
        "${Character::notes.name} text not null, " +
        "${Character::favorite.name} integer not null" +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class DBRepository(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
    , Repository {

    override fun delete(selection: String?, selectionArgs: Array<String>?) =
        writableDatabase.delete(TABLE_NAME, selection, selectionArgs)

    override fun update(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)

    override fun query(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder
    )

    override fun insert(values: ContentValues?) =
        writableDatabase.insert(TABLE_NAME, null, values)

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}