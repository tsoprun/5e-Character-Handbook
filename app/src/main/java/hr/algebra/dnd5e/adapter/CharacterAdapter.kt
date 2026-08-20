package hr.algebra.dnd5e.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.dnd5e.DND_PROVIDER_CONTENT_URI
import hr.algebra.dnd5e.R
import hr.algebra.dnd5e.model.Character
import hr.algebra.dnd5e.CHARACTER_POS
import hr.algebra.dnd5e.CharacterPagerActivity
import hr.algebra.dnd5e.framework.startActivity


class CharacterAdapter(
    private val context: Context,
    private val characters: MutableList<Character>
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_character, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val character = characters[position]
        holder.bind(character)

        holder.itemView.setOnClickListener {
            context.startActivity<CharacterPagerActivity>(CHARACTER_POS, position)
        }

        holder.itemView.setOnLongClickListener {
            deleteCharacter(position)
            true
        }

    }

    // "content://hr.algebra.dnd5e.provider/Characters
    //"content://hr.algebra.dnd5e.provider/Characters/22

    private fun deleteCharacter(position: Int) {
        val character = characters[position]
        context.contentResolver.delete(
            ContentUris.withAppendedId(DND_PROVIDER_CONTENT_URI, character._id!!),
            null,
            null
        )
        characters.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount() = characters.count()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvMeta = itemView.findViewById<TextView>(R.id.tvMeta)

        fun bind(character: Character){
            tvName.text = character.name
            tvMeta.text =
                "${character.race} • ${character.characterClass} • Lvl ${character.level}"
        }

    }


}