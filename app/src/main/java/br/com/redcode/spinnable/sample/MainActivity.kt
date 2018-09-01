package br.com.redcode.spinnable.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.redcode.spinnable.library.extensions_functions.setSpinnable
import br.com.redcode.spinnable.library.model.Spinnable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content = arrayListOf<Spinnable>()
        for (i in 1..10) {
            val spinnable = Spinnable(i.toString(), "Item with id $i")
            content.add(spinnable)
        }

        val callback: (String, Int) -> Unit = { id, index ->
            textViewSelectedItem.text = "ID $id and index $index selected"
        }

        spinner.setSpinnable(
                data = content,
                hasDefault = true,
                id = "2",
                defaultString = "Select something",
                function = callback
        )
    }
}
