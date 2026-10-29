package org.gtor.visualpreview

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

/**
 * GTor product-design sandbox. Buttons and choices only change local preview state.
 * No VPN service, tunnel, network access, account, backend, analytics, or persistence exists here.
 */
class MainActivity : Activity() {
    private lateinit var contentHost: LinearLayout
    private lateinit var bottomNav: LinearLayout
    private var currentTab = 0
    private var detailPage: String? = null
    private var previewActive = false
    private var protectApps = true
    private var selectedRegion = "Automatic region"
    private var bridgeMode = "Snowflake"
    private var themeChoice = "Dark"

    private lateinit var statusText: TextView
    private lateinit var statusDot: View

    private val bg = Color.rgb(8, 14, 19)
    private val panel = Color.rgb(17, 26, 33)
    private val panel2 = Color.rgb(22, 33, 41)
    private val accent = Color.rgb(133, 231, 205)
    private val muted = Color.rgb(145, 163, 173)
    private val white = Color.rgb(242, 248, 249)
    private val line = Color.rgb(39, 54, 63)

    @Deprecated("Deprecated in Android API; retained for broad preview-device compatibility")
    override fun onBackPressed() {
        if (detailPage != null) {
            detailPage = null
            currentTab = 2
            renderPage()
            return
        }
        if (currentTab != 0) {
            currentTab = 0
            renderPage()
            return
        }
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()

        val appRoot = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
        }
        appRoot.addView(createHeader(), matchWrap())
        contentHost = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
        }
        appRoot.addView(contentHost, LinearLayout.LayoutParams(-1, 0, 1f))
        bottomNav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(12), dp(8), dp(12), dp(8))
            background = shape(Color.rgb(11, 19, 25), line, 1, 20)
        }
        val navHolder = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(10), dp(4), dp(10), dp(6))
            setBackgroundColor(bg)
        }
        navHolder.addView(bottomNav, LinearLayout.LayoutParams(-1, dp(62)))
        appRoot.addView(navHolder, matchWrap())
        setContentView(appRoot)
        renderPage()
    }

    private fun createHeader() = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setPadding(dp(22), dp(17), dp(22), dp(12))
        setBackgroundColor(bg)
        val logo = ImageView(this@MainActivity).apply {
            setImageResource(R.mipmap.ic_launcher)
            contentDescription = "GTor white-mask logo"
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        addView(logo, LinearLayout.LayoutParams(dp(46), dp(46)))
        val brand = LinearLayout(this@MainActivity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(11), 0, 0, 0)
            addView(text("GTOR", 19f, white, true))
            addView(text("PRIVATE BY DESIGN", 9f, muted, true).apply { letterSpacing = .14f })
        }
        addView(brand, LinearLayout.LayoutParams(0, -2, 1f))
        addView(pill("UI CONCEPT", accent, Color.rgb(21, 49, 45)))
    }

    private fun renderPage() {
        contentHost.removeAllViews()
        val page = when {
            detailPage != null -> buildDetailPage(detailPage!!)
            currentTab == 1 -> buildRegionPage()
            currentTab == 2 -> buildSettingsPage()
            else -> buildHomePage()
        }
        val scroll = ScrollView(this).apply {
            isFillViewport = true
            isVerticalScrollBarEnabled = false
            setBackgroundColor(bg)
        }
        scroll.addView(page)
        contentHost.addView(scroll, LinearLayout.LayoutParams(-1, -1))
        renderBottomNav()
    }

    private fun buildHomePage() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(22), dp(13), dp(22), dp(22))

        addView(text("Your connection.", 29f, white, true))
        addView(space(5))
        addView(text("A calmer, clearer home for your privacy.", 14f, muted, false))
        addView(space(21))

        val hero = card(padding = 18, radius = 24)
        val heroTop = LinearLayout(this@MainActivity).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        statusDot = View(this@MainActivity).apply { background = shape(if (previewActive) Color.rgb(245, 194, 104) else accent, if (previewActive) Color.rgb(245, 194, 104) else accent, 0, 50) }
        heroTop.addView(statusDot, LinearLayout.LayoutParams(dp(8), dp(8)))
        heroTop.addView(text("  CONNECTION STATUS", 10f, muted, true), LinearLayout.LayoutParams(0, -2, 1f))
        heroTop.addView(pill("LOCAL DEMO", muted, panel2))
        hero.addView(heroTop, matchWrap())
        hero.addView(space(19))
        statusText = text(if (previewActive) "Preview is active" else "Ready when you are", 23f, white, true)
        hero.addView(statusText)
        hero.addView(space(7))
        hero.addView(text("A visual prototype. No connection is made.", 12f, muted, false))
        hero.addView(space(22))
        val action = text(if (previewActive) "RESET PREVIEW     ↺" else "START PREVIEW     ›", 14f, bg, true).apply {
            gravity = Gravity.CENTER
            letterSpacing = .05f
            background = shape(accent, accent, 1, 16)
            setPadding(dp(16), 0, dp(16), 0)
            isClickable = true
            isFocusable = true
            setOnClickListener {
                previewActive = !previewActive
                statusText.text = if (previewActive) "Preview is active" else "Ready when you are"
                this.text = if (previewActive) "RESET PREVIEW     ↺" else "START PREVIEW     ›"
                val dot = if (previewActive) Color.rgb(245, 194, 104) else accent
                statusDot.background = shape(dot, dot, 0, 50)
            }
        }
        hero.addView(action, LinearLayout.LayoutParams(-1, dp(54)))
        addView(hero, matchWrap())

        addView(space(22))
        addView(sectionTitle("AT A GLANCE", "SAMPLE UI DATA"), matchWrap())
        addView(space(10))
        val metrics = LinearLayout(this@MainActivity).apply { orientation = LinearLayout.HORIZONTAL }
        metrics.addView(metric("ROUTE", "Multi-hop", "Concept preview"), LinearLayout.LayoutParams(0, dp(112), 1f))
        metrics.addView(spaceWidth(10))
        metrics.addView(metric("PROTECTION", if (protectApps) "Selected apps" else "Off", "Illustrative label"), LinearLayout.LayoutParams(0, dp(112), 1f))
        addView(metrics, matchWrap())

        addView(space(20))
        addView(text("PREFERRED REGION", 11f, muted, true))
        addView(space(9))
        addView(settingRow("◎", selectedRegion, "Tap to browse sample regions", "›") {
            detailPage = null
            currentTab = 1
            renderPage()
        }, matchWrap())

        addView(space(12))
        addView(text("QUICK SETTINGS", 11f, muted, true))
        addView(space(9))
        addView(settingRow("▦", "App routing", "Choose which apps would be included", "›") {
            detailPage = "App routing"
            currentTab = 2
            renderPage()
        }, matchWrap())
        addView(settingRow("◇", "Bridge options", "Connection-method screen concept", "›") {
            detailPage = "Bridges"
            currentTab = 2
            renderPage()
        }, matchWrap())
        addView(space(8))
        addView(privacyNote(), matchWrap())
        addView(space(18))
        addView(footer(), matchWrap())
    }

    private fun buildRegionPage() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(22), dp(13), dp(22), dp(24))
        addView(text("Choose a region", 28f, white, true))
        addView(space(6))
        addView(text("Preview how location selection could look. These are sample choices, not live exits.", 13f, muted, false))
        addView(space(20))
        val regions = listOf(
            Triple("Automatic region", "Recommended selection", "◎"),
            Triple("Germany", "Example region · Frankfurt", "DE"),
            Triple("Netherlands", "Example region · Amsterdam", "NL"),
            Triple("Sweden", "Example region · Stockholm", "SE"),
            Triple("Japan", "Example region · Tokyo", "JP"),
            Triple("United States", "Example region · New York", "US"),
            Triple("France", "Example region · Paris", "FR")
        )
        val regionList = LinearLayout(this@MainActivity).apply { orientation = LinearLayout.VERTICAL }
        val search = EditText(this@MainActivity).apply {
            hint = "Search sample regions"
            setHintTextColor(muted)
            setTextColor(white)
            textSize = 14f
            singleLine = true
            setPadding(dp(16), dp(14), dp(16), dp(14))
            background = shape(panel, line, 1, 16)
            contentDescription = "Search sample regions"
        }
        addView(search, LinearLayout.LayoutParams(-1, dp(52)))
        addView(space(12))
        addView(regionList, matchWrap())
        fun showRegions(query: String) {
            regionList.removeAllViews()
            val matches = regions.filter {
                it.first.contains(query.trim(), ignoreCase = true) ||
                    it.second.contains(query.trim(), ignoreCase = true)
            }
            if (matches.isEmpty()) {
                regionList.addView(infoCard("No sample regions found", "Try another region name."), matchWrap())
            } else {
                matches.forEach { item ->
                    val selected = selectedRegion == item.first
                    val row = settingRow(item.third, item.first, item.second, if (selected) "✓" else "›", accentIfSelected = selected) {
                        selectedRegion = item.first
                        renderPage()
                    }
                    regionList.addView(row, matchWrap())
                }
            }
        }
        showRegions("")
        search.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showRegions(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
        addView(space(8))
        addView(privacyNote(), matchWrap())
        addView(space(18))
        addView(footer(), matchWrap())
    }

    private fun buildSettingsPage() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(22), dp(13), dp(22), dp(24))
        addView(text("Settings", 29f, white, true))
        addView(space(6))
        addView(text("A preview of the settings structure for a fuller GTor app.", 14f, muted, false))
        addView(space(21))
        addView(text("CONNECTION", 10f, muted, true))
        addView(space(8))
        addView(settingRow("◎", "Region selection", selectedRegion, "›") { detailPage = null; currentTab = 1; renderPage() }, matchWrap())
        addView(settingRow("◇", "Bridges", bridgeMode, "›") { detailPage = "Bridges"; renderPage() }, matchWrap())
        addView(settingRow("▦", "App routing", if (protectApps) "Enabled in preview" else "Disabled in preview", "›") { detailPage = "App routing"; renderPage() }, matchWrap())
        addView(space(17))
        addView(text("PREFERENCES", 10f, muted, true))
        addView(space(8))
        addView(settingRow("◐", "Appearance", themeChoice, "›") { detailPage = "Appearance"; renderPage() }, matchWrap())
        addView(settingRow("⌁", "Privacy and safety", "Design principles", "›") { detailPage = "Privacy and safety"; renderPage() }, matchWrap())
        addView(settingRow("ⓘ", "About GTor", "Version 0.1 · UI preview", "›") { detailPage = "About GTor"; renderPage() }, matchWrap())
        addView(space(18))
        addView(privacyNote(), matchWrap())
        addView(space(18))
        addView(footer(), matchWrap())
    }

    private fun buildDetailPage(title: String) = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(22), dp(13), dp(22), dp(24))
        val back = text("‹   SETTINGS", 11f, accent, true).apply {
            letterSpacing = .08f
            setPadding(0, dp(6), 0, dp(12))
            isClickable = true
            setOnClickListener { detailPage = null; currentTab = 2; renderPage() }
        }
        addView(back)
        addView(text(title, 28f, white, true))
        addView(space(7))
        val description = when (title) {
            "App routing" -> "Imagine choosing which apps would use the protected route. All choices here are local preview state."
            "Bridges" -> "A layout concept for connection methods. Nothing is configured or connected."
            "Appearance" -> "Preview a preference screen for display options."
            "Privacy and safety" -> "The real product should make protection state and data handling clear."
            else -> "Product information and build details for the GTor visual concept."
        }
        addView(text(description, 13f, muted, false))
        addView(space(20))
        when (title) {
            "App routing" -> {
                addView(settingRow("▦", "Protect selected apps", if (protectApps) "On in preview" else "Off in preview", if (protectApps) "ON" else "OFF") {
                    protectApps = !protectApps
                    renderPage()
                }, matchWrap())
                addView(space(10))
                addView(settingRow("＋", "Choose apps", "Example app list", "›") {
                    showLocalMessage("App chooser is a UI mock; installed apps are not queried.")
                }, matchWrap())
            }
            "Bridges" -> {
                listOf("Off", "Snowflake", "Obfs4").forEach { mode ->
                    addView(settingRow("◇", mode, when (mode) {
                        "Off" -> "No bridge selected in preview"
                        "Snowflake" -> "Helps illustrate a bridge option"
                        else -> "Alternative bridge option"
                    }, if (bridgeMode == mode) "✓" else "›", accentIfSelected = bridgeMode == mode) {
                        bridgeMode = mode
                        renderPage()
                    }, matchWrap())
                    addView(space(8))
                }
            }
            "Appearance" -> {
                addView(settingRow("◐", "Display mode", themeChoice, "Change") {
                    themeChoice = if (themeChoice == "Dark") "System" else "Dark"
                    renderPage()
                }, matchWrap())
                addView(space(8))
                addView(text("This selection is stored only while the preview process is open; it does not change Android system appearance.", 11f, muted, false))
            }
            "Privacy and safety" -> {
                addView(infoCard("No background activity", "The prototype runs no tunnel or background service."))
                addView(space(10))
                addView(infoCard("No data collection", "No account, analytics, remote API, or telemetry is implemented."))
                addView(space(10))
                addView(infoCard("Preview data only", "Region, bridge, and app choices are illustrative UI state."))
            }
            else -> {
                val logo = ImageView(this@MainActivity).apply {
                    setImageResource(R.mipmap.ic_launcher)
                    contentDescription = "GTor white-mask logo"
                }
                val logoWrap = LinearLayout(this@MainActivity).apply { gravity = Gravity.CENTER; addView(logo, LinearLayout.LayoutParams(dp(88), dp(88))) }
                addView(logoWrap, matchWrap())
                addView(space(15))
                addView(infoCard("GTor Visual Preview", "Version 0.2-preview"))
                addView(space(10))
                addView(infoCard("Build type", "Android debug · UI/UX sandbox"))
                addView(space(10))
                addView(infoCard("Product boundary", "This app is a visual concept, not a working VPN client."))
            }
        }
        addView(space(20))
        addView(privacyNote(), matchWrap())
        addView(space(18))
        addView(footer(), matchWrap())
    }

    private fun renderBottomNav() {
        bottomNav.removeAllViews()
        val items = listOf(Triple("⌂", "Home", 0), Triple("◎", "Regions", 1), Triple("⚙", "Settings", 2))
        items.forEach { item ->
            val selected = (detailPage == null && currentTab == item.third) || (detailPage != null && item.third == 2)
            val navItem = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                background = shape(if (selected) Color.rgb(25, 44, 47) else Color.TRANSPARENT,
                    if (selected) Color.rgb(25, 44, 47) else Color.TRANSPARENT, 0, 14)
                setPadding(dp(6), dp(5), dp(6), dp(5))
                isClickable = true
                isFocusable = true
                addView(text(item.first, 20f, if (selected) accent else muted, true).apply { gravity = Gravity.CENTER })
                addView(text(item.second, 10f, if (selected) accent else muted, selected).apply { gravity = Gravity.CENTER })
                setOnClickListener {
                    currentTab = item.third
                    detailPage = null
                    renderPage()
                }
            }
            bottomNav.addView(navItem, LinearLayout.LayoutParams(0, -1, 1f).apply {
                marginStart = dp(3); marginEnd = dp(3)
            })
        }
    }

    private fun settingRow(icon: String, title: String, subtitle: String, trailing: String,
                           accentIfSelected: Boolean = false, onClick: () -> Unit) =
        LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            background = shape(if (accentIfSelected) Color.rgb(18, 39, 37) else panel,
                if (accentIfSelected) Color.rgb(51, 104, 91) else line, 1, 17)
            setPadding(dp(13), dp(13), dp(13), dp(13))
            isClickable = true
            isFocusable = true
            val iconView = text(icon, 22f, if (accentIfSelected) accent else muted, true).apply { gravity = Gravity.CENTER }
            addView(iconView, LinearLayout.LayoutParams(dp(36), dp(38)))
            val labels = LinearLayout(this@MainActivity).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(10), 0, dp(6), 0)
                addView(text(title, 14f, white, true))
                addView(space(4))
                addView(text(subtitle, 11f, muted, false))
            }
            addView(labels, LinearLayout.LayoutParams(0, -2, 1f))
            addView(text(trailing, if (trailing.length > 2) 9f else 19f,
                if (accentIfSelected) accent else muted, true).apply { gravity = Gravity.CENTER })
            setOnClickListener { onClick() }
        }.apply {
            layoutParams = LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = dp(8) }
        }

    private fun infoCard(title: String, detail: String) = card(padding = 15, radius = 16).apply {
        addView(text(title, 14f, white, true))
        addView(space(5))
        addView(text(detail, 12f, muted, false))
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

    private fun sectionTitle(label: String, detail: String) = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        addView(text(label, 11f, muted, true), LinearLayout.LayoutParams(0, -2, 1f))
        addView(text(detail, 9f, muted, true))
    }

    private fun privacyNote() = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        background = shape(Color.rgb(14, 34, 32), Color.rgb(29, 67, 59), 1, 16)
        setPadding(dp(13), dp(11), dp(13), dp(11))
        addView(ImageView(this@MainActivity).apply {
            setImageResource(R.drawable.gtor_mask)
            contentDescription = null
        }, LinearLayout.LayoutParams(dp(29), dp(29)))
        addView(text("  PREVIEW ONLY  ·  NO BACKEND  ·  NO CONNECTION", 9f, accent, true))
    }

    private fun footer() = text("GTor  ·  UI/UX sandbox only", 11f, muted, false).apply {
        gravity = Gravity.CENTER
        setPadding(0, dp(3), 0, dp(5))
    }

    private fun card(padding: Int, radius: Int) = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        background = shape(panel, line, 1, radius)
        setPadding(dp(padding), dp(padding), dp(padding), dp(padding))
    }

    private fun showLocalMessage(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
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
