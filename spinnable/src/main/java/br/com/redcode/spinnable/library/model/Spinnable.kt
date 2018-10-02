package br.com.redcode.spinnable.library.model

import android.os.Parcelable
import br.com.redcode.spinnable.library.domain.Selectable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Spinnable(
        val id: String,
        val description: String,
        override var selected: Boolean = false
) : Parcelable, Selectable {
    override fun toString() = description
}