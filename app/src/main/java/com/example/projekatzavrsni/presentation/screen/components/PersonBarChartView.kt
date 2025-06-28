package com.example.projekatzavrsni.presentation.screen.components

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import androidx.compose.foundation.layout.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

@Composable
fun PersonBarChartView(data: Map<String, Int>) {
    AndroidView(factory = { context ->
        HorizontalBarChart(context).apply {
            val labels = data.keys.toList()
            val values = data.values.toList()

            val customColors = listOf(
                Color.parseColor("#F44336"), // crvena
                Color.parseColor("#2196F3"), // plava
                Color.parseColor("#4CAF50"), // zelena
                Color.parseColor("#FF9800"), // narandžasta
                Color.parseColor("#9C27B0"), // ljubičasta
                Color.parseColor("#03A9F4"), // svijetlo plava
                Color.parseColor("#CDDC39"), // limeta
                Color.parseColor("#E91E63"), // pink
                Color.parseColor("#FFEB3B"), // žuta
                Color.parseColor("#00BCD4"), // cijan
                Color.parseColor("#8BC34A"), // svijetlo zelena
                Color.parseColor("#795548")  // smeđa
            )

            val dataSets = labels.mapIndexed { index, kanton ->
                val entry = listOf(BarEntry(index.toFloat(), values[index].toFloat()))
                BarDataSet(entry, kanton).apply {
                    color = customColors[index % customColors.size] // unikatna boja
                    valueTextSize = 12f
                }
            }

            val barData = BarData(dataSets as List<IBarDataSet>)
            this.data = barData

            xAxis.isEnabled = false

            axisLeft.setDrawGridLines(false)
            axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            axisLeft.setDrawLabels(false)
            axisLeft.axisMinimum = 0f

            axisRight.isEnabled = false

            legend.textSize = 12f
            legend.formSize = 12f
            legend.form = Legend.LegendForm.SQUARE
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)
            legend.isWordWrapEnabled = true
            legend.isEnabled = true

            description.isEnabled = false
            invalidate()
        }
    }, modifier = Modifier
        .fillMaxWidth()
        .height((data.size * 32).dp + 150.dp))
}
