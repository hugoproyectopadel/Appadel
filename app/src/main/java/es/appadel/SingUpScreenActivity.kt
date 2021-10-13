package es.appadel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.appadel.clases.Usuario

class SingUpScreenActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etTelefono: EditText
    lateinit var etNombre: EditText
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up_screen)
        title = "Registro"
        etEmail = findViewById(R.id.etLoginRegistro)
        etPassword = findViewById(R.id.etPasswordRegistro)
        etTelefono = findViewById(R.id.etRegistroTelefono)
        etNombre = findViewById(R.id.etRegisterName)
    }
    private fun guardarUsuarioFirestore(usuario: Usuario){
        db.collection("users").document(usuario.uid).set(usuario)
            .addOnSuccessListener {
                Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ocurrió un error al crear el usuario", Toast.LENGTH_LONG).show()
            }
    }
    fun onClickCrearUsuario(view: View) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val nombre = etNombre.text.toString()
        val telefono = etTelefono.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debes de rellenar email y password", Toast.LENGTH_LONG).show()
        } else {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

                        val usuario = Usuario(
                            uid = uid,
                            email = email,
                            nombre = nombre,
                            telefono = telefono
                        ).also {
                            guardarUsuarioFirestore(it)
                        }

                    } else {
                        Toast.makeText(
                            baseContext, "¡Error! Ocurrió un error al intentar registrarse",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }
    }
}