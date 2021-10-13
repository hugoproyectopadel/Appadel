package es.appadel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginScreenActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        title = "Login"
        etEmail = findViewById(R.id.etLoginEmail)
        etPassword = findViewById(R.id.etPasswordLogin)
    }

    fun onClickRecuperarContrasena(view: View) {}
    fun onClickRegistro(view: View) {
        val intent = Intent(this, SingUpScreenActivity::class.java)
        startActivity(intent)
    }
    fun onClickAcceder(view: View) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debes de rellenar email y password", Toast.LENGTH_LONG).show()
        } else {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                    } else {
                        Toast.makeText(
                            baseContext, "¡Error! Ocurrió un error al intentar acceder",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }

    }
}