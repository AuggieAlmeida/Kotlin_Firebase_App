package br.com.fatec

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val logintext: TextView = findViewById(R.id.txt_login)
        val regButton: Button = findViewById(R.id.btn_reg)

        logintext.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        regButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email_reg)
            val password = findViewById<EditText>(R.id.password_reg)



            val inputEmail = email.text.toString()
            val inputPassword = password.text.toString()
            performSignUp(inputEmail, inputPassword)
        }

    }

    private fun performSignUp(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                "Preencha todos os campos", Toast.LENGTH_SHORT)
                .show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext,
                        "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext,
                        "Falha na autenticação!.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,
                    "Erro: ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

}