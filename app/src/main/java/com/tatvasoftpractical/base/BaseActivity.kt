package com.tatvasoftpractical.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.tatvasoftpractical.factories.ViewModelProviderFactory
import java.text.SimpleDateFormat
import kotlin.reflect.KClass

abstract class BaseActivity<V : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    abstract val modelClass: KClass<V>
    protected abstract val layoutId: Int
    protected abstract fun initControls()
    protected open fun getDataFromIntent() {}
    protected lateinit var binding: B

    val viewModel by lazy {
        ViewModelProviders.of(this, ViewModelProviderFactory(this.application)).get(modelClass.java)
    }

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        getDataFromIntent()
        initControls()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    fun Activity.hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun goToActivity(context: Context, activity: KClass<*>) {

        val intent = Intent(context, activity.java)
        startActivity(intent)

    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val imm =
            editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun Activity.showSoftKeyboard(mView: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInputFromWindow(
            mView.applicationWindowToken,
            InputMethodManager.SHOW_FORCED,
            0
        )
    }

    fun sendEmail(recipient: String) {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
            and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
               so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent
//        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
//        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTo12HourFormat(time: String): String {

        val f1 = SimpleDateFormat("HH:mm:ss") //HH for hour of the day (0 - 23)
        val d = f1.parse(time)
        val f2 = SimpleDateFormat("h:mma")

        return f2.format(d!!)
    }

    fun showToast(context: Context, msg: String, duration: Int?) {
        var finalDuration = duration
        if (finalDuration == null) {
            finalDuration = Toast.LENGTH_SHORT
        }
        Toast.makeText(context, msg, finalDuration).show()
    }

    fun checkPermissions() : Boolean{

        val cameraPermission =  ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.CAMERA)
        val readStorage =  ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeStorage =  ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        return cameraPermission == PackageManager.PERMISSION_GRANTED && readStorage == PackageManager.PERMISSION_GRANTED && writeStorage == PackageManager.PERMISSION_GRANTED
    }

//    fun askForPermission(activity : Activity){
//
//        ActivityCompat.requestPermissions(this, arrayOf<String>(CAMERA, READ_EXTERNAL_STORAGE,
//            WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
//    }

    private fun copyTextToClipboard(string : String) {

        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clipData = ClipData.newPlainText("text", string)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }

}