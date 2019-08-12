package com.pfmiranda.redux2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.pfmiranda.redux2.redux.AppState
import com.pfmiranda.redux2.redux.AppStore
import com.pfmiranda.redux2.redux.ClearData
import com.pfmiranda.redux2.redux.NewData

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val BotonAgregar = findViewById<Button>(R.id.buttonAgregar)
        val BotonBorrar = findViewById<Button>(R.id.buttonBorrar)
        val texto = findViewById<EditText>(R.id.editTextIngreso)


        BotonAgregar.setOnClickListener {
            AppStore.instance.dispatch(NewData(texto.text.toString()))
        }
        BotonBorrar.setOnClickListener {
            AppStore.instance.dispatch(ClearData)
        }
        AppStore.instance.subscribe(::render)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppStore.instance.unsubscribe(::render)
    }

    private fun render(appState: AppState) {
        Toast.makeText(this, appState.data, Toast.LENGTH_SHORT).show()
    }
}
