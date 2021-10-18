package es.appadel

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.appadel.clases.Partido
import es.appadel.herramientas.MetodosFecha

class DetallePartidoActivity : AppCompatActivity() {
    
    lateinit var tvHora: TextView
    lateinit var tvFecha: TextView
    lateinit var tvDir: TextView
    lateinit var tvNumJugadores: TextView
    lateinit var tvNivel: TextView
    lateinit var btnInscribirme: Button
    lateinit var uidPartido: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_partido)
        
        tvHora = findViewById(R.id.tvDetallesHora)
        tvFecha = findViewById(R.id.tvDetallesFecha)
        tvDir = findViewById(R.id.tvDetallesDir)
        tvNumJugadores = findViewById(R.id.tvDetallesNumPersonas)
        tvNivel = findViewById(R.id.tvDetallesNivel)
        btnInscribirme = findViewById(R.id.btnInscribirse)
        
        val bundle  = intent.extras
        
        bundle?.let {
            uidPartido = it.get("EXTRA_UID_PARTIDO").toString()
            cargarPartidoFirestore(uidPartido)
        }
        
        
    }
    @SuppressLint("SetTextI18n")
    fun dibujarPartidoDetalles(partido: Partido?){
        partido?.let {
            val fechaNombre = MetodosFecha.getNombreMes(it.fecha!!.toDate())
            val fechaNumero = MetodosFecha.getNumero(it.fecha!!.toDate())
            val horaNombre = MetodosFecha.getHora(it.fecha!!.toDate())
            val minutos = MetodosFecha.getMinutos(it.fecha!!.toDate())
            tvHora.text = "$fechaNumero de $fechaNombre"
            tvHora.text = "$horaNombre:$minutos"
            tvDir.text = "${it.direccion}, (${it.provincia})"
            if(it.users!=null){
                tvNumJugadores.text = "${it.users!!.size}/${it.numero_jugadores}"
            }else{
                tvNumJugadores.text =  "0/${it.numero_jugadores}"
            }
        }
    }
    fun cargarPartidoFirestore(uid: String){
        Firebase.firestore.collection("partidos").document(uid).get()
            .addOnSuccessListener { 
                val partido = it.toObject(Partido::class.java)
                dibujarPartidoDetalles(partido)
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Error al cargar el partido seleccionado", Toast.LENGTH_SHORT).show()
            }
    }
    fun onClickInscribirse(view: android.view.View) {
        
    }
}