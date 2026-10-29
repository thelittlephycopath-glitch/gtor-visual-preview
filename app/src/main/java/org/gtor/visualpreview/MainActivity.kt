package org.gtor.visualpreview

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

/** UI-only GTor concept. All metrics are illustrative; no VPN, network, backend, account, analytics, or persistence. */
class MainActivity : Activity() {
    private lateinit var status: TextView
    private lateinit var statusDot: View
    private var previewActive = false
    private val bg = Color.rgb(8, 14, 19)
    private val panel = Color.rgb(17, 26, 33)
    private val panel2 = Color.rgb(22, 33, 41)
    private val accent = Color.rgb(133, 231, 205)
    private val muted = Color.rgb(145, 163, 173)
    private val white = Color.rgb(242, 248, 249)
    private val line = Color.rgb(39, 54, 63)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()

        val scroll = ScrollView(this).apply { setFillViewport(true); isVerticalScrollBarEnabled = false; setBackgroundColor(bg) }
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(20), dp(22), dp(24))
            setBackgroundColor(bg)
        }
        scroll.addView(root)

        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        val brandIcon = ImageView(this).apply {
            setImageResource(R.drawable.gtor_mask)
            setPadding(dp(4), dp(4), dp(4), dp(4))
            background = shape(panel2, line, 1, 15)
        }
        top.addView(brandIcon, LinearLayout.LayoutParams(dp(48), dp(48)))
        val brandText = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), 0, 0, 0)
            addView(text("GTOR", 20f, white, true))
            addView(text("PRIVATE BY DESIGN", 9f, muted, true).apply { letterSpacing = .13f })
        }
        top.addView(brandText, LinearLayout.LayoutParams(0, -2, 1f))
        top.addView(pill("UI CONCEPT", accent, Color.rgb(21, 49, 45)))
        root.addView(top, matchWrap())

        root.addView(space(30))
        root.addView(text("Your connection.", 29f, white, true))
        root.addView(space(6))
        root.addView(text("A calmer, clearer home for your privacy.", 14f, muted, false))
        root.addView(space(22))

        val hero = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = shape(panel, line, 1, 24)
            setPadding(dp(18), dp(18), dp(18), dp(18))
        }
        val heroTop = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        statusDot = View(this).apply { background = shape(accent, accent, 0, 50) }
        heroTop.addView(statusDot, LinearLayout.LayoutParams(dp(8), dp(8)))
        heroTop.addView(text("  CONNECTION STATUS", 10f, muted, true), LinearLayout.LayoutParams(0, -2, 1f))
        heroTop.addView(pill("PREVIEW", muted, panel2))
        hero.addView(heroTop, matchWrap())
        hero.addView(space(20))
        status = text("Ready when you are", 23f, white, true)
        hero.addView(status)
        hero.addView(space(7))
        hero.addView(text("Visual prototype only. Nothing connects.", 12f, muted, false))
        hero.addView(space(22))
        val action = text("START PREVIEW   ›", 14f, bg, true).apply {
            gravity = Gravity.CENTER
            letterSpacing = .04f
            background = shape(accent, accent, 1, 16)
            setPadding(dp(16), 0, dp(16), 0)
            isClickable = true
            isFocusable = true
            setOnClickListener {
                previewActive = !previewActive
                status.text = if (previewActive) "Preview is active" else "Ready when you are"
                text = if (previewActive) "RESET PREVIEW   ↺" else "START PREVIEW   ›"
                val dot = if (previewActive) Color.rgb(245, 194, 104) else accent
                statusDot.background = shape(dot, dot, 0, 50)
            }
        }
        hero.addView(action, LinearLayout.LayoutParams(-1, dp(54)))
        root.addView(hero, matchWrap())

        root.addView(space(22))
        val section = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        section.addView(text("AT A GLANCE", 11f, muted, true), LinearLayout.LayoutParams(0, -2, 1f))
        section.addView(text("SAMPLE UI DATA", 9f, muted, true))
        root.addView(section)
        root.addView(space(10))
        val metrics = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        metrics.addView(metric("ROUTE", "Multi-hop", "Concept preview"), LinearLayout.LayoutParams(0, dp(108), 1f))
        metrics.addView(spaceWidth(10))
        metrics.addView(metric("PROTECTION", "On-device", "Illustrative label"), LinearLayout.LayoutParams(0, dp(108), 1f))
        root.addView(metrics, matchWrap())

        root.addView(space(18))
        root.addView(text("PREFERRED REGION", 11f, muted, true))
        root.addView(space(10))
        val region = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            background = shape(panel, line, 1, 18)
            setPadding(dp(15), dp(14), dp(15), dp(14))
        }
        region.addView(text("◎", 27f, accent, true).apply { gravity = Gravity.CENTER }, LinearLayout.LayoutParams(dp(42), dp(42)))
        val regionText = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(10), 0, 0, 0)
            addView(text("Automatic region", 15f, white, true))
            addView(space(4))
            addView(text("Smart selection · concept only", 11f, muted, false))
        }
        region.addView(regionText, LinearLayout.LayoutParams(0, -2, 1f))
        region.addView(text("›", 26f, muted, false))
        root.addView(region, matchWrap())

        root.addView(space(18))
        val privacy = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            background = shape(Color.rgb(14, 34, 32), Color.rgb(29, 67, 59), 1, 16)
            setPadding(dp(14), dp(13), dp(14), dp(13))
        }
        privacy.addView(ImageView(this).apply { setImageResource(R.drawable.gtor_mask) }, LinearLayout.LayoutParams(dp(30), dp(30)))
        privacy.addView(text("  No account · No backend · No connection", 11f, accent, true))
        root.addView(privacy, matchWrap())
        root.addView(space(22))
        root.addView(text("DESIGNED FOR QUIET CONFIDENCE", 9f, muted, true).apply { gravity = Gravity.CENTER; letterSpacing = .12f })
        root.addView(space(6))
        root.addView(text("GTor  ·  UI/UX sandbox only", 11f, muted, false).apply { gravity = Gravity.CENTER })
        setContentView(scroll)
    }

    private fun metric(label: String, value: String, note: String) = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        background = shape(panel, line, 1, 18)
        setPadding(dp(13), dp(13), dp(10), dp(10))
        addView(text(label, 9f, muted, true).apply { letterSpacing = .08f })
        addView(space(10))
        addView(text(value, 15f, white, true))
        addView(space(4))
        addView(text(note, 9f, muted, false))
    }
    private fun text(value: String, size: Float, color: Int, bold: Boolean) = TextView(this).apply {
        text = value
        textSize = size
        setTextColor(color)
        typeface = if (bold) Typeface.create("sans-serif", Typeface.BOLD) else Typeface.create("sans-serif", Typeface.NORMAL)
    }
    private fun pill(value: String, fg: Int, fill: Int) = text(value, 9f, fg, true).apply {
        gravity = Gravity.CENTER
        letterSpacing = .05f
        setPadding(dp(10), dp(8), dp(10), dp(8))
        background = shape(fill, fill, 1, 50)
    }
    private fun shape(fill: Int, stroke: Int, strokeDp: Int, radiusDp: Int) = GradientDrawable().apply {
        setColor(fill)
        cornerRadius = dp(radiusDp).toFloat()
        if (strokeDp > 0) setStroke(dp(strokeDp), stroke)
    }
    private fun space(heightDp: Int) = View(this).apply { layoutParams = LinearLayout.LayoutParams(1, dp(heightDp)) }
    private fun spaceWidth(widthDp: Int) = View(this).apply { layoutParams = LinearLayout.LayoutParams(dp(widthDp), 1) }
    private fun matchWrap() = LinearLayout.LayoutParams(-1, -2)
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
}
