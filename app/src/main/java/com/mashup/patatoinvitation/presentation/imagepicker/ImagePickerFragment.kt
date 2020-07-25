package com.mashup.patatoinvitation.presentation.imagepicker

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseFragment
import com.mashup.patatoinvitation.databinding.FragmentImagePickerBinding
import com.mashup.patatoinvitation.presentation.imagepicker.data.ImageClickData
import com.mashup.patatoinvitation.utils.AppUtils
import gun0912.tedimagepicker.builder.TedRxImagePicker
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_image_picker.*

class ImagePickerFragment : BaseFragment<FragmentImagePickerBinding>(R.layout.fragment_image_picker) {
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
            imagePickAdapter.itemLongClickSubject.subscribe(this::onLongClicked)
                .addTo(compositeDisposable)
        }
        viewModel = ViewModelProvider(this).get(ImagePickerViewModel::class.java)
    }

    private fun initView() {
        rvImagePicker.apply {
            adapter = imagePickAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)

                    // 마지막 아이템을 제외하고 바텀 마진값을 줘서 이미지 간격 설정
                    val position = parent.getChildAdapterPosition(view)
                    if(position != imagePickAdapter.itemCount)
                    outRect.bottom = AppUtils.dpToPx(context, 10)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onClicked(data: ImageClickData) {
        when (data.view.id) {
            R.id.clRootImagePicker -> {
                // 이미지 수정
                viewModel.requestUpdateImage(data.view.context, data.position)
            }
            R.id.clRootImagePickerPlus -> {
                // 이미지 추가
                viewModel.requestAddImage(data.view.context)
            }
        }
    }

    fun onLongClicked(data: ImageClickData){
        // 이미지 삭제
        viewModel.deleteImage(data.position)
    }

    private fun Disposable.addTo(compositeDisposable: CompositeDisposable) =
        compositeDisposable.add(this)
}