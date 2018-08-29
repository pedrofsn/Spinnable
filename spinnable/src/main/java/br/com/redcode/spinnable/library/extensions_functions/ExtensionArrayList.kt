package br.com.redcode.spinnable.library.extensions_functions

import br.com.redcode.spinnable.library.domain.Selectable
import br.com.redcode.spinnable.library.model.Spinnable

/**
 * Created by pedrofsn on 30/11/2017.
 */
fun List<Spinnable>.getSelected(): Spinnable? = this.firstOrNull { it.selected }

fun List<Spinnable>.getSelectedPosition() = this.indexOfFirst { it.selected }
fun List<Spinnable>.hasSelected() = getSelectedPosition() != -1

fun List<Spinnable>.clearSelection(): Boolean {
    if (isNotEmpty()) {
        forEach { obj -> (obj as? Selectable)?.selected = false }
        return true
    }

    return false
}

fun List<Spinnable>.select(position: Int): Boolean {
    if (clearSelection()) {
        (this[position] as? Selectable)?.selected = true
        return true
    }

    return false
}

fun List<Spinnable>.select(id: String): Boolean {
    if (clearSelection()) {
        (firstOrNull { it.id == id } as? Selectable)?.selected = true
        return true
    }

    return false
}