package com.example.kotlincharts

import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.example.kotlincharts.databinding.ActivityMainBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val barChart: BarChart = findViewById(R.id.barChart)

        val visitors = mutableListOf<BarEntry>()

        // Add data to the list
        visitors.add(BarEntry(2014f, 420f))   // (X-value, Y-value)
        visitors.add(BarEntry(2015f, 415f))
        visitors.add(BarEntry(2016f, 508f))
        visitors.add(BarEntry(2017f, 606f))
        visitors.add(BarEntry(2018f, 202f))
        visitors.add(BarEntry(2019f, 550f))
        visitors.add(BarEntry(2020f, 440f))

        val barDataSet: BarDataSet = BarDataSet(visitors, "Visitors")
        barDataSet.setDrawValues(false)

        var colorsful = mutableListOf<Int>()

        for (i in visitors) {
            if (i.y > 500f) {
                colorsful.add(Color.GREEN)
            } else {
                colorsful.add(Color.RED)
            }
        }

        barDataSet.colors = colorsful

        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet)

        val pie = PieData()

        barChart.setFitBars(true)
        barChart.data = barData
        barChart.description.text = "Bar Chart Example"
        barChart.animateY(2000)

        val pieChart = findViewById<PieChart>(R.id.pieChart)

// Create entries and provide colors
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(30f, "Slice 1"))
        entries.add(PieEntry(20f, "Slice 2"))
        entries.add(PieEntry(50f, "Slice 3"))

        val colors = ArrayList<Int>()
        colors.add(Color.YELLOW)    // Color for Slice 1
        colors.add(Color.GREEN)   // Color for Slice 2
        colors.add(Color.CYAN)     // Color for Slice 3

// Create a PieDataSet with entries and colors
        val dataSet = PieDataSet(entries, "Pie Chart")
        dataSet.colors = colors

// Customize the data set, e.g., add text size, slice spacing, etc.
        dataSet.valueTextSize = 16f

// Create a PieData object with the data set
        val data = PieData(dataSet)

// Set the data to the PieChart
        pieChart.data = data
        pieChart.animateY(1400) // 1500 milliseconds (1.5 seconds)

//        startContinuousRotationAnimation(pieChart)
    }

    private fun startContinuousRotationAnimation(pieChart: PieChart) {
        val degreesToRotate = 10f // Number of degrees to rotate in each animation

        val rotateAnimation = RotateAnimation(pieChart, degreesToRotate)
        rotateAnimation.start()
    }


    private inner class RotateAnimation(
        private val chart: PieChart,
        private val degreesToRotate: Float
    ) : Runnable {
        private var isRunning = true

        init {
            chart.post(this)
        }

        override fun run() {
            if (isRunning) {
                // Rotate the data slices
                chart.spin(3500, chart.rotationAngle, chart.rotationAngle + degreesToRotate, Easing.Linear)

                // Delay the next rotation
                chart.postDelayed(this, 500)
            }
        }

        fun start() {
            isRunning = true
        }

        fun stop() {
            isRunning = false
        }
    }
}
