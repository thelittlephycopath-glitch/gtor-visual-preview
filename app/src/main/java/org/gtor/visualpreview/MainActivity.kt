package org.gtor.visualpreview

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Visual-only preview shell. It deliberately has no VPN service, network access,
 * tunnel controls, analytics, or persistence. The central control changes local UI text only.
 */
class MainActivity : Activity() {
    private lateinit var action: TextView
    private lateinit var status: TextView
    private var previewActive = false

    private val bg = Color.rgb(9, 16, 22)
    private val panel = Color.rgb(17, 28, 36)
    private val accent = Color.rgb(130, 224, 202)
    private val muted = Color.rgb(145, 164, 174)
    private val white = Color.rgb(241, 248, 249)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() and
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(26), dp(30), dp(26), dp(24))
            setBackgroundColor(bg)
        }

        val topRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val brand = text("GTOR", 25f, white, true).apply { letterSpacing = 0.16f }
        topRow.addView(brand, LinearLayout.LayoutParams(0, dp(48), 1f))
        topRow.addView(pill("VISUAL PREVIEW", accent, Color.rgb(20, 53, 51)))
        root.addView(topRow, matchWrap())

        root.addView(space(44))

        val orb = TextView(this).apply {
            text = "G"
            gravity = Gravity.CENTER
            textSize = 42f
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            setTextColor(white)
            background = shape(Color.rgb(24, 47, 53), accent, 2, 100)
            elevation = dp(12).toFloat()
        }
        root.addView(orb, LinearLayout.LayoutParams(dp(116), dp(116)).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        })

        root.addView(space(30))
        root.addView(text("Your quiet space.", 28f, white, true).apply {
            gravity = Gravity.CENTER
        }, matchWrap())
        root.addView(space(10))
        root.addView(text(
            "A visual shell for exploring the GTor connection screen.",
            15f, muted, false
        ).apply {
            gravity = Gravity.CENTER
            setLineSpacing(dp(4).toFloat(), 1f)
        }, matchWrap())

        root.addView(space(36))
        val statusCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            background = shape(panel, Color.rgb(33, 49, 57), 1, 22)
            setPadding(dp(18), dp(16), dp(18), dp(16))
        }
        statusCard.addView(text("PREVIEW STATUS", 11f, muted, true).apply {
            letterSpacing = 0.14f
        })
        status = text("Ready to preview", 17f, white, true).apply {
            gravity = Gravity.CENTER
        }
        statusCard.addView(space(8))
        statusCard.addView(status, matchWrap())
        root.addView(statusCard, matchWidth())

        root.addView(space(28))
        action = text("CONNECT", 15f, bg, true).apply {
            gravity = Gravity.CENTER
            letterSpacing = 0.12f
            background = shape(accent, accent, 1, 28)
            elevation = dp(5).toFloat()
            isClickable = true
            isFocusable = true
            setPadding(dp(20), 0, dp(20), 0)
            setOnClickListener {
                previewActive = !previewActive
                status.text = if (previewActive) "Preview interaction active" else "Ready to preview"
                text = if (previewActive) "RESET PREVIEW" else "CONNECT"
            }
        }
        root.addView(action, LinearLayout.LayoutParams(-1, dp(58)))

        root.addView(space(16))
        root.addView(text(
            "DEMO ONLY  •  NO VPN TUNNEL  •  NO NETWORK ACCESS",
            10f, muted, true
        ).apply {
            gravity = Gravity.CENTER
            letterSpacing = 0.04f
        }, matchWrap())

        val spacer = View(this)
        root.addView(spacer, LinearLayout.LayoutParams(1, 0, 1f))
        root.addView(text("UI sandbox · no backend connected", 12f, muted, false).apply {
            gravity = Gravity.CENTER
        }, matchWrap())

        setContentView(root)
    }

    private fun text(value: String, size: Float, color: Int, bold: Boolean) =
        TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            typeface = if (bold) Typeface.create("sans-serif", Typeface.BOLD)
                else Typeface.create("sans-serif", Typeface.NORMAL)
        }

    private fun pill(value: String, fg: Int, fill: Int) = text(value, 10f, fg, true).apply {
        gravity = Gravity.CENTER
        letterSpacing = 0.06f
        setPadding(dp(12), dp(9), dp(12), dp(9))
        background = shape(fill, fill, 1, 50)
    }

    private fun shape(fill: Int, stroke: Int, strokeDp: Int, radiusDp: Int) =
        GradientDrawable().apply {
            setColor(fill)
            cornerRadius = dp(radiusDp).toFloat()
            if (strokeDp > 0) setStroke(dp(strokeDp), stroke)
        }

    private fun space(heightDp: Int) = View(this).apply {
        layoutParams = LinearLayout.LayoutParams(1, dp(heightDp))
    }

    private fun matchWrap() = LinearLayout.LayoutParams(-1, -2)
    private fun matchWidth() = LinearLayout.LayoutParams(-1, -2)
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
}
