package org.d3if4129.hitungbmi2.ui.histori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.d3if4129.hitungbmi2.databinding.FragmentHistoriBinding

class HistoriFragment : Fragment() {

    private lateinit var binding: FragmentHistoriBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHistoriBinding.inflate(layoutInflater,
            container, false)
        return binding.root
    }
}