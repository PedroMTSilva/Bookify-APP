package com.bookify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bookify.data.LoginResponse
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupView()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupView() {
        val inputUsername: EditText = findViewById(R.id.username)
        val inputPassword: EditText = findViewById(R.id.password)
        val button: Button = findViewById(R.id.entrar)
        button.setOnClickListener {
            loginServer(inputUsername.text.toString(), inputPassword.text.toString())
            button.isEnabled = false
        }
    }

    private fun loginServer(username: String, pass: String) {

        val url = "https://bookify-api-eight.vercel.app/login"
        val button: Button = findViewById(R.id.entrar)

        val jsonBody = JSONObject()
        jsonBody.put("username", username)
        jsonBody.put("password", pass)
        val mRequestBody = jsonBody.toString()

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                Log.d("APP_REST", response)

                try {
                    //Toast.makeText(this,  JSONObject(response).toString(), Toast.LENGTH_SHORT).show()
                    button.isEnabled = true
                    Toast.makeText(this, handleJsonObject(JSONObject(response).toString()).toString(), Toast.LENGTH_SHORT).show()
                    Log.d("APP_REST", handleJsonObject(JSONObject(response).toString()).toString())
                    irPaginaCatalogo()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "JSONException", Toast.LENGTH_SHORT).show()
                    Log.d("APP_REST","JSONException" + e.printStackTrace() )
                    button.isEnabled = true
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                Log.d("APP_REST",error.toString())

                button.isEnabled = true
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
            override fun getBody(): ByteArray {
                return mRequestBody.toByteArray(Charset.defaultCharset())
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun handleJsonObject(obj: String) : String {

        val builder = GsonBuilder()
        builder.setPrettyPrinting()
        val gson = builder.create()
        val res: LoginResponse = gson.fromJson(obj, LoginResponse::class.java)
        //var jsonString: String? = ""
        //jsonString = gson.toJson(res)
        return res.token
    }

    fun irPaginaCatalogo(){
        val intent = Intent(this, Catalogo::class.java)
        startActivity(intent)
    }

    fun irPaginaCriarConta(view: View){
        val intent = Intent(this, CriarConta::class.java)
        startActivity(intent)
    }
}