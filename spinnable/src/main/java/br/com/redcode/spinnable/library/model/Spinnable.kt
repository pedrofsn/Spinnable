package br.com.redcode.spinnable.library.model

import br.com.redcode.spinnable.library.domain.Selectable

data class Spinnable(
        val id: String,
        val description: String,
        override var selected: Boolean = false
) : Selectable {
    override fun toString() = description
}