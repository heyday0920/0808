package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.TopBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

data class RecentPractitioner(
    val id: String,
    val name: String,
    val imageUrl: String? = null
)

class TopActivity : AppCompatActivity() {

    private lateinit var binding: TopBinding
    
    private val viewModel: TopViewModel by viewModels {
        TopViewModelFactory(
            RecentPractitionersRepository(
                getSharedPreferences("recent_practitioners", MODE_PRIVATE),
                Gson()
            )
        )
    }

    private val menuFilterLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val selectedServices = result.data!!.getStringArrayExtra("selectedServices") ?: arrayOf()
            updateMenuSelector(selectedServices)
            Toast.makeText(this, "フィルター条件を適用しました", Toast.LENGTH_SHORT).show()
        }
    }

    private val dateTimeLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            updateDateSelector(result.data!!)
            Toast.makeText(this, "日時を適用しました", Toast.LENGTH_SHORT).show()
        }
    }

    private val areaSelectionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            updateAreaSelector(result.data!!)
            Toast.makeText(this, "エリアを適用しました", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupObservers()

        viewModel.loadRecentPractitioners()
    }

    private fun setupClickListeners() {
        binding.menuSelector.setOnClickListener {
            val intent = Intent(this, MenuFilterActivity::class.java)
            menuFilterLauncher.launch(intent)
        }

        binding.dateSelector.setOnClickListener {
            val intent = Intent(this, DateTimeSelectionActivity::class.java)
            dateTimeLauncher.launch(intent)
        }

        binding.areaSelector.setOnClickListener {
            val intent = Intent(this, AreaSelectionActivity::class.java)
            areaSelectionLauncher.launch(intent)
        }

        binding.searchButton.setOnClickListener {
            performSearch()
        }

        setupCategoryButtons()
        setupFooterNavigation()
        setupHeaderMenu()
    }

    private fun setupObservers() {
        viewModel.recentPractitioners.observe(this) { practitioners ->
            displayRecentPractitioners(practitioners)
        }
    }

    private fun setupCategoryButtons() {
        binding.categoryEsthe.setOnClickListener { navigateToSearch("エステ") }
        binding.categoryYoga.setOnClickListener { navigateToSearch("ヨガ") }
        binding.categoryNail.setOnClickListener { navigateToSearch("ネイル") }
        binding.categoryFacial.setOnClickListener { navigateToSearch("フェイシャル") }
        binding.categoryEyelash.setOnClickListener { navigateToSearch("まつげ") }
        binding.categoryHairRemoval.setOnClickListener { navigateToSearch("脱毛") }
    }

    private fun setupFooterNavigation() {
        val navigationHelper = BottomNavigationHelper(this)
        navigationHelper.setupBottomNavigation(
            binding.bottomSearch,
            binding.bottomHome,
            binding.bottomChat,
            binding.bottomProfile,
            "search"
        )
    }

    private fun setupHeaderMenu() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
    
    private fun performSearch() {
        val keyword = binding.searchEdittext.text.toString()
        val menu = if (binding.menuSelector.text != "未指定") binding.menuSelector.text.toString() else ""
        val date = if (binding.dateSelector.text != "未指定") binding.dateSelector.text.toString() else ""
        val area = if (binding.areaSelector.text != "未指定") binding.areaSelector.text.toString() else ""

        val intent = Intent(this, PractitionerListActivity::class.java).apply {
            putExtra("keyword", keyword)
            putExtra("menu", menu)
            putExtra("date", date)
            putExtra("area", area)
        }
        startActivity(intent)
    }

    private fun navigateToSearch(category: String) {
        val intent = Intent(this, PractitionerListActivity::class.java).apply {
            putExtra("category", category)
        }
        startActivity(intent)
    }

    private fun displayRecentPractitioners(practitioners: List<RecentPractitioner>) {
        val practitionerLayouts = listOf(
            binding.recentPractitioner1,
            binding.recentPractitioner2,
            binding.recentPractitioner3,
            binding.recentPractitioner4,
            binding.recentPractitioner5
        )

        val practitionerImageViews = listOf(
            binding.practitionerImage1,
            binding.practitionerImage2,
            binding.practitionerImage3,
            binding.practitionerImage4,
            binding.practitionerImage5
        )

        val practitionerNameViews = listOf(
            binding.practitionerName1,
            binding.practitionerName2,
            binding.practitionerName3,
            binding.practitionerName4,
            binding.practitionerName5
        )

        practitionerLayouts.forEach { it.visibility = View.GONE }

        practitioners.forEachIndexed { index, practitioner ->
            if (index < practitionerLayouts.size) {
                practitionerLayouts[index].visibility = View.VISIBLE
                practitionerNameViews[index].text = practitioner.name
                
                Glide.with(this)
                    .load(practitioner.imageUrl)
                    .placeholder(R.color.background_card)
                    .circleCrop()
                    .into(practitionerImageViews[index])
                
                practitionerLayouts[index].setOnClickListener {
                    val intent = Intent(this, PractitionerDetailActivity::class.java)
                    intent.putExtra("practitioner_id", practitioner.id)
                    intent.putExtra("practitioner_name", practitioner.name)
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateMenuSelector(selectedServices: Array<String>) {
        if (selectedServices.isNotEmpty()) {
            val displayText = if (selectedServices.size <= 2) {
                selectedServices.joinToString("、")
            } else {
                "${selectedServices.take(2).joinToString("、")} 他${selectedServices.size - 2}件"
            }
            binding.menuSelector.text = displayText
        } else {
            binding.menuSelector.text = "未指定"
        }
    }

    private fun updateDateSelector(data: Intent) {
        val selectedDate = data.getStringExtra("selectedDate")
        val selectedStartTime = data.getStringExtra("selectedStartTime")
        val selectedEndTime = data.getStringExtra("selectedEndTime")

        val displayText = when {
            selectedDate != null && selectedStartTime != null && selectedEndTime != null -> {
                "2月${selectedDate}日 ${selectedStartTime}~${selectedEndTime}"
            }
            selectedDate != null -> {
                "2月${selectedDate}日"
            }
            else -> "未指定"
        }
        binding.dateSelector.text = displayText
    }

    private fun updateAreaSelector(data: Intent) {
        val selectedArea = data.getStringExtra("selected_area")
        if (selectedArea != null && selectedArea != "指定しない") {
            binding.areaSelector.text = selectedArea
            binding.areaSelector.setTextColor(getColor(R.color.text_primary))
        } else {
            binding.areaSelector.text = "未指定"
            binding.areaSelector.setTextColor(getColor(R.color.text_hint))
        }
    }
}