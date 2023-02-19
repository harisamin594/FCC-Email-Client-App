package com.example.fccemailclient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fccemailclient.databinding.SentFragBinding

class SentFrags : Fragment() {
    private var _binding: SentFragBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = SentFragBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sentReturn.setOnClickListener {
            findNavController().navigate(R.id.action_sentFrags_to_folderFrag2)
        }
    }
}