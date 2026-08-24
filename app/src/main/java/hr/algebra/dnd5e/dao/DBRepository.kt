package hr.algebra.dnd5e.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.dnd5e.model.Character
import hr.algebra.dnd5e.model.ClassRef
import hr.algebra.dnd5e.model.RaceRef
import hr.algebra.dnd5e.model.SkillRef
import hr.algebra.dnd5e.model.SubclassRef

private const val DB_NAME = "dnd5e.db"
private const val DB_VERSION = 4

private const val TABLE_CHARACTERS = "characters"
private const val TABLE_RACES = "races"
private const val TABLE_CLASSES = "classes"
private const val TABLE_SUBCLASSES = "subclasses"
private const val TABLE_SKILLS = "skills"


private val ALL_TABLES = listOf(
    TABLE_CHARACTERS, TABLE_RACES, TABLE_CLASSES, TABLE_SUBCLASSES, TABLE_SKILLS
)

private val CREATE_CHARACTERS = "create table $TABLE_CHARACTERS( " +
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

private val CREATE_RACES = "create Table $TABLE_RACES(" +
        "${RaceRef::_id.name} integer primary key autoincrement, " +
        "${RaceRef::apiIndex.name} text not null, " +
        "${RaceRef::name.name} text not null, " +
        "${RaceRef::speed.name} integer not null, " +
        "${RaceRef::abilityBonuses.name} text not null" +
        ")"

private val CREATE_CLASSES = "create Table $TABLE_CLASSES(" +
        "${ClassRef::_id.name} integer primary key autoincrement, " +
        "${ClassRef::apiIndex.name} text not null, " +
        "${ClassRef::name.name} text not null, " +
        "${ClassRef::hitDie.name} integer not null, " +
        "${ClassRef::savingThrows.name} text not null, " +
        "${ClassRef::skillChoiceCount.name} integer not null, " +
        "${ClassRef::skillOptions.name} text not null" +
        ")"

private val CREATE_SUBCLASSES = "create Table $TABLE_SUBCLASSES(" +
        "${SubclassRef::_id.name} integer primary key autoincrement, " +
        "${SubclassRef::apiIndex.name} text not null, " +
        "${SubclassRef::name.name} text not null, " +
        "${SubclassRef::classIndex.name} text not null" +
        ")"

private val CREATE_SKILLS = "create Table $TABLE_SKILLS(" +
        "${SkillRef::_id.name} integer primary key autoincrement, " +
        "${SkillRef::apiIndex.name} text not null, " +
        "${SkillRef::name.name} text not null, " +
        "${SkillRef::ability.name} text not null" +
        ")"



class DBRepository(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
    , Repository {

    override fun delete(table: String, selection: String?, selectionArgs: Array<String>?) =
        writableDatabase.delete(table, selection, selectionArgs)

    override fun update(
        table: String,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(table, values, selection, selectionArgs)

    override fun query(
        table: String,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor = readableDatabase.query(
        table, projection, selection, selectionArgs, null, null, sortOrder
    )

    override fun insert(table: String, values: ContentValues?) =
        writableDatabase.insert(table, null, values)

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_CHARACTERS)
        db?.execSQL(CREATE_RACES)
        db?.execSQL(CREATE_CLASSES)
        db?.execSQL(CREATE_SUBCLASSES)
        db?.execSQL(CREATE_SKILLS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        ALL_TABLES.forEach { db?.execSQL("drop table if exists $it") }
        onCreate(db)
    }
}