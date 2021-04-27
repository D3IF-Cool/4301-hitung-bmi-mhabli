package org.d3if4129.hitungbmi2.ui.histori

import androidx.lifecycle.ViewModel
import org.d3if4129.hitungbmi2.db.BmiDao

class HistoriViewModel(db: BmiDao) : ViewModel() {
    val data = db.getLastBmi()
}
