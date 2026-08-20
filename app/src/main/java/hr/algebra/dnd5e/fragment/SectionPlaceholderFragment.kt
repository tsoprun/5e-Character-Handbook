package hr.algebra.dnd5e.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import hr.algebra.dnd5e.R
import kotlinx.coroutines.selects.SelectInstance

class SectionPlaceholderFragment : Fragment(R.layout.fragment_section_placeholder) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.tvPlaceholder).text =
            arguments?.getString(ARG_TITLE).orEmpty()
    }
    companion object {
        private const val ARG_TITLE = "title"
        fun newInstance(title: String)= SectionPlaceholderFragment().apply {
            arguments = bundleOf(ARG_TITLE to title)
        }
    }
}

