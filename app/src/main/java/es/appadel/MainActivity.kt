package es.appadel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.appadel.adapter.ClickItem
import es.appadel.adapter.PartidoAdapter
import es.appadel.clases.Partido

class MainActivity : AppCompatActivity() {
    lateinit var rvPartidos: RecyclerView
    lateinit var btnCrearPartido: FloatingActionButton
    lateinit var partidoAdapter: PartidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPartidos = findViewById(R.id.rvPartidos)
        btnCrearPartido = findViewById(R.id.fbCrearPartido)
        btnCrearPartido.setOnClickListener {
            val intent = Intent(this, FormularioPartidoActivity::class.java)
            startActivity(intent)
        }
        partidoAdapter = PartidoAdapter(ClickItem {

        })
        cargarPartidosFirestore()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_search -> {
                buscarPartido()
                true
            }
            R.id.menu_logout -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun cargarPartidosFirestore(){
        Firebase.firestore.collection("partidos").get()
            .addOnSuccessListener {
                val tempPartidosList = mutableListOf<Partido>()
                for(document in it.documents) {
                    val partido = document.toObject(Partido::class.java)
                    if (partido != null) {
                        tempPartidosList.add(partido)
                    }
                }
                partidoAdapter.submitList(tempPartidosList)
                partidoAdapter.notifyDataSetChanged()
                rvPartidos.adapter = partidoAdapter
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,  "Error al cargar los partidos", Toast.LENGTH_SHORT).show()
            }
    }
    fun buscarPartido(){

    }
    fun signOut() {
        Firebase.auth.signOut()
        startActivity(Intent(this, LoginScreenActivity::class.java))
        finish()
    }
}