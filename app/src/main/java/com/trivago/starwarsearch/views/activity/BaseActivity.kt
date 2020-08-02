package com.trivago.starwarsearch.views.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.trivago.starwarsearch.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper


abstract class BaseActivity : AppCompatActivity() {

    private lateinit var currentFragment: Fragment

    override fun attachBaseContext(newBase: Context?) {
        //For: Calligraphy
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    open fun startFragment(fragment: Fragment?, addToBackStack: Boolean) {
        startFragment(fragment, addToBackStack, false)
    }

    open fun startFragment(
        fragment: Fragment?,
        addToBackStack: Boolean,
        allowStateLoss: Boolean
    ) {
        if (fragment != null) {

            if (::currentFragment.isInitialized && currentFragment == fragment)
                return

            currentFragment = fragment

            val fragmentName = fragment.javaClass.simpleName
            val fManager: FragmentManager = supportFragmentManager

            if (!fManager.isStateSaved) {

                val ft: FragmentTransaction = fManager.beginTransaction()

                ft.replace(R.id.container, fragment, fragmentName)

                if (addToBackStack) {
                    ft.addToBackStack(fragmentName)
                }

                if (allowStateLoss) {
                    ft.commitAllowingStateLoss()
                } else {
                    ft.commit()
                }

            }
        }
    }

    open fun closeFragment() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0 && isActivityStarted()) {
            hideKeyboard()
            if (!fragmentManager.isStateSaved) fragmentManager.popBackStack()
        } else {
            hideKeyboard()
            finish()
        }
    }

    open fun popAllFragments() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    open fun isActivityStarted(): Boolean {
        return lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
    }

    open fun showKeyboard() {
        val view = currentFocus
        view?.let { showKeyboard(it) }
    }

    open fun showKeyboard(view: View) {

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )

        inputManager.showSoftInput(
            view,
            InputMethodManager.SHOW_FORCED
        )
    }

    open fun hideKeyboard() {
        currentFocus?.let {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    open fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    open fun toast(msg: Any) {
        Toast.makeText(applicationContext, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    open fun toastLong(msg: Any) {
        Toast.makeText(applicationContext, msg.toString(), Toast.LENGTH_LONG).show()
    }

    open fun popBackStackTo(fragName: String) {

        val fragExistInBackStack = supportFragmentManager.findFragmentByTag(fragName) != null

        if (!fragExistInBackStack)
            return

        supportFragmentManager.popBackStackImmediate(fragName, 0)
    }

    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount
        if (count > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

}