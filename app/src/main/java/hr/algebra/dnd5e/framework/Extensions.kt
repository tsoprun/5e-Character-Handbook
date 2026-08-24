package hr.algebra.dnd5e.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.edit
import androidx.core.content.getSystemService
import hr.algebra.dnd5e.DND_PROVIDER_CONTENT_URI
import hr.algebra.dnd5e.model.Character
import android.content.ContentValues
import androidx.preference.PreferenceManager
import hr.algebra.dnd5e.CLASSES_CONTENT_URI
import hr.algebra.dnd5e.RACES_CONTENT_URI
import hr.algebra.dnd5e.SKILLS_CONTENT_URI
import hr.algebra.dnd5e.SUBCLASSES_CONTENT_URI
import hr.algebra.dnd5e.api.ApiReference
import hr.algebra.dnd5e.model.ClassRef
import hr.algebra.dnd5e.model.RaceRef
import hr.algebra.dnd5e.model.SkillRef
import hr.algebra.dnd5e.model.SubclassRef

@SuppressLint("Range")
fun Context.fetchCharacters(): MutableList<Character> {
    val characters = mutableListOf<Character>()
    contentResolver.query(
        DND_PROVIDER_CONTENT_URI,
        null,
        null,
        null,
        null
    ).use { cursor ->
        while (cursor?.moveToNext() == true) {
            characters.add(
                Character(
                    cursor.getLong(cursor.getColumnIndex(Character::_id.name)),
                    cursor.getString(cursor.getColumnIndex(Character::name.name)),
                    cursor.getString(cursor.getColumnIndex(Character::race.name)),
                    cursor.getString(cursor.getColumnIndex(Character::characterClass.name)),
                    cursor.getString(cursor.getColumnIndex(Character::subclass.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::level.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::maxHp.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::currentHp.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::armorClass.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::speed.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::strength.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::dexterity.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::constitution.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::intelligence.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::wisdom.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::charisma.name)),
                    cursor.getString(cursor.getColumnIndex(Character::notes.name)),
                    cursor.getInt(cursor.getColumnIndex(Character::favorite.name)) == 1
                )
            )
        }
    }
    return characters
}

@SuppressLint("Range")
fun Context.fetchRaces(): List<ApiReference>{
    val races = mutableListOf<ApiReference>()
    contentResolver.query(
        RACES_CONTENT_URI,
        null,
        null,
        null,
        null
    ).use { cursor ->
        while (cursor?.moveToNext() == true) {
            races.add(
                ApiReference(
                    cursor.getString(cursor.getColumnIndex((RaceRef::apiIndex.name))),
                    cursor.getString(cursor.getColumnIndex(RaceRef::name.name)),
                    ""
                )
            )
        }
    }
    return races
}

@SuppressLint("Range")
fun Context.fetchClasses(): List<ApiReference>{
    val classes = mutableListOf<ApiReference>()
    contentResolver.query(
        CLASSES_CONTENT_URI,
        null,
        null,
        null,
        null
    ).use { cursor ->
        while (cursor?.moveToNext() == true) {
            classes.add(
                ApiReference(
                    cursor.getString(cursor.getColumnIndex((ClassRef::apiIndex.name))),
                    cursor.getString(cursor.getColumnIndex(ClassRef::name.name)),
                    ""
                )
            )
        }
    }
    return classes
}

@SuppressLint("Range")
fun Context.fetchSubclasses(classIndex: String): List<ApiReference>{
    val subclasses = mutableListOf<ApiReference>()
    contentResolver.query(
        SUBCLASSES_CONTENT_URI,
        null,
        "${SubclassRef::classIndex.name}=?",
        arrayOf(classIndex),
        null
    ).use { cursor ->
        while (cursor?.moveToNext() == true) {
            subclasses.add(
                ApiReference(
                    cursor.getString(cursor.getColumnIndex((SubclassRef::apiIndex.name))),
                    cursor.getString(cursor.getColumnIndex(SubclassRef::name.name)),
                    ""
                )
            )
        }
    }
    return subclasses
}

@SuppressLint("Range")
fun Context.fetchSkills(): List<ApiReference>{
    val skills = mutableListOf<ApiReference>()
    contentResolver.query(
        SKILLS_CONTENT_URI,
        null,
        null,
        null,
        null
    ).use { cursor ->
        while (cursor?.moveToNext() == true) {
            skills.add(
                ApiReference(
                    cursor.getString(cursor.getColumnIndex((SkillRef::apiIndex.name))),
                    cursor.getString(cursor.getColumnIndex(SkillRef::name.name)),
                    ""
                )
            )
        }
    }
    return skills
}


fun Character.toContentValues() = ContentValues().apply {
    put(Character::name.name, name)
    put(Character::race.name, race)
    put(Character::characterClass.name, characterClass)
    put(Character::subclass.name, subclass)
    put(Character::level.name, level)
    put(Character::maxHp.name, maxHp)
    put(Character::currentHp.name, currentHp)
    put(Character::armorClass.name, armorClass)
    put(Character::speed.name, speed)
    put(Character::strength.name, strength)
    put(Character::dexterity.name, dexterity)
    put(Character::constitution.name, constitution)
    put(Character::intelligence.name, intelligence)
    put(Character::wisdom.name, wisdom)
    put(Character::charisma.name, charisma)
    put(Character::notes.name, notes)
    put(Character::favorite.name, favorite)
}


fun View.applyAnimation(id: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, id))

inline fun <reified T : Activity> Context.startActivity() = startActivity(
    Intent(
        this,
        T::class.java
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })

fun callDelayed(delay: Long, work: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

inline fun <reified T : Activity> Context.startActivity(key: String, value: Int) = startActivity(
    Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(key, value)
    }
)

inline fun <reified T : Activity> Context.startActivity(key: String, value: Long) = startActivity(
    Intent(this, T::class.java).apply{
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(key,value)
    }
)

fun Context.setBooleanPreference(key: String, value: Boolean = true) {
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit {
            putBoolean(key, value)
        }
}

fun Context.getBooleanPreference(key: String): Boolean {
    return PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)
}

fun Context.isOnline(): Boolean {
    val connectivityManager =
        getSystemService<ConnectivityManager>() // compare with ours -> reified T: Any - returns null!
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
    return false
}

inline fun <reified T: BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))



