package hr.algebra.dnd5e

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewpager2.widget.ViewPager2
import hr.algebra.dnd5e.adapter.CharacterCreateAdapter
import hr.algebra.dnd5e.adapter.CharacterPagerAdapter
import hr.algebra.dnd5e.databinding.ActivityCharacterCreateBinding
import hr.algebra.dnd5e.databinding.ActivityCharacterPagerBinding
import hr.algebra.dnd5e.framework.fetchCharacters
import hr.algebra.dnd5e.model.Ability
import hr.algebra.dnd5e.model.AbilityScore


class CharacterCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterCreateBinding

    var selectedRace: String?=null
    var selectedClass: String?=null

    var selectedClassIndex: String?=null
    var selectedSubclass: String?=null

    val abilityScores = Ability.values().map { AbilityScore(it, 8) }.toMutableList()

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterCreateBinding.inflate(layoutInflater)
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

    }

    private fun applyStatusBarInset() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top)
            insets
        }
    }

    private fun initPager() {
        binding.viewPager2.adapter = CharacterCreateAdapter(this, position)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private val navIds = listOf(

        R.id.menuRace,
        R.id.menuClass,
        R.id.menuSubclass,
        R.id.menuAbilities,
        R.id.menuSkillStep)

    private fun initBottomNav(){
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

        binding.viewPager2.setCurrentItem(0, false)
        binding.bottomNav.selectedItemId = R.id.menuRace
    }
}

