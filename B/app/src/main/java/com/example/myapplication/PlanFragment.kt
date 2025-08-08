package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class PlanFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        Log.d("PlanFragment", "onViewCreated called")
        
        // プラン詳細コンテナを取得
        val planDetailContainer = view.findViewById<LinearLayout>(R.id.planDetailContainer)
        var isDetailVisible = false
        
        Log.d("PlanFragment", "planDetailContainer found: ${planDetailContainer != null}")
        
        // コースカードのクリックリスナーを設定（プラン詳細の展開/折りたたみ）
        val courseCard = view.findViewById<LinearLayout>(R.id.courseCard)
        val courseCard2 = view.findViewById<LinearLayout>(R.id.courseCard2)
        
        Log.d("PlanFragment", "courseCard found: ${courseCard != null}")
        
        courseCard?.setOnClickListener {
            Log.d("PlanFragment", "courseCard clicked! Current visibility: $isDetailVisible")
            isDetailVisible = !isDetailVisible
            planDetailContainer?.visibility = if (isDetailVisible) View.VISIBLE else View.GONE
            Log.d("PlanFragment", "New visibility set to: ${if (isDetailVisible) "VISIBLE" else "GONE"}")
            
            // 詳細が表示された後にボタンのリスナーを設定
            if (isDetailVisible) {
                val menuReservationButton = view.findViewById<Button>(R.id.menuReservationButton)
                Log.d("PlanFragment", "menuReservationButton found after expand: ${menuReservationButton != null}")
                
                menuReservationButton?.setOnClickListener {
                    val intent = Intent(requireContext(), DateTimeSelectionActivity::class.java)
                    intent.putExtra("courseName", "クイックボディケア")
                    intent.putExtra("courseDuration", "30分")
                    intent.putExtra("coursePrice", "￥3,300")
                    startActivity(intent)
                }
            }
        }
        
        courseCard2.setOnClickListener {
            val intent = Intent(requireContext(), DateTimeSelectionActivity::class.java)
            intent.putExtra("courseName", "リラクゼーションケア")
            intent.putExtra("courseDuration", "60分")
            intent.putExtra("coursePrice", "￥5,500")
            startActivity(intent)
        }
    }
} 