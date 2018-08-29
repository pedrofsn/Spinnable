package br.com.redcode.spinnable.library.extensions_functions

import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import br.com.redcode.spinnable.library.R
import br.com.redcode.spinnable.library.adapter.AdapterSpinneable
import br.com.redcode.spinnable.library.model.Spinnable
import java.util.*

/**
 * Created by pedrofsn on 31/10/2017.
 */

fun <T : Spinner> T.setSpinnable(data: List<Spinnable>, defaultString: String?, idSelected: String?, onChange: (String, Int) -> Unit): AdapterSpinneable {
    val indexSelected = data.indexOfFirst { it.id == idSelected }
    val hasDefault = defaultString?.isNotBlank() == true

    val adapter = setSpinnable(
            data = data,
            hasDefault = hasDefault,
            defaultString = defaultString,
            function = { id, index ->
                if (id != data.getSelected()?.id) {
                    data.select(index)
                    onChange(id, index)
                }
            }
    )

    setSelection(indexSelected, false, hasDefault)

    return adapter
}

fun <T : Spinner> T.setSpinnable(data: List<Spinnable>, hasDefault: Boolean = false, id: String? = "", defaultString: String? = null, function: ((String, Int) -> Unit)? = null): AdapterSpinneable {
    val temp = ArrayList<Spinnable>()
    var adapterSpinner = AdapterSpinneable(context, hasDefault, temp)

    if (data.isNotEmpty()) {
        val default = defaultString ?: context.getString(R.string.select)
        if (hasDefault) temp.add(Spinnable(default, default, false))

        temp.addAll(data)
        adapterSpinner = AdapterSpinneable(context, hasDefault, temp)
        adapter = adapterSpinner

        setIdSpinnable(data, hasDefault, id)
    }

    function?.let { setOnItemSelected(data, hasDefault, function, id) }

    return adapterSpinner
}

fun <T : Spinner> T.setOnItemSelected(data: List<Spinnable>, hasDefault: Boolean = false, function: ((String, Int) -> Unit), idFirst: String? = "") {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val index = if (hasDefault) position.dec() else position
            if (index >= 0 && index <= data.count() - 1 && index != -1) {
                val selected = data[index].id

                if (idFirst == null || idFirst.isBlank() || idFirst.isNotBlank() && selected != idFirst) {
                    function(selected, index)
                }
            }
        }
    }
}

fun <T : Spinner> T.setIdSpinnable(data: List<Spinnable>, hasDefault: Boolean = false, id: String? = "", delayInMillis: Long = 1000) {
    // Pre-select item
    if (id?.isNotEmpty() == true) {
        for (i in data.indices) {
            val spinnable = data[i]

            if (id.equals(spinnable.id, ignoreCase = true)) {
                Handler().postDelayed({
                    val positionOfItem = if (hasDefault) i.inc() else i
                    if (selectedItemPosition != positionOfItem) {
                        setSelection(positionOfItem, true)
                    }
                }, delayInMillis)

                break
            }
        }
    }
}

fun <T : Spinner> T.getSpinnableFromSpinner(data: List<Spinnable>?): Spinnable? {
    if (data != null) {
        val selectedText = selectedItem?.toString()?.trim() ?: ""
        return if (selectedText.isNotEmpty()) data.firstOrNull { s -> selectedText == s.description.trim() } else null
    }

    return null
}

fun <T : Spinner> T.mock(hasDefault: Boolean = false, defaultString: String?, canMock: Boolean = true) {
    if (canMock) {
        val spinnables = arrayListOf<Spinnable>()
        val mDefaultString = if (defaultString != null && defaultString.isNotBlank()) defaultString else "Label mock"
        for (i in 1..5) {
            spinnables.add(Spinnable(i.toString(), "Mock data ${i}", false))
        }

        setSpinnable(
                data = spinnables,
                hasDefault = hasDefault,
                defaultString = mDefaultString
        )
    }
}

fun <T> Spinner.setSelection(data: List<T>, isSameItem: (T) -> Boolean) {
    if (data.isNotEmpty()) {
        val position = data.indexOfFirst { obj -> isSameItem(obj) }
        if (position != -1) {
            setSelection(position, true)
        }
    }
}

fun <T : Spinner> T.getSpinnableSelected() = (adapter as? AdapterSpinneable)?.getSpinnableSelected()

fun <T : Spinner> T.setSelection(position: Int, animate: Boolean, hasDefault: Boolean) {
    if (position != -1) {
        setSelection(if (hasDefault) position.inc() else position, animate)
    }
}