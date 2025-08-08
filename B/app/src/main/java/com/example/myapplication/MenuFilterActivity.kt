package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuFilterActivity : AppCompatActivity() {
    
    private lateinit var closeButton: ImageView
    private lateinit var keywordInput: EditText
    private lateinit var priceRangeText: TextView
    private lateinit var priceSlider: SeekBar
    private lateinit var clearButton: Button
    private lateinit var doneButton: Button
    
    // Hair Removal CheckBoxes
    private lateinit var hairRemovalFullBody: CheckBox
    private lateinit var hairRemovalPartial: CheckBox
    private lateinit var hairRemovalShr: CheckBox
    private lateinit var hairRemovalIpl: CheckBox
    private lateinit var hairRemovalSsc: CheckBox
    private lateinit var hairRemovalMedical: CheckBox
    
    // Body Slimming CheckBoxes
    private lateinit var slimmingMassage: CheckBox
    private lateinit var slimmingEms: CheckBox
    private lateinit var slimmingIndiba: CheckBox
    private lateinit var slimmingHifu: CheckBox
    private lateinit var slimmingCellulite: CheckBox
    private lateinit var slimmingCooling: CheckBox
    private lateinit var slimmingCavitation: CheckBox
    private lateinit var slimmingRadio: CheckBox
    
    // Facial CheckBoxes
    private lateinit var facialEsthetic: CheckBox
    private lateinit var facialPeeling: CheckBox
    private lateinit var facialWhitening: CheckBox
    private lateinit var facialPore: CheckBox
    private lateinit var facialLift: CheckBox
    private lateinit var facialSmallFace: CheckBox
    private lateinit var facialBeautyHair: CheckBox
    
    // Nail, Eyelash, Eyebrow CheckBoxes
    private lateinit var nailGel: CheckBox
    private lateinit var nailSculpt: CheckBox
    private lateinit var nailEyelashExt: CheckBox
    private lateinit var nailEyelashPerm: CheckBox
    private lateinit var nailEyebrow: CheckBox
    
    // Relaxation CheckBoxes
    private lateinit var relaxMaternity: CheckBox
    private lateinit var relaxMeal: CheckBox
    private lateinit var relaxDna: CheckBox
    private lateinit var relaxPair: CheckBox
    private lateinit var relaxPersonal: CheckBox
    private lateinit var relaxKids: CheckBox
    private lateinit var relaxMens: CheckBox
    
    private var selectedCount = 5 // Default selected items (will be calculated dynamically)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_filter)
        
        initializeViews()
        setupClickListeners()
        setupPriceSlider()
        updateDoneButtonText()
    }
    
    private fun initializeViews() {
        closeButton = findViewById(R.id.closeButton)
        keywordInput = findViewById(R.id.keywordInput)
        priceRangeText = findViewById(R.id.priceRangeText)
        priceSlider = findViewById(R.id.priceSlider)
        clearButton = findViewById(R.id.clearButton)
        doneButton = findViewById(R.id.doneButton)
        
        // Initialize Hair Removal CheckBoxes
        hairRemovalFullBody = findViewById(R.id.hair_removal_full_body)
        hairRemovalPartial = findViewById(R.id.hair_removal_partial)
        hairRemovalShr = findViewById(R.id.hair_removal_shr)
        hairRemovalIpl = findViewById(R.id.hair_removal_ipl)
        hairRemovalSsc = findViewById(R.id.hair_removal_ssc)
        hairRemovalMedical = findViewById(R.id.hair_removal_medical)
        
        // Initialize Body Slimming CheckBoxes
        slimmingMassage = findViewById(R.id.slimming_massage)
        slimmingEms = findViewById(R.id.slimming_ems)
        slimmingIndiba = findViewById(R.id.slimming_indiba)
        slimmingHifu = findViewById(R.id.slimming_hifu)
        slimmingCellulite = findViewById(R.id.slimming_cellulite)
        slimmingCooling = findViewById(R.id.slimming_cooling)
        slimmingCavitation = findViewById(R.id.slimming_cavitation)
        slimmingRadio = findViewById(R.id.slimming_radio)
        
        // Initialize Facial CheckBoxes
        facialEsthetic = findViewById(R.id.facial_esthetic)
        facialPeeling = findViewById(R.id.facial_peeling)
        facialWhitening = findViewById(R.id.facial_whitening)
        facialPore = findViewById(R.id.facial_pore)
        facialLift = findViewById(R.id.facial_lift)
        facialSmallFace = findViewById(R.id.facial_small_face)
        facialBeautyHair = findViewById(R.id.facial_beauty_hair)
        
        // Initialize Nail, Eyelash, Eyebrow CheckBoxes
        nailGel = findViewById(R.id.nail_gel)
        nailSculpt = findViewById(R.id.nail_sculpt)
        nailEyelashExt = findViewById(R.id.nail_eyelash_ext)
        nailEyelashPerm = findViewById(R.id.nail_eyelash_perm)
        nailEyebrow = findViewById(R.id.nail_eyebrow)
        
        // Initialize Relaxation CheckBoxes
        relaxMaternity = findViewById(R.id.relax_maternity)
        relaxMeal = findViewById(R.id.relax_meal)
        relaxDna = findViewById(R.id.relax_dna)
        relaxPair = findViewById(R.id.relax_pair)
        relaxPersonal = findViewById(R.id.relax_personal)
        relaxKids = findViewById(R.id.relax_kids)
        relaxMens = findViewById(R.id.relax_mens)
    }
    
    private fun setupClickListeners() {
        // Close button
        closeButton.setOnClickListener {
            finish()
        }
        
        // Clear button
        clearButton.setOnClickListener {
            clearAllSelections()
        }
        
        // Done button
        doneButton.setOnClickListener {
            applyFilters()
        }
        
        // Setup checkbox listeners
        setupCheckBoxListeners()
    }
    
    private fun setupCheckBoxListeners() {
        val checkBoxes = listOf(
            hairRemovalFullBody, hairRemovalPartial, hairRemovalShr, hairRemovalIpl, hairRemovalSsc, hairRemovalMedical,
            slimmingMassage, slimmingEms, slimmingIndiba, slimmingHifu, slimmingCellulite, slimmingCooling, slimmingCavitation, slimmingRadio,
            facialEsthetic, facialPeeling, facialWhitening, facialPore, facialLift, facialSmallFace, facialBeautyHair,
            nailGel, nailSculpt, nailEyelashExt, nailEyelashPerm, nailEyebrow,
            relaxMaternity, relaxMeal, relaxDna, relaxPair, relaxPersonal, relaxKids, relaxMens
        )
        
        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                updateDoneButtonText()
            }
        }
    }
    
    private fun setupPriceSlider() {
        priceSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updatePriceRangeText(progress)
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    
    private fun updatePriceRangeText(progress: Int) {
        val minPrice = 5000
        val maxPrice = if (progress >= 95) "料金上限なし" else "${(progress * 1000) + minPrice}円"
        priceRangeText.text = "${minPrice}円 - $maxPrice"
    }
    
    private fun updateDoneButtonText() {
        val count = calculateSelectedCount()
        doneButton.text = "完了 (${count}件)"
    }
    
    private fun calculateSelectedCount(): Int {
        var count = 0
        val checkBoxes = listOf(
            hairRemovalFullBody, hairRemovalPartial, hairRemovalShr, hairRemovalIpl, hairRemovalSsc, hairRemovalMedical,
            slimmingMassage, slimmingEms, slimmingIndiba, slimmingHifu, slimmingCellulite, slimmingCooling, slimmingCavitation, slimmingRadio,
            facialEsthetic, facialPeeling, facialWhitening, facialPore, facialLift, facialSmallFace, facialBeautyHair,
            nailGel, nailSculpt, nailEyelashExt, nailEyelashPerm, nailEyebrow,
            relaxMaternity, relaxMeal, relaxDna, relaxPair, relaxPersonal, relaxKids, relaxMens
        )
        
        checkBoxes.forEach { checkBox ->
            if (checkBox.isChecked) count++
        }
        return count
    }
    
    private fun clearAllSelections() {
        val checkBoxes = listOf(
            hairRemovalFullBody, hairRemovalPartial, hairRemovalShr, hairRemovalIpl, hairRemovalSsc, hairRemovalMedical,
            slimmingMassage, slimmingEms, slimmingIndiba, slimmingHifu, slimmingCellulite, slimmingCooling, slimmingCavitation, slimmingRadio,
            facialEsthetic, facialPeeling, facialWhitening, facialPore, facialLift, facialSmallFace, facialBeautyHair,
            nailGel, nailSculpt, nailEyelashExt, nailEyelashPerm, nailEyebrow,
            relaxMaternity, relaxMeal, relaxDna, relaxPair, relaxPersonal, relaxKids, relaxMens
        )
        
        checkBoxes.forEach { checkBox ->
            checkBox.isChecked = false
        }
        
        updateDoneButtonText()
        
        Toast.makeText(this, "すべての選択をクリアしました", Toast.LENGTH_SHORT).show()
    }
    
    private fun applyFilters() {
        val keyword = keywordInput.text.toString()
        val priceRange = priceRangeText.text.toString()
        
        // Collect selected services
        val selectedServices = mutableListOf<String>()
        
        if (hairRemovalFullBody.isChecked) selectedServices.add("全身脱毛")
        if (hairRemovalPartial.isChecked) selectedServices.add("部分脱毛")
        if (hairRemovalShr.isChecked) selectedServices.add("SHR脱毛")
        if (hairRemovalIpl.isChecked) selectedServices.add("IPL脱毛")
        if (hairRemovalSsc.isChecked) selectedServices.add("S.S.C脱毛")
        if (hairRemovalMedical.isChecked) selectedServices.add("医療脱毛")
        
        if (slimmingMassage.isChecked) selectedServices.add("痩身マッサージ")
        if (slimmingEms.isChecked) selectedServices.add("EMS・電気刺激")
        if (slimmingIndiba.isChecked) selectedServices.add("インディバ")
        if (slimmingHifu.isChecked) selectedServices.add("ハイフ(HIFU)")
        if (slimmingCellulite.isChecked) selectedServices.add("セルライトクラッシュ")
        if (slimmingCooling.isChecked) selectedServices.add("冷却冷却")
        if (slimmingCavitation.isChecked) selectedServices.add("キャビテーション")
        if (slimmingRadio.isChecked) selectedServices.add("ラジオ波")
        
        if (facialEsthetic.isChecked) selectedServices.add("フェイシャルエステ")
        if (facialPeeling.isChecked) selectedServices.add("ピーリング")
        if (facialWhitening.isChecked) selectedServices.add("美白")
        if (facialPore.isChecked) selectedServices.add("毛穴洗浄")
        if (facialLift.isChecked) selectedServices.add("リフトアップマッサージ")
        if (facialSmallFace.isChecked) selectedServices.add("小顔マッサージ")
        if (facialBeautyHair.isChecked) selectedServices.add("美肌脱毛")
        
        if (nailGel.isChecked) selectedServices.add("ジェルネイル")
        if (nailSculpt.isChecked) selectedServices.add("スカルプネイル")
        if (nailEyelashExt.isChecked) selectedServices.add("マツエク")
        if (nailEyelashPerm.isChecked) selectedServices.add("マツパ")
        if (nailEyebrow.isChecked) selectedServices.add("アイブロウ")
        
        if (relaxMaternity.isChecked) selectedServices.add("マタニティマッサージ")
        if (relaxMeal.isChecked) selectedServices.add("食事カウンセリング")
        if (relaxDna.isChecked) selectedServices.add("DNA美容解析")
        if (relaxPair.isChecked) selectedServices.add("ペア施術")
        if (relaxPersonal.isChecked) selectedServices.add("パーソナルトレーニング")
        if (relaxKids.isChecked) selectedServices.add("キッズ脱毛/お子様脱毛")
        if (relaxMens.isChecked) selectedServices.add("メンズエステ対応")
        
        // Return result to previous activity
        val resultIntent = Intent().apply {
            putExtra("keyword", keyword)
            putExtra("priceRange", priceRange)
            putExtra("selectedServices", selectedServices.toTypedArray())
        }
        
        setResult(RESULT_OK, resultIntent)
        finish()
        
        Toast.makeText(this, "フィルターを適用しました", Toast.LENGTH_SHORT).show()
    }
} 