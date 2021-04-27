package org.d3if4129.hitungbmi2.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if4129.hitungbmi2.R
import org.d3if4129.hitungbmi2.data.KategoriBMI
import org.d3if4129.hitungbmi2.databinding.FragmentHitungBinding
import org.d3if4129.hitungbmi2.db.BmiDb

//import org.d3if4129.hitungbmi2.ui.HitungFragmentDirections

class HitungFragment : Fragment() {

//    private val viewModel: HitungViewModel by viewModels()
    private val viewModel: HitungViewModel by lazy {
    val db = BmiDb.getInstance(requireContext())
    val factory = HitungViewModelFactory(db.dao)
    ViewModelProvider(this, factory).get(HitungViewModel::class.java)
}

    private lateinit var binding: FragmentHitungBinding
//    private lateinit var kategoriBmi: KategoriBMI

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
//        binding.saranButton.setOnClickListener { view: View ->
//            view.findNavController().navigate(
//                    HitungFragmentDirections.actionHitungFragmentToSaranFragment(kategoriBmi)
//            )
//        }
        binding.saranButton.setOnClickListener { viewModel.mulaiNavigasi() }
        binding.shareButton.setOnClickListener { shareData() }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(HitungFragmentDirections
                    .actionHitungFragmentToSaranFragment(it))
            viewModel.selesaiNavigasi()
        })

        viewModel.getHasilBmi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.bmiTextView.text = getString(R.string.bmi_x, it.bmi)
            binding.kategoriTextView.text = getString(R.string.kategori_x,
                    getKategori(it.kategori))
            binding.buttonGroup.visibility = View.VISIBLE
        })
        viewModel.data.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            Log.d("HitungFragment", "Data tersimpan. ID = ${it.id}")
        })
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
//        val tinggiCm = tinggi.toFloat() / 100

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val isMale = selectedId == R.id.priaRadioButton

        viewModel.hitungBmi(berat, tinggi, isMale)

//        val bmi = berat.toFloat() / (tinggiCm * tinggiCm)
//        val kategori = getKategori(bmi, isMale)
//
//
//        binding.bmiTextView.text = getString(R.string.bmi_x, bmi)
//        binding.kategoriTextView.text = getString(R.string.kategori_x, kategori)
//        binding.buttonGroup.visibility = View.VISIBLE
//      binding.saranButton.visibility = View.VISIBLE
    }

    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.priaRadioButton)
            getString(R.string.pria)
        else
            getString(R.string.wanita)
        val message = getString(R.string.bagikan_template,
            binding.beratEditText.text,
            binding.tinggiEditText.text,
            gender,
            binding.bmiTextView.text,
            binding.kategoriTextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

//    private fun getKategori(bmi: Float, isMale: Boolean): String {
    private fun getKategori(kategori: KategoriBMI): String {
//    kategoriBmi = if (isMale) {
//            when {
//                bmi < 20.5 -> KategoriBMI.KURUS
//                bmi >= 27.0 -> KategoriBMI.GEMUK
//                else -> KategoriBMI.IDEAL
//            }
//        } else {
//            when {
//                bmi < 18.5 -> KategoriBMI.KURUS
//                bmi >= 25.0 -> KategoriBMI.GEMUK
//                else -> KategoriBMI.IDEAL
//            }
//        }
//        val stringRes = when (kategoriBmi) {
        val stringRes = when (kategori) {
            KategoriBMI.KURUS -> R.string.kurus
            KategoriBMI.IDEAL -> R.string.ideal
            KategoriBMI.GEMUK -> R.string.gemuk
        }
        return getString(stringRes)
    }
}