package com.mashup.nawainvitation.imagepicker

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.databinding.FragmentImagePickerBinding
import com.mashup.nawainvitation.imagepicker.data.ImageClickData
import gun0912.tedimagepicker.builder.TedRxImagePicker
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ImagePickFragment : Fragment() {
    //    private val viewModel by viewModels<ImagePickerViewModel>()
    private lateinit var binding: FragmentImagePickerBinding
    private lateinit var imagePickAdapter: ImagePickerAdapter

    private val compositeDisposable = CompositeDisposable()

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
            imagePickAdapter.itemClickSubject.subscribe(this::onClicked)
                .addTo(compositeDisposable)
        }
    }

    private fun initView() {
        binding.rvImagePicker.apply {
            adapter = imagePickAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onClicked(data: ImageClickData){
        // add
        showImagePicker(data.view.context)

        // delete
//        deleteImage(data.position)
    }

    private fun showImagePicker(context: Context) {
        TedRxImagePicker.with(context)
            .startMultiImage()
            .subscribe({uriList ->
                addImageUriList(uriList)
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)
    }

    private fun addImageUriList(uriList: List<Uri>){
        imagePickAdapter.addImageUriList(uriList)
    }

    private fun deleteImage(position: Int){
        imagePickAdapter.deleteImage(position)
    }

    private fun Disposable.addTo(compositeDisposable: CompositeDisposable) = compositeDisposable.add(this)
}