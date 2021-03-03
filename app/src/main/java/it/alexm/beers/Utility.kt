package it.alexm.beers

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import it.alexm.beers.data.vo.BrewDate
import java.util.*


fun ImageView.load(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun Fragment.showToast(duration: Int = Toast.LENGTH_SHORT, block: () -> String) {
    Toast.makeText(context, block(), duration).show()
}

fun Fragment.showDatePicker(
    oldStart: Long?,
    oldEnd: Long?,
    listener: (start: BrewDate, end: BrewDate) -> Unit
) {
    val now = Calendar.getInstance()

    val builder = MaterialDatePicker.Builder.dateRangePicker()
    builder.setSelection(
        androidx.core.util.Pair(
            oldStart ?: now.timeInMillis,
            oldEnd ?: now.timeInMillis
        )
    )

    val picker = builder.build()
    picker.addOnPositiveButtonClickListener {
        listener(BrewDate(it.first), BrewDate(it.second))
    }
    picker.show(requireActivity().supportFragmentManager, picker.toString())
}