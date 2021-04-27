package org.d3if4129.hitungbmi2.data

import org.d3if4129.hitungbmi2.db.BmiEntity

object HitungBmi {
    fun hitung(data: BmiEntity): HasilBmi {
        val tinggiCm = data.tinggi / 100
        val bmi = data.berat / (tinggiCm * tinggiCm)
        val kategori = if (data.isMale) {
            when {
                bmi < 20.5 -> KategoriBMI.KURUS
                bmi >= 27.0 -> KategoriBMI.GEMUK
                else -> KategoriBMI.IDEAL
            }
        } else {
            when {
                bmi < 18.5 -> KategoriBMI.KURUS
                bmi >= 25.0 -> KategoriBMI.GEMUK
                else -> KategoriBMI.IDEAL
            }
        }
        return HasilBmi(bmi, kategori)
    }
}