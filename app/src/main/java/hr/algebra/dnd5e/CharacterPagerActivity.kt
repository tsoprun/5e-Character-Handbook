package hr.algebra.dnd5e

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewpager2.widget.ViewPager2
import hr.algebra.dnd5e.adapter.CharacterPagerAdapter
import hr.algebra.dnd5e.databinding.ActivityCharacterPagerBinding
import hr.algebra.dnd5e.framework.fetchCharacters


const val CHARACTER_POS = "hr.algebra.dnd5e.character_position"

class CharacterPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterPagerBinding

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initPager()
        initBottomNav()
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        WindowCompat.getInsetsController(window, binding.root)
            .isAppearanceLightStatusBars = true
        applyStatusBarInset()
        position = intent.getIntExtra(CHARACTER_POS, position)

    }

    private fun applyStatusBarInset() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top)
            insets
        }
    }

    private fun initPager() {
        binding.viewPager2.adapter = CharacterPagerAdapter(this, position)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private val navIds = listOf(

        R.id.menuActions,
        R.id.menuSpells,
        R.id.menuSheet,
        R.id.menuSkills,
        R.id.menuConditions)

    private fun initBottomNav(){
    binding.bottomNav.selectedItemId = R.id.menuSheet
        binding.bottomNav.setOnItemSelectedListener {  item ->
            binding.viewPager2.currentItem = navIds.indexOf(item.itemId)
            true
        }
        binding.viewPager2.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(pos: Int) {
                    binding.bottomNav.selectedItemId = navIds[pos]
                }
            }
        )
        binding.viewPager2.setCurrentItem(navIds.indexOf(R.id.menuSheet), false)
    }
}

