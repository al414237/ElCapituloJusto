package es.sanchoo.capitulojusto

import android.content.Intent
import android.content.pm.ActivityInfo.*
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.sanchoo.capitulojusto.menu.VPAdapter
import es.sanchoo.capitulojusto.menu.ajustesFragment
import es.sanchoo.capitulojusto.menu.jugadoresFragment
import es.sanchoo.capitulojusto.menu.reglasFragment
import es.sanchoo.capitulojusto.views.MenuView

class MainActivity : AppCompatActivity(), MenuView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        // MENÚS
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        val vpAdapter = VPAdapter(this)

        vpAdapter.addFragment(jugadoresFragment(), "Jugadores")
        vpAdapter.addFragment(reglasFragment(), "Cómo jugar")
        vpAdapter.addFragment(ajustesFragment(), "Ajustes")
        viewPager.setAdapter(vpAdapter)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = vpAdapter.getTitle(position)
        }.attach()


        // COMENZAR A JUGAR
        val button: Button = findViewById(R.id.startButton)
        button.setOnClickListener {
            val intent: Intent = Intent(this, GameActivity:: class.java)
            startActivity(intent)
        }

    }


}