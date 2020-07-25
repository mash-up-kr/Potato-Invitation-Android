package com.mashup.patatoinvitation.presentation.imagepicker

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseFragment
import com.mashup.patatoinvitation.databinding.FragmentImagePickerBinding
import com.mashup.patatoinvitation.presentation.imagepicker.data.ImageClickData
import gun0912.tedimagepicker.builder.TedRxImagePicker
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_image_picker.*

class ImagePickFragment : BaseFragment<FragmentImagePickerBinding>(R.layout.fragment_image_picker) {
    private lateinit var viewModel: ImagePickerViewModel
    private lateinit var imagePickAdapter: ImagePickerAdapter

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        initView()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.imageUriList.observe(viewLifecycleOwner, Observer<List<Uri>> { list ->
            imagePickAdapter.setData(list)
        })
    }

    private fun initComponent() {
        if (null != context) {
            imagePickAdapter = ImagePickerAdapter(requireContext())
            imagePickAdapter.itemClickSubject.subscribe(this::onClicked)
                .addTo(compositeDisposable)
        }
    }

    private fun initView() {
        rvImagePicker.apply {
            adapter = imagePickAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onClicked(data: ImageClickData) {
        when (data.view.id) {
            R.id.ivPicked -> {
                // TODO: show choice dialog
                // 삭제
//                deleteImage(data.position)

                // 수정
                viewModel.requestUpdateImage(data.view.context, data.position)
            }
            R.id.ivPlus -> {
                viewModel.requestAddImage(data.view.context)
            }
        }
    }

    private fun Disposable.addTo(compositeDisposable: CompositeDisposable) =
        compositeDisposable.add(this)
}