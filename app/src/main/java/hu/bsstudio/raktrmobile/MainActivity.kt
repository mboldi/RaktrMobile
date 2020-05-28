package hu.bsstudio.raktrmobile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import hu.bsstudio.raktrmobile.model.CompositeItem
import hu.bsstudio.raktrmobile.model.Device
import hu.bsstudio.raktrmobile.model.Rent

class MainActivity : AppCompatActivity() {

    companion object {
        const val DEVICE_KEY = "device_key"
        const val COMPOSITE_KEY = "device_key"
        const val RENT_KEY = "rent_key"
        const val EDIT_KEY = "edit_key"

        var CURRENT_TYPE = "device"

        const val BASE_URL = "http://192.168.1.175:8080/api/"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            when(CURRENT_TYPE) {
                "device" -> {
                    intent = Intent(this, DeviceDetailsActivity::class.java)
                    intent.putExtra(DEVICE_KEY, Device())
                    intent.putExtra(EDIT_KEY, true)
                    startActivity(intent)
                }
                "composite" -> {
                    intent = Intent(this, CompositeItemDetailsActivity::class.java)
                    intent.putExtra(COMPOSITE_KEY, CompositeItem())
                    intent.putExtra(EDIT_KEY, true)
                    startActivity(intent)
                }
                "rent" -> {
                    intent = Intent(this, RentDetailsActivity::class.java)
                    intent.putExtra(RENT_KEY, Rent())
                    intent.putExtra(EDIT_KEY, true)
                    startActivity(intent)
                }
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_devices, R.id.nav_composite, R.id.nav_rents
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.devices, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
