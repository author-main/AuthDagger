package com.ruani.authdagger

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.ruani.authdagger.abstract_data.auth_data
import com.ruani.authdagger.mvp.Presenter
import com.ruani.authdagger.helpers.MessageHandler
import com.ruani.authdagger.helpers.getStringResource
import com.ruani.authdagger.mvp.Contract

/*
    Для собственной реализации формы аутентификации,
    вам необходимо переопределить классы пакета model_classes:
*   AuthFingerPrint - класс для работы со сканером отпечатка пальцев
        var onAuthBiometricComplete: ((value: auth_data.FingerValue?) -> Unit)? = null - callback,
        обрабатывается в случае успешной операции сканирования отпечатка пальцев
        value - результат сканирования, FINGER_COMPLETE - успешно, FINGER_ERROR - ошибка

*   CipherPassword  - класс кодирования/декодирования пароля пользователя
        fun decryptPassword(value: String): String? - получаем закодированный пароль
        fun encryptPassword(value: String): String? - получаем декодируемый пароль

*   FirebaseServer  - класс для работы с сервером аутентификацииЖ
        var onAuthServerResult: ((action: auth_data.AuthAction, result: auth_data.AuthValue) -> Unit)? - callback,
        результат выполнения запроса на сервере аутентификации,
        action - тип выполняемого запроса, значения SIGNIN - аутентификая, RESTORE - восстановление, REGISTER - регистрация
        result - результат запроса, COMPLETE - успешно, ERROR - ошибка выполнения запроса,
                 ERROR_CONNECTION -отсутсвует сеть, ERROR_USERDATA - не заполнены данные пользователя

*   UserDataStorage - класс, реализующий сохранение/чтение данных пользователя (почта и пароль):
        fun putPassword(password: String)   - сохранить пароль пользователя
        fun getPassword(): String?          - получить пароль пользователя
        fun getEmail(): String?             - получить почту пользователя
        fun putEmail(email: String)         - сохранить почту пользователя
        fun existPassword(): Boolean        - проверить, сохранен ли пароль
*/

class MainActivity : AppCompatActivity(), Contract.IView {
    private lateinit var dataBinding: com.ruani.authdagger.databinding.ActivityMainBinding
    private lateinit var presenter: Presenter<Contract.IView>
    companion object{
        private fun setNightMode() {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        }
    }
    init {
        setNightMode()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        initData()
    }

    private fun initData(){
        presenter = Presenter()
        dataBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        dataBinding.eventhandler = presenter
        presenter.attachView(this)
    }

    override fun onResultAuth(authAction: auth_data.AuthAction, authValue: auth_data.AuthValue) {
        if (authValue != auth_data.AuthValue.COMPLETE)
            MessageHandler.showAuthError(authAction, authValue)
        else {
            val value = when (authAction){
                auth_data.AuthAction.RESTORE ->
                    getStringResource(R.string.dlgrest_success)
                auth_data.AuthAction.REGISTER ->
                    getStringResource(R.string.dlgreg_success)
                else ->
                    null
            }
            if (!value.isNullOrBlank())
                MessageHandler.showMessage(value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun getSymbolViews() =
        arrayOf(dataBinding.textViewSymbol0,
            dataBinding.textViewSymbol1,
            dataBinding.textViewSymbol2,
            dataBinding.textViewSymbol3,
            dataBinding.textViewSymbol4)

    override fun enabledFingerPrint(value: Boolean?) {
        dataBinding.buttonFinger.isEnabled = value ?: false
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val editTextRect = Rect()
        dataBinding.editTextEmail.getGlobalVisibleRect(editTextRect)
        ev?.let { event ->
            if (!editTextRect.contains(event.x.toInt(), event.y.toInt()))
                hideFocusEmail()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideFocusEmail(){
        if (dataBinding.editTextEmail.isFocused) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(dataBinding.editTextEmail.windowToken, 0)
            dataBinding.editTextEmail.isFocusableInTouchMode = false
            dataBinding.editTextEmail.clearFocus()
            dataBinding.editTextEmail.isFocusableInTouchMode = true
        }
    }

    /*
    * onAccessed() - доступ получен
    */
    override fun onAccessed() {

    }
}