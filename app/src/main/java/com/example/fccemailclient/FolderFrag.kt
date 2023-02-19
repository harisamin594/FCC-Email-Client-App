package com.example.fccemailclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fccemailclient.databinding.FolderFragBinding

class FolderFrag: Fragment()  {
    private var _binding: FolderFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)
        _binding = FolderFragBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.inboxbutn.setOnClickListener {
            findNavController().navigate(R.id.action_folderFrag_to_inboxFrag)
        }
        binding.sendbutn.setOnClickListener {
            findNavController().navigate(R.id.action_folderFrag_to_inboxFrag)
        }
    }
}