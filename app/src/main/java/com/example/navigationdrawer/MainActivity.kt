package com.example.navigationdrawer

//import ListviewActivity
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_POST_NOTIFICATIONS = 101
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        drawerLayout = findViewById(R.id.drawer_layout)


        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        navigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
//            findViewById(R.id.toolbar),
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                R.id.splash_screen -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val Intent = Intent(this@MainActivity, splashscreen::class.java);
                    startActivity(Intent)

                    true
                }

                R.id.registration_page -> {

                    drawerLayout.closeDrawer(GravityCompat.START)
                    val Intent = Intent(this@MainActivity, registration::class.java);
                    startActivity(Intent)
                    true
                }

                R.id.form_controls -> {

                    drawerLayout.closeDrawer(GravityCompat.START)
                    val Intent = Intent(this@MainActivity, formcontrols::class.java);
                    startActivity(Intent)
                    true
                }


                R.id.footermenu -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    val intent = Intent(this@MainActivity, footerbar2::class.java)
                    startActivity(intent)
                    true
                }

                R.id.floating_button -> {
                    val intent = Intent(this@MainActivity, floating_button3::class.java)
                    startActivity(intent)
                    true
                }

                R.id.tab_bar -> {
                    val intent = Intent(this@MainActivity, Tab_bar::class.java)
                    startActivity(intent)

                    true
                }

                R.id.google_maps -> {

                    drawerLayout.closeDrawer(GravityCompat.START)
                    val Intent = Intent(this@MainActivity, googlemaps::class.java);
                    startActivity(Intent)
                    true

                }
                R.id.firebase_crashlytics -> {

                    drawerLayout.closeDrawer(GravityCompat.START)
                    val Intent = Intent(this@MainActivity, firebase_crashlytics::class.java);
                    startActivity(Intent)
                    true

                }
                R.id.loading_screen -> {
                    // Handle about item click
                    val intent = Intent(this@MainActivity, loading_screen::class.java);
                    startActivity(intent)
                    true
                }
                R.id.ListView -> {
                    val intent = Intent(applicationContext, ListviewActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.lazy_loading -> {
                    val intent = Intent(this@MainActivity, lazy_loading::class.java)
                    startActivity(intent)
                    true
                }
                R.id.web_view -> {
                    val intent = Intent(this@MainActivity, webview::class.java)
                    startActivity(intent)
                    true
                }

                R.id.speech_to_text -> {
                    val intent = Intent(this@MainActivity, speach_to_text::class.java);
                    startActivity(intent)
                    true

                }

                R.id.text_to_speech -> {

                    val intent = Intent(this@MainActivity, text_to_speech::class.java);
                    startActivity(intent)
                    true
                }

                R.id.Search_autofill -> {


                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }



                R.id.push_notification -> {
                    val intent = Intent(this@MainActivity, FCMPushNotification::class.java);
                    startActivity(intent)
                    true
                }

//
                R.id.Soc_Login -> {
                    val intent = Intent(this@MainActivity, social_login::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }



                R.id.pull_to_refresh -> {
                    val intent = Intent(this@MainActivity, pull_to_refresh::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.third_party_apis -> {
                    val intent = Intent(this@MainActivity, third_party_api::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
//
                R.id.currency_converter1 -> {
                    val intent = Intent(this@MainActivity, currency_converter1::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.camera -> {
                    val intent = Intent(this@MainActivity, camera::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.image_crop -> {
                    // Handle about item click
                    val intent = Intent(this@MainActivity, image_crop2::class.java);
                    startActivity(intent)
                    true
                }

                R.id.image_shape -> {
                    // Handle about item click
                    val intent = Intent(this@MainActivity, image_shape::class.java);
                    startActivity(intent)
                    true
                }



                R.id.google_ads -> {
                    val intent = Intent(this@MainActivity, google_ads::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.video_streaming -> {
                    val intent = Intent(this@MainActivity, video_streaming::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.audio_streaming -> {
                    val intent = Intent(this@MainActivity, audio_streaming::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.local_Video_play -> {
                    val intent = Intent(this@MainActivity, local_video::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.local_audio_play -> {
                    val intent = Intent(this@MainActivity, local_audio::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.file_selection -> {
                    val intent = Intent(this@MainActivity, file_selection::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.socialmedia_feed -> {
                    val intent = Intent(this@MainActivity, socialmedia_feed::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }


                R.id.api -> {

                    val Intent = Intent(this@MainActivity, api::class.java);
                    startActivity(Intent)
                    true
                }

                R.id.video -> {

                    val intent = Intent(this@MainActivity, videoo::class.java);
                    startActivity(intent)
                    true
                }
                R.id.backgroundService -> {

                    val intent = Intent(this@MainActivity, Background_Service::class.java);
                    startActivity(intent)
                    true
                }
                R.id.vibration -> {

                    val intent = Intent(this@MainActivity, Vibration::class.java);
                    startActivity(intent)
                    true
                }
                R.id.tourch -> {

                    val intent = Intent(this@MainActivity, Tourch::class.java);
                    startActivity(intent)
                    true
                }
                R.id.permission -> {

                    val intent = Intent(this@MainActivity, Permission::class.java);
                    startActivity(intent)
                    true
                }
                R.id.file_upload -> {

                    val intent = Intent(this@MainActivity, file_upload_toServer::class.java);
                    startActivity(intent)
                    true
                }


                else -> false
            }
            true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf("com.example.navigationdrawer.permission.POST_NOTIFICATION"), REQUEST_CODE_POST_NOTIFICATIONS)
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            // Here, you can check the result of the permission request
            // and react accordingly, such as showing a rationale or a warning.
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }
}


