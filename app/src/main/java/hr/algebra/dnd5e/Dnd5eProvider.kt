package hr.algebra.dnd5e

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import hr.algebra.dnd5e.dao.Repository
import hr.algebra.dnd5e.dao.getRepository


private const val AUTHORITY = "hr.algebra.dnd5e.provider"

val DND_PROVIDER_CONTENT_URI: Uri = "content://$AUTHORITY/characters".toUri()
val RACES_CONTENT_URI: Uri = "content://$AUTHORITY/races".toUri()
val CLASSES_CONTENT_URI: Uri = "content://$AUTHORITY/classes".toUri()
val SUBCLASSES_CONTENT_URI: Uri = "content://$AUTHORITY/subclasses".toUri()
val SKILLS_CONTENT_URI: Uri = "content://$AUTHORITY/skills".toUri()

private const val COLUMN_ID = "_id"
private const val COLLECTION = 10
private const val ITEM = 20

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)){
    // "content://hr.algebra.dnd5e.provider/<tablica>
    addURI(AUTHORITY, "*", COLLECTION)
    // "content://hr.algebra.dnd5e.provider/<tablica>/22
    addURI(AUTHORITY, "*/#", ITEM)
    this
}

class DndProvider : ContentProvider() {

    private lateinit var repository: Repository
    private fun Uri.table()=pathSegments.first()
    override fun getType(uri: Uri): String? = null


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = repository.insert(uri.table(), values)
        return ContentUris.withAppendedId(uri, id)
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)) {
            COLLECTION -> return repository.update(uri. table(), values, selection, selectionArgs)
            ITEM -> {
                val id = uri.lastPathSegment
                if(id != null) {
                    return repository.update(uri.table(),values,"$COLUMN_ID=?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("Wrong URI")
    }

    override fun delete(
        uri: Uri, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)) {
            COLLECTION -> return repository.delete(uri. table(), selection, selectionArgs)
            ITEM -> {
                val id = uri.lastPathSegment
                if(id != null) {
                    return repository.delete(uri.table(),"$COLUMN_ID=?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("Wrong URI")
    }

    override fun onCreate(): Boolean {
        repository=getRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor? = repository.query(
        uri.table(),
        projection,
        selection,
        selectionArgs,
        sortOrder
    )
}
