package com.example.maskdetector.mainActivity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.cleancore.data.Resource
import com.example.maskdetector.R
import com.example.maskdetector.databinding.ActivityMainBinding
import com.example.maskdetector.tflite.DetectorActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateButton: Button
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var cl: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar_main))

        viewModel.dataCovid.observe(this, {
            if(it != null){
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        binding.confirmedNumber.text = it.data?.confirmed.toString()
                        binding.deathNumber.text = it.data?.death.toString()
                        binding.recoveredNumber.text = it.data?.recovered.toString()

                        val chart = findViewById<PieChart>(R.id.chart)
                        chart.description.isEnabled = false
                        chart.setExtraOffsets(5f, 10f, 5f, 5f)

                        chart.dragDecelerationFrictionCoef = 0.95f

                        chart.setCenterTextTypeface(Typeface.SERIF)
                        chart.centerText = generateCenterSpannableText(it.data?.confirmed)

                        chart.isDrawHoleEnabled = true
                        chart.setHoleColor(Color.WHITE)

                        chart.setTransparentCircleColor(Color.WHITE)
                        chart.setTransparentCircleAlpha(110)

                        chart.holeRadius = 58f
                        chart.transparentCircleRadius = 61f

                        chart.setDrawCenterText(true)

                        chart.rotationAngle = 0f
                        chart.isRotationEnabled = true
                        chart.isHighlightPerTapEnabled = true


                        chart.animateY(1400, Easing.EaseInOutQuad)
                        val l = chart.legend
                        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                        l.orientation = Legend.LegendOrientation.HORIZONTAL
                        l.setDrawInside(false)
                        l.xEntrySpace = 7f
                        l.yEntrySpace = 0f
                        l.yOffset = 0f

                        chart.setEntryLabelColor(Color.BLACK)
                        chart.setEntryLabelTypeface(Typeface.DEFAULT)
                        chart.setEntryLabelTextSize(12f)

                        val colors = java.util.ArrayList<Int>()

                        colors.add(Color.rgb(236,9,62))
                        colors.add(Color.rgb(48,189,23))
                        colors.add(Color.rgb(255,233,41))


                        colors.add(ColorTemplate.getHoloBlue())

                        val confirmed = it.data?.confirmed
                        val death = it.data?.death
                        val recovered = it.data?.recovered

                        val entries: ArrayList<PieEntry> = arrayListOf(PieEntry(death!!.toFloat(), "death"), PieEntry(recovered!!.toFloat(), "recovered"), PieEntry((confirmed!! - (death + recovered)).toFloat(), "recovering"))

                        val dataSet = PieDataSet(entries, "Covid-19 Cases")
                        dataSet.setDrawIcons(false)

                        dataSet.sliceSpace = 3f
                        dataSet.iconsOffset = MPPointF(0f, 40f)
                        dataSet.selectionShift = 5f

                        dataSet.colors = colors
                        val data = PieData(dataSet)
                        data.setValueFormatter(PercentFormatter(chart))
                        data.setValueTextSize(12f)
                        data.setValueTextColor(Color.BLACK)
                        data.setValueTypeface(Typeface.DEFAULT)

                        chart.setUsePercentValues(true)
                        chart.data = data

                        chart.highlightValues(null)

                        chart.invalidate()
                    }
                    is Resource.Error -> {
                        Log.d("data Covid", "failed")
                    }
                }
            }
        })

        val chart_line: LineChart = findViewById(R.id.line_chart1)
        chart_line.setOnChartValueSelectedListener(this)
        chart_line.setDrawGridBackground(false)
        chart_line.description.isEnabled = false
        chart_line.setDrawBorders(true)
        chart_line.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart_line.xAxis.isEnabled = false
        chart_line.axisRight.isEnabled = false
        chart_line.axisRight.setDrawAxisLine(false)
        chart_line.axisRight.setDrawGridLines(false)
        chart_line.xAxis.setDrawAxisLine(true)
        chart_line.xAxis.setDrawGridLines(true)

        // enable touch gestures
        chart_line.setTouchEnabled(false)
        // enable scaling and dragging
        chart_line.isDragEnabled = false
        chart_line.setScaleEnabled(false)
        // if disabled, scaling can be done on x- and y-axis separately
        chart_line.setPinchZoom(false)


        val l: Legend = chart_line.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)

        db.collection("maskData")
            .orderBy("time", Query.Direction.ASCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener {documents ->
                var ts: Timestamp
                var date: Date
                for (document in documents) {
                    ts = document.data["time"] as Timestamp
                    ts = Timestamp(ts.seconds - 43200, ts.nanoseconds)
                    date = ts.toDate()

                    db.collection("maskData")
                        .orderBy("time", Query.Direction.ASCENDING)
                        .whereGreaterThanOrEqualTo("time", ts)
                        .get()
                        .addOnSuccessListener { docs ->
                            chart_line.resetTracking()

                            val dataSets = ArrayList<ILineDataSet>()
                            var reference_ts: Long = 0
                            val array1 = ArrayList<Entry>()
                            val array2 = ArrayList<Entry>()
                            var counter = 0
                            var temp1: Long = 0
                            var temp2: Long = 0
                            for(doc in docs){
                                val currTs = doc.data["time"] as Timestamp
                                if(counter == 0){
                                    reference_ts = currTs.seconds
                                }
                                val simplifiedTs: Long = currTs.seconds - reference_ts
                                var withMask = doc.data["withMask"] as Long
                                var withoutMask = doc.data["withoutMask"] as Long

                                withMask += temp1
                                withoutMask += temp2

                                temp1 = withMask
                                temp2 = withoutMask

                                array1.add(Entry(simplifiedTs.toFloat(), withMask.toFloat()))
                                array2.add(Entry(simplifiedTs.toFloat(), withoutMask.toFloat()))

                                counter ++
                            }
                            val lineDataSet1 = LineDataSet(array1, "with mask")
                            val lineDataSet2 = LineDataSet(array2, "without mask")
                            lineDataSet1.lineWidth = 2.5f
                            lineDataSet2.lineWidth = 2.5f

                            lineDataSet1.circleRadius = 4f
                            lineDataSet2.circleRadius = 4f

                            val colors = java.util.ArrayList<Int>()

                            colors.add(Color.rgb(236,9,62))
                            colors.add(Color.rgb(48,189,23))
                            colors.add(Color.rgb(255,233,41))

                            lineDataSet1.color = colors[1]
                            lineDataSet2.color = colors[0]

                            lineDataSet1.setCircleColor(colors[2])
                            lineDataSet2.setCircleColor(colors[2])
                            lineDataSet1.setDrawCircles(false)
                            lineDataSet2.setDrawCircles(false)
                            lineDataSet1.setDrawValues(false)
                            lineDataSet2.setDrawValues(false)

                            dataSets.add(lineDataSet1)
                            dataSets.add(lineDataSet2)

                            chart_line.animateX(2000)
                            val data = LineData(dataSets)
                            chart_line.data = data
                            chart_line.invalidate()

                            initDatePicker()
                            dateButton = findViewById(R.id.datePickerButton)
                            dateButton.text = getTodaysDate()

                            chart_line.isAutoScaleMinMaxEnabled = true;

                            counter = 0
                        }.addOnFailureListener {
                            Log.d("firestore", "Failed to load data from firebase")
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("firestore", "Error getting documents: ", exception)
            }

        cl = findViewById(R.id.line_chart2)
        cl.setOnChartValueSelectedListener(this)
        cl.setDrawGridBackground(false)
        cl.description.isEnabled = false
        cl.setDrawBorders(true)
        cl.xAxis.position = XAxis.XAxisPosition.BOTTOM
        cl.xAxis.isEnabled = false
        cl.axisRight.isEnabled = false
        cl.axisRight.setDrawAxisLine(false)
        cl.axisRight.setDrawGridLines(false)
        cl.xAxis.setDrawAxisLine(true)
        cl.xAxis.setDrawGridLines(true)

        // enable touch gestures
        cl.setTouchEnabled(false)
        // enable scaling and dragging
        cl.isDragEnabled = false
        cl.setScaleEnabled(false)
        // if disabled, scaling can be done on x- and y-axis separately
        cl.setPinchZoom(false)


        val le: Legend = chart_line.legend
        le.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        le.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        le.orientation = Legend.LegendOrientation.HORIZONTAL
        le.setDrawInside(false)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.camera_button -> {
                val intent = Intent(this, DetectorActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    companion object {
        @kotlin.jvm.JvmField
        var MINIMUM_CONFIDENCE_TF_OD_API: Float = 0.5f
    }

    private fun generateCenterSpannableText(confirmed: Int?): SpannableString {
        val s = SpannableString("Confirmed Cases :\n$confirmed")
        s.setSpan(RelativeSizeSpan(1.2f), 0, 17, 0)
        s.setSpan(RelativeSizeSpan(2f), 17, s.length, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 17, s.length, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 17, s.length, 0)
        s.setSpan(RelativeSizeSpan(.8f), 17, s.length, 0)
        return s
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected() {
        TODO("Not yet implemented")
    }

    private fun getTodaysDate(): String? {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month += 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return makeDateString(day, month, year)
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month += 1
                val date = makeDateString(day, month-1, year)
                val dDate = Timestamp(Date(year-1900, month-1, day))

                dateButton.text = date
                Log.d("firestore", dDate.toString())

                db.collection("maskData")
                    .whereGreaterThanOrEqualTo("time", dDate)
                    .whereLessThanOrEqualTo("time", Timestamp(dDate.seconds + 86400, 0))
                    .orderBy("time", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener{ docs ->
                        cl.resetTracking()

                        val dataSets = ArrayList<ILineDataSet>()
                        var reference_ts: Long = 0
                        val array1 = ArrayList<Entry>()
                        val array2 = ArrayList<Entry>()
                        var counter = 0
                        var temp1: Long = 0
                        var temp2: Long = 0
                        for(doc in docs){
                            val currTs = doc.data["time"] as Timestamp
                            if(counter == 0){
                                reference_ts = currTs.seconds
                            }
                            val simplifiedTs: Long = currTs.seconds - reference_ts
                            var withMask = doc.data["withMask"] as Long
                            var withoutMask = doc.data["withoutMask"] as Long

                            withMask += temp1
                            withoutMask += temp2

                            temp1 = withMask
                            temp2 = withoutMask

                            array1.add(Entry(simplifiedTs.toFloat(), withMask.toFloat()))
                            array2.add(Entry(simplifiedTs.toFloat(), withoutMask.toFloat()))

                            counter ++
                        }
                        val lineDataSet1 = LineDataSet(array1, "with mask")
                        val lineDataSet2 = LineDataSet(array2, "without mask")
                        lineDataSet1.lineWidth = 2.5f
                        lineDataSet2.lineWidth = 2.5f

                        lineDataSet1.circleRadius = 4f
                        lineDataSet2.circleRadius = 4f

                        val colors = java.util.ArrayList<Int>()

                        colors.add(Color.rgb(236,9,62))
                        colors.add(Color.rgb(48,189,23))
                        colors.add(Color.rgb(255,233,41))

                        lineDataSet1.color = colors[1]
                        lineDataSet2.color = colors[0]

                        lineDataSet1.setCircleColor(colors[2])
                        lineDataSet2.setCircleColor(colors[2])
                        lineDataSet1.setDrawCircles(false)
                        lineDataSet2.setDrawCircles(false)
                        lineDataSet1.setDrawValues(false)
                        lineDataSet2.setDrawValues(false)

                        dataSets.add(lineDataSet1)
                        dataSets.add(lineDataSet2)

                        cl.animateX(2000)
                        val data = LineData(dataSets)
                        cl.data = data
                        cl.invalidate()

                        cl.isAutoScaleMinMaxEnabled = true;

                        counter = 0
                    }.addOnFailureListener {
                        Log.d("firebase", "Fail")
                    }
            }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val style = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return day.toString() + " " + getMonthFormat(month) + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 0) return "JAN"
        if (month == 1) return "FEB"
        if (month == 2) return "MAR"
        if (month == 3) return "APR"
        if (month == 4) return "MAY"
        if (month == 5) return "JUN"
        if (month == 6) return "JUL"
        if (month == 7) return "AUG"
        if (month == 8) return "SEP"
        if (month == 9) return "OCT"
        if (month == 10) return "NOV"
        return if (month == 11) "DEC" else "JAN"
    }

    fun openDatePicker(view: View?) {
        datePickerDialog.show()
    }
}