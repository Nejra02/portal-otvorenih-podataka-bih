package com.example.projekatzavrsni.presentation.screen.components

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import androidx.compose.foundation.layout.*

@Composable
fun BarChartView(data: List<Float>) {
    AndroidView(factory = { context ->
        BarChart(context).apply {
            val labels = listOf("FBiH", "RS")

            val customColors = listOf(
                Color.parseColor("#42A5F5"), // plava
                Color.parseColor("#EF5350")  // crvena
            )

            val dataSets = labels.mapIndexed { index, entitet ->
                val entry = listOf(BarEntry(index.toFloat(), data[index]))
                BarDataSet(entry, entitet).apply {
                    color = customColors[index % customColors.size]
                    valueTextSize = 15f
                }
            }

            val barData = BarData(dataSets as List<IBarDataSet>)
            this.data = barData

            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.setDrawGridLines(false)
            xAxis.textSize = 15f

            axisLeft.axisMinimum = 0f
            axisRight.isEnabled = false

            legend.textSize = 14f
            legend.formSize = 12f
            legend.form = Legend.LegendForm.SQUARE
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)
            legend.isWordWrapEnabled = true
            legend.isEnabled = false

            description.isEnabled = false
            invalidate()
        }
    }, modifier = Modifier
        .fillMaxWidth()
        .height(320.dp))
}
