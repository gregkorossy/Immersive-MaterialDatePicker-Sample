package me.gingerninja.immersivedatepicker

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import me.gingerninja.immersivedatepicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerDatePickerFragmentCallbacks()

        binding.buttonNormal.setOnClickListener {
            openMaterialDialog(R.style.MyCalendar)
        }

        binding.buttonFullscreen.setOnClickListener {
            openMaterialDialog(R.style.MyCalendar_Fullscreen)
        }
    }

    private fun registerDatePickerFragmentCallbacks() {
        val setFocusFlags = fun(dialog: Dialog, setUiFlags: Boolean) {
            dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

            if (setUiFlags) {
                dialog.window?.decorView?.setOnSystemUiVisibilityChangeListener { visibility ->
                    if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                        hideSystemUI(dialog.window?.decorView)
                        hideSystemUI() // this might not be needed
                    }
                }

                hideSystemUI(dialog.window?.decorView)
                hideSystemUI() // this might not be needed
            }
        }

        val clearFocusFlags = fun(dialog: Dialog) {
            dialog.window?.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

                decorView.also {
                    if (it.isAttachedToWindow) {
                        windowManager.updateViewLayout(it, attributes)
                    }
                }
            }
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                if (f is MaterialDatePicker<*>) {
                    f.requireDialog().apply {
                        setFocusFlags(this, true)

                        setOnShowListener {
                            clearFocusFlags(this)
                        }
                    }
                }
            }

            /*override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                if (f is MaterialDatePicker<*>) {
                    f.dialog?.apply {
                        setFocusFlags(this, true)

                        if (isShowing) {
                            clearFocusFlags(this)
                        } else {
                            setOnShowListener {
                                clearFocusFlags(this)
                            }
                        }
                    }
                }
            }

            override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                if (f is MaterialDatePicker<*>) {
                    f.dialog?.apply {
                        setFocusFlags(this, false)
                    }
                }
            }*/
        }, false)
    }

    private fun openMaterialDialog(@StyleRes themeRes: Int) {
        val datePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()

        datePickerBuilder.apply {
            setTitleText("Select a date")
            setTheme(themeRes)
        }

        val dp = datePickerBuilder.build()

        dp.show(supportFragmentManager, null)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        hideSystemUI(window.decorView)
    }

    @Suppress("DEPRECATION")
    private fun hideSystemUI(view: View?) {
        view?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    @Suppress("DEPRECATION")
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}