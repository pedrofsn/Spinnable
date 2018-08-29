package br.com.redcode.spinnable.library.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import br.com.redcode.spinnable.library.R
import br.com.redcode.spinnable.library.model.Spinnable

/**
 * Created by pedrofsn on 27/10/2017.
 */
class AdapterSpinneable(
        mContext: Context,
        private val hasDefault: Boolean,
        private val mObjects: List<Spinnable>,
        private val textViewResourceId: Int = R.layout.adapter_spinnable
) : ArrayAdapter<Any>(mContext, textViewResourceId, mObjects) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    private fun getCustomView(position: Int, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(textViewResourceId, parent, false)
        val label = row.findViewById<View>(android.R.id.text1) as TextView
        label.text = getItem(position)

        val color = when {
            hasDefault && position == 0 -> R.color.text_color_hint
            else -> android.R.color.black
        }

        label.setTextColor(ContextCompat.getColor(context, color))

        return row
    }

    fun getSpinnableSelected(): Spinnable? = mObjects.firstOrNull { it.selected }
    fun getIndexSpinnableSelected(): Int? = mObjects.indexOfFirst { it.selected }

    override fun getItem(position: Int): String? = (super.getItem(position) as Spinnable).description

    fun getSpinnable(position: Int): Spinnable = super.getItem(position) as Spinnable

    fun getSpinnables(): List<Spinnable> = mObjects

    fun clearAndAddAll(new: List<Spinnable>) {
        clear()
        addAll(new)
    }

    fun select(index: Int) {
        if (index != -1) {
            mObjects.map { obj -> obj.selected = false }
            mObjects[index].selected = true
        }
    }

}