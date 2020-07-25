package com.mashup.patatoinvitation.imagepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.databinding.FragmentImagePickerBinding

class ImagePickFragment : Fragment() {
    //    private val viewModel by viewModels<ImagePickerViewModel>()
    private lateinit var binding: FragmentImagePickerBinding
    private lateinit var imagePickAdapter: ImagePickerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_picker, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        initView()
    }

    private fun initComponent() {
        if (null != context) {
            imagePickAdapter = ImagePickerAdapter(requireContext())
        }
    }

    private fun initView() {
        binding.rvImagePicker.apply {
            adapter = imagePickAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}