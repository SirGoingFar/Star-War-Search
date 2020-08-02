package com.trivago.starwarsearch.views.fragment

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.trivago.starwarsearch.views.activity.BaseActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseFragment : Fragment() {

    protected var baseActivity: BaseActivity? = null
        private set

    override fun onAttach(context: Context) {
        //For: Calligraphy
        super.onAttach(ViewPumpContextWrapper.wrap(context))

        if (context is BaseActivity)
            baseActivity = context
    }

    open fun startFragment(fragment: Fragment?, addToBackStack: Boolean) {
        startFragment(fragment, addToBackStack, false)
    }

    open fun startFragment(
        fragment: Fragment?,
        addToBackStack: Boolean,
        allowStateLoss: Boolean
    ) {
        baseActivity?.startFragment(fragment, addToBackStack, allowStateLoss)
    }

    open fun closeFragment() {
        baseActivity?.closeFragment()
    }

    open fun showKeyboard() {
        baseActivity?.showKeyboard()
    }

    open fun showKeyboard(view: View) {
        baseActivity?.showKeyboard(view)
    }

    open fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }

    open fun hideKeyboard(view: View) {
        baseActivity?.hideKeyboard(view)
    }

    open fun toast(msg: Any) {
        baseActivity?.toast(msg)
    }

    open fun toastLong(msg: Any) {
        baseActivity?.toastLong(msg)
    }

}