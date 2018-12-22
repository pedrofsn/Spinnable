package br.com.redcode.spinnable.library.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
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
            hasDefault && position == 0 -> R.color.spinnable_hint_color
            else -> R.color.spinnable_text_color
        }

        label.setTextColor(ContextCompat.getColor(context, color))

        return row
    }

    fun getSpinnableSelected(): Spinnable? {
        val indexSelected = getIndexSpinnableSelected()
        indexSelected?.let {
            if (hasDefault && indexSelected > 0) {
                return mObjects[it]
            }
        }

        return null
    }

    fun getIndexSpinnableSelected(): Int? {
        val indexSpinnableSlected = mObjects.indexOfFirst { it.selected }
        if (indexSpinnableSlected == -1 && hasDefault) {
            return 0
        }

        return null
    }

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