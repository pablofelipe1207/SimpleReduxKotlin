package com.pfmiranda.redux2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.pfmiranda.redux2.redux.*

class MainActivity : AppCompatActivity() {

    private var dispatch: Dispatch? = null
    private lateinit var unsubscribe: Unsubscribe

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val BotonAgregar = findViewById<Button>(R.id.buttonAgregar)
        val BotonBorrar = findViewById<Button>(R.id.buttonBorrar)
        val texto = findViewById<EditText>(R.id.editTextIngreso)

        unsubscribe = AppStore.instance.subscribe(::render)

        BotonAgregar.setOnClickListener {
            dispatch?.invoke(NewData(texto.text.toString()))
        }
        BotonBorrar.setOnClickListener {
            dispatch?.invoke(ClearData)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribe.invoke()
        dispatch = null
    }

    private fun render(appState: AppState, dispatch: Dispatch) {
        this.dispatch = dispatch
        Toast.makeText(this, appState.data, Toast.LENGTH_SHORT).show()
    }
}
