package org.d3if4129.hitungbmi2.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if4129.hitungbmi2.R
import org.d3if4129.hitungbmi2.data.KategoriBMI
import org.d3if4129.hitungbmi2.databinding.FragmentHitungBinding

class HitungFragment : Fragment() {

    private lateinit var binding: FragmentHitungBinding
    private lateinit var kategoriBmi: KategoriBMI

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHitungBinding.inflate(
            layoutInflater, container, false)
        binding.button.setOnClickListener { hitungBmi() }
        binding.saranButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                HitungFragmentDirections.
                actionHitungFragmentToSaranFragment(kategoriBmi)
            )
        }
        setHasOptionsMenu(true)
        return binding.root
    }
    private fun hitungBmi() {

        val berat = binding.beratEditText.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(context, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi = binding.tinggiEditText.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggiCm = tinggi.toFloat() / 100

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale = selectedId == R.id.priaRadioButton
        val bmi = berat.toFloat() / (tinggiCm * tinggiCm)
        val kategori = getKategori(bmi, isMale)


        binding.bmiTextView.text = getString(R.string.bmi_x, bmi)
        binding.kategoriTextView.text = getString(R.string.kategori_x, kategori)
        binding.saranButton.visibility = View.VISIBLE

    }
    private fun getKategori(bmi: Float, isMale: Boolean): String {
        kategoriBmi = if (isMale) {
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
        val stringRes = when (kategoriBmi) {
            KategoriBMI.KURUS -> R.string.kurus
            KategoriBMI.IDEAL -> R.string.ideal
            KategoriBMI.GEMUK -> R.string.gemuk
        }
        return getString(stringRes)
    }
}