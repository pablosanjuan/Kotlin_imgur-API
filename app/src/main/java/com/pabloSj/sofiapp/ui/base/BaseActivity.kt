package com.pabloSj.sofiapp.ui.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject


abstract class BaseActivity<B: ViewDataBinding, V : ViewModel> : AppCompatActivity() {
    @Inject
    lateinit var viewModelClass: Class<V>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var binding: B
    protected lateinit var viewModel: V


    protected fun setAndBindContentView(@LayoutRes layoutResID: Int) {
        binding = DataBindingUtil.setContentView(this, layoutResID)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }
}
