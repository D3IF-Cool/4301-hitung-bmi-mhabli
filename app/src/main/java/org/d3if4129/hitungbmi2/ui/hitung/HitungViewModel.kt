package org.d3if4129.hitungbmi2.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if4129.hitungbmi2.data.HasilBmi
import org.d3if4129.hitungbmi2.data.KategoriBMI

class HitungViewModel : ViewModel() {

    private val hasilBmi = MutableLiveData<HasilBmi?>()

    private val navigasi = MutableLiveData<KategoriBMI?>()

    fun hitungBmi(berat: String, tinggi: String, isMale: Boolean) {
        val tinggiCm = tinggi.toFloat() / 100
        val bmi = berat.toFloat() / (tinggiCm * tinggiCm)
        val kategori = if (isMale) {
            when {
                bmi < 20.5 -> KategoriBMI.KURUS
                bmi >= 27.0 -> KategoriBMI.GEMUK
                else -> KategoriBMI.IDEAL
            }
        }
        else {
            when {
                bmi < 18.5 -> KategoriBMI.KURUS
                bmi >= 25.0 -> KategoriBMI.GEMUK
                else -> KategoriBMI.IDEAL
            }
        }
        hasilBmi.value = HasilBmi(bmi, kategori)
    }
    fun mulaiNavigasi() {
        navigasi.value = hasilBmi.value?.kategori
    }
    fun selesaiNavigasi() {
        navigasi.value = null
    }

    fun getHasilBmi() : LiveData<HasilBmi?> = hasilBmi

    fun getNavigasi() : LiveData<KategoriBMI?> = navigasi
}


