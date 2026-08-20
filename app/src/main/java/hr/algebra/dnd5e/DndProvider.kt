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
import hr.algebra.dnd5e.model.Character

private const val AUTHORITY = "hr.algebra.dnd5e.provider"
private const val PATH = "characters"
val DND_PROVIDER_CONTENT_URI: Uri = "content://$AUTHORITY/$PATH".toUri()

private const val CHARACTERS = 10
private const val CHARACTER_ID = 20

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)){
    // "content://hr.algebra.nasa.provider/items : 10
    addURI(AUTHORITY, PATH, CHARACTERS)
    //"content://hr.algebra.nasa.provider/items/22 : 20
    addURI(AUTHORITY, "$PATH/#", CHARACTER_ID)
    this
}

class DndProvider : ContentProvider() {

    private lateinit var repository: Repository

    // "content://hr.algebra.nasa.provider/items  -> SVI ITEMS add
    // "content://hr.algebra.nasa.provider/items/22  -> SINGLE ITEM delete, select, update
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(URI_MATCHER.match(uri)) {
            CHARACTERS -> return repository.delete(selection, selectionArgs)
            CHARACTER_ID -> {
                val id = uri.lastPathSegment
                if(id != null) {
                    return repository.delete("${Character::_id.name}=?", arrayOf(id))
                }
            }
        }

        throw IllegalArgumentException("WRONG URI")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = repository.insert(values)
        return ContentUris.withAppendedId(DND_PROVIDER_CONTENT_URI, id)
    }

    override fun onCreate(): Boolean {
        repository = getRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor = repository.query(
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)) {
            CHARACTERS -> return repository.update(values, selection, selectionArgs)
            CHARACTER_ID -> {
                val id = uri.lastPathSegment
                if(id != null) {
                    return repository.update(values,"${Character::_id.name}=?", arrayOf(id))
                }
            }
        }
        throw IllegalArgumentException("Wrong URI")
    }
}
