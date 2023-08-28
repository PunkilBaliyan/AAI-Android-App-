package com.punkil.airportauthorityofindia

import SlideAdapter
import SlidePagerTransformer
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView


import com.google.android.material.navigation.NavigationView
import com.punkil.airportauthorityofindia.ui.login
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navigationView : NavigationView
    private lateinit var viewPager: ViewPager2
    private lateinit var slideAdapter: SlideAdapter
    private val slideChangeInterval: Long = 6000
    private lateinit var bottomNavigationView: BottomNavigationView

    private val timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout  = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navigationView.bringToFront()
        val toggle =  ActionBarDrawerToggle(this,drawerLayout,R.string.action_sign_in,R.string.continue_as_guest)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        viewPager = findViewById(R.id.viewPager)
        slideAdapter = SlideAdapter(this)
        viewPager.adapter = slideAdapter
        val dotsLayout = findViewById<LinearLayout>(R.id.dotsLayout)
        addDots(dotsLayout, slideAdapter.itemCount)

        val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            val newPosition = (position + 1) % slideAdapter.itemCount
                            viewPager.currentItem = newPosition
                        }
                    }
                }, slideChangeInterval)
            }
        }
        viewPager.registerOnPageChangeCallback(pageChangeCallback)
        viewPager.currentItem = 0


        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    viewPager.currentItem = (viewPager.currentItem + 1) % slideAdapter.itemCount
                }
            }
        }, slideChangeInterval, slideChangeInterval)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_notices -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aai.aero/en/what-s-new"))
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_latest_tender -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aai.aero/en/tender/tender-search"))
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_home -> {
                    val intent = Intent(this,MainActivity::class.java)
                    this.startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_important_links -> {
                    val intent = Intent(this,ImportantLinksActivity::class.java)
                    this.startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_emp_login -> {
                    val intent = Intent(this,login::class.java)
                    this.startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
        val actionBar = supportActionBar
        actionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        actionBar?.setCustomView(R.layout.custom_action_bar)

        val customActionBar = actionBar?.customView
        val actionBarMenu = customActionBar?.findViewById<ImageView>(R.id.actionBarMenu)

        actionBarMenu?.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val photo1ImageView: ImageView = findViewById(R.id.photo1)
        val photo2ImageView: ImageView = findViewById(R.id.photo2)

        Glide.with(this)
            .load("https://www.aai.aero/sites/default/files/photo_gallery/IMG-20230605-WA0013.jpg")
            .into(photo1ImageView)

        Glide.with(this)
            .load("https://www.aai.aero/sites/default/files/photo_gallery/_V0A7454.JPG")
            .into(photo2ImageView)


    }
    private fun addDots(dotsLayout: LinearLayout, count: Int) {
        val dots = arrayOfNulls<ImageView>(count)

        for (i in 0 until count) {
            dots[i] = ImageView(this)
            dots[i]?.setImageResource(R.drawable.dot_selector)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            dotsLayout.addView(dots[i], params)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }


    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
        super.onBackPressed()}
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        hideAllSubItems()

        when (item.itemId) {
            R.id.nav_home -> { println("Home Clicked")
                val intent = Intent(this,MainActivity::class.java)
                this.startActivity(intent)}

            R.id.nav_about_us -> {
                toggleSubMenuVisibility(R.id.nav_aai_at_glance)
                toggleSubMenuVisibility(R.id.nav_mission)
                toggleSubMenuVisibility(R.id.nav_aai_board)
                toggleSubMenuVisibility(R.id.nav_Org_structure)
                toggleSubMenuVisibility(R.id.nav_chairman_message)
                toggleSubMenuVisibility(R.id.nav_aai_corporate_film)
            }

            R.id.nav_business_info -> {
                toggleSubMenuVisibility(R.id.nav_traffic_news)
                toggleSubMenuVisibility(R.id.nav_current_rates)
                toggleSubMenuVisibility(R.id.nav_annual_reports)
                toggleSubMenuVisibility(R.id.nav_aai_moca)
                toggleSubMenuVisibility(R.id.nav_Traffic_survey)
                toggleSubMenuVisibility(R.id.nav_indian_airports)
            }

            R.id.nav_services -> {
                toggleSubMenuVisibility(R.id.nav_passenger_facility)
                toggleSubMenuVisibility(R.id.nav_air_navigation_services)
                toggleSubMenuVisibility(R.id.nav_airports_services)
                toggleSubMenuVisibility(R.id.nav_fire_services)
                toggleSubMenuVisibility(R.id.nav_project_services)

            }

            R.id.nav_tender -> {
                toggleSubMenuVisibility(R.id.nav_tender_search)
                toggleSubMenuVisibility(R.id.nav_e_tender)
                toggleSubMenuVisibility(R.id.nav_notice_inviting_tender)
                toggleSubMenuVisibility(R.id.nav_restaurant_tender)
                toggleSubMenuVisibility(R.id.nav_contract_award)
                toggleSubMenuVisibility(R.id.nav_tender_status)
                toggleSubMenuVisibility(R.id.nav_impediment_external_monitors)

            }

            R.id.nav_vigilance -> {
                toggleSubMenuVisibility(R.id.nav_about_vigilance)
                toggleSubMenuVisibility(R.id.nav_publications)
                toggleSubMenuVisibility(R.id.nav_system_improvement)
                toggleSubMenuVisibility(R.id.nav_integrity_pact)
                toggleSubMenuVisibility(R.id.nav_do_not_pay_bribe)
                toggleSubMenuVisibility(R.id.nav_photo_gallery)
                toggleSubMenuVisibility(R.id.nav_contact_us)
            }

           R.id.nav_public_info -> {
               toggleSubMenuVisibility(R.id.nav_citizen_charter)
               toggleSubMenuVisibility(R.id.nav_right_to_information)
               toggleSubMenuVisibility(R.id.nav_public_grievances)
               toggleSubMenuVisibility(R.id.nav_public_documents)
               toggleSubMenuVisibility(R.id.nav_sports_control_board)
               toggleSubMenuVisibility(R.id.nav_major_procurement_projections)
               toggleSubMenuVisibility(R.id.nav_rules_policies)
               toggleSubMenuVisibility(R.id.nav_iso_certifications)
               toggleSubMenuVisibility(R.id.nav_best_practices)
               toggleSubMenuVisibility(R.id.nav_dashboard_backlog_vacancies)
           }

            R.id.nav_csr -> {
                toggleSubMenuVisibility(R.id.nav_about_aai_csr)
                toggleSubMenuVisibility(R.id.nav_current_csr_committee)
                toggleSubMenuVisibility(R.id.nav_awards_accolades)
                toggleSubMenuVisibility(R.id.nav_csr_policy)
                toggleSubMenuVisibility(R.id.nav_csr_dpe_theme)
                toggleSubMenuVisibility(R.id.nav_csr_annual_plan)
                toggleSubMenuVisibility(R.id.nav_case_stories)
                toggleSubMenuVisibility(R.id.nav_circular_mom_letters)
                toggleSubMenuVisibility(R.id.nav_swachh_vidyalaya_campaign)
                toggleSubMenuVisibility(R.id.nav_csr_programmes)
                toggleSubMenuVisibility(R.id.nav_csr_photo)
                toggleSubMenuVisibility(R.id.nav_media_coverage)
                toggleSubMenuVisibility(R.id.nav_csr_contact_us)

            }
            R.id.nav_aai_airports-> {
                toggleSubMenuVisibility(R.id.nav_eastern_region)
                toggleSubMenuVisibility(R.id.nav_northeastern_region)
                toggleSubMenuVisibility(R.id.nav_northern_region)
                toggleSubMenuVisibility(R.id.nav_southern_region)
                toggleSubMenuVisibility(R.id.nav_western_region)
                toggleSubMenuVisibility(R.id.nav_chennai)
                toggleSubMenuVisibility(R.id.nav_kolkata)
            }
        }


        return true
    }
    private fun hideAllSubItems() {
        navigationView.menu.findItem(R.id.nav_aai_at_glance).isVisible = false
        navigationView.menu.findItem(R.id.nav_mission).isVisible = false
        navigationView.menu.findItem(R.id.nav_aai_board).isVisible = false
        navigationView.menu.findItem(R.id.nav_Org_structure).isVisible = false
        navigationView.menu.findItem(R.id.nav_chairman_message).isVisible = false
        navigationView.menu.findItem(R.id.nav_aai_corporate_film).isVisible = false

        navigationView.menu.findItem(R.id.nav_traffic_news).isVisible = false
        navigationView.menu.findItem(R.id.nav_current_rates).isVisible = false
        navigationView.menu.findItem(R.id.nav_annual_reports).isVisible = false
        navigationView.menu.findItem(R.id.nav_aai_moca).isVisible = false
        navigationView.menu.findItem(R.id.nav_Traffic_survey).isVisible = false
        navigationView.menu.findItem(R.id.nav_indian_airports).isVisible = false

        navigationView.menu.findItem(R.id.nav_passenger_facility).isVisible = false
        navigationView.menu.findItem(R.id.nav_air_navigation_services).isVisible = false
        navigationView.menu.findItem(R.id.nav_airports_services).isVisible = false
        navigationView.menu.findItem(R.id.nav_fire_services).isVisible = false
        navigationView.menu.findItem(R.id.nav_project_services).isVisible = false

        navigationView.menu.findItem(R.id.nav_tender_search).isVisible = false
        navigationView.menu.findItem(R.id.nav_e_tender).isVisible = false
        navigationView.menu.findItem(R.id.nav_notice_inviting_tender).isVisible = false
        navigationView.menu.findItem(R.id.nav_restaurant_tender).isVisible = false
        navigationView.menu.findItem(R.id.nav_contract_award).isVisible = false
        navigationView.menu.findItem(R.id.nav_tender_status).isVisible = false
        navigationView.menu.findItem(R.id.nav_impediment_external_monitors).isVisible = false

        navigationView.menu.findItem(R.id.nav_about_vigilance).isVisible = false
        navigationView.menu.findItem(R.id.nav_publications).isVisible = false
        navigationView.menu.findItem(R.id.nav_system_improvement).isVisible = false
        navigationView.menu.findItem(R.id.nav_integrity_pact).isVisible = false
        navigationView.menu.findItem(R.id.nav_do_not_pay_bribe).isVisible = false
        navigationView.menu.findItem(R.id.nav_photo_gallery).isVisible = false
        navigationView.menu.findItem(R.id.nav_contact_us).isVisible = false


        navigationView.menu.findItem(R.id.nav_citizen_charter).isVisible = false
        navigationView.menu.findItem(R.id.nav_right_to_information).isVisible = false
        navigationView.menu.findItem(R.id.nav_public_grievances).isVisible = false
        navigationView.menu.findItem(R.id.nav_public_documents).isVisible = false

        navigationView.menu.findItem(R.id.nav_sports_control_board).isVisible = false
        navigationView.menu.findItem(R.id.nav_major_procurement_projections).isVisible = false

        navigationView.menu.findItem(R.id.nav_best_practices).isVisible = false
        navigationView.menu.findItem(R.id.nav_dashboard_backlog_vacancies).isVisible = false
        navigationView.menu.findItem(R.id.nav_rules_policies).isVisible = false
        navigationView.menu.findItem(R.id.nav_iso_certifications).isVisible = false

        navigationView.menu.findItem(R.id.nav_about_aai_csr).isVisible = false
        navigationView.menu.findItem(R.id.nav_current_csr_committee).isVisible = false
        navigationView.menu.findItem(R.id.nav_awards_accolades).isVisible = false
        navigationView.menu.findItem(R.id.nav_csr_policy).isVisible = false
        navigationView.menu.findItem(R.id.nav_csr_dpe_theme).isVisible = false
        navigationView.menu.findItem(R.id.nav_csr_annual_plan).isVisible = false
        navigationView.menu.findItem(R.id.nav_case_stories).isVisible = false
        navigationView.menu.findItem(R.id.nav_circular_mom_letters).isVisible = false
        navigationView.menu.findItem(R.id.nav_swachh_vidyalaya_campaign).isVisible = false
        navigationView.menu.findItem(R.id.nav_csr_programmes).isVisible = false
        navigationView.menu.findItem(R.id.nav_csr_photo).isVisible = false
        navigationView.menu.findItem(R.id.nav_media_coverage).isVisible = false
        navigationView.menu.findItem(R.id.nav_csr_contact_us).isVisible = false

        navigationView.menu.findItem(R.id.nav_eastern_region).isVisible = false
        navigationView.menu.findItem(R.id.nav_northeastern_region).isVisible = false
        navigationView.menu.findItem(R.id.nav_northern_region).isVisible = false
        navigationView.menu.findItem(R.id.nav_southern_region).isVisible = false
        navigationView.menu.findItem(R.id.nav_western_region).isVisible = false
        navigationView.menu.findItem(R.id.nav_chennai).isVisible = false
        navigationView.menu.findItem(R.id.nav_kolkata).isVisible = false


    }

    private fun toggleSubMenuVisibility(itemId: Int) {
        val subMenuItem = navigationView.menu.findItem(itemId)
        subMenuItem.isVisible = !subMenuItem.isVisible
    }




}