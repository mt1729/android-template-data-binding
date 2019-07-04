package com.company.app.scenes.login

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.app.R
import com.company.app.app.PreferenceStore
import com.company.app.database.User
import com.company.app.network.Result
import com.company.app.network.StatusCodes
import com.company.app.viewModel.LiveEvent
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    private val prefs: PreferenceStore,
    private val loginRepo: LoginRepository,
    private val coroutineCtx: CoroutineContext
) : ViewModel() {
    // TODO: - Populate username from SharedPreferences subclass (inject subclass through DI)
    private val _username = MutableLiveData<String>().also { it.value = prefs.username ?: "" }
    private val _password = MutableLiveData<String>().also { it.value = "" }
    private val _loginRequest = MutableLiveData<Boolean>()
    private val _loginSuccess = LiveEvent<User>()
    private val _loginFailure = LiveEvent<@StringRes Int>()

    val username: LiveData<String> = _username
    val password: LiveData<String> = _password
    val loginRequest: LiveData<Boolean> = _loginRequest
    val loginSuccess: LiveData<User> = _loginSuccess
    val loginFailure: LiveData<Int> = _loginFailure

    fun login() = viewModelScope.launch(coroutineCtx) {
        if (_loginRequest.value == true) { return@launch }

        // TODO: - Empty username unit test
        // TODO: - Empty password unit test

        // TODO: - Wrap all request/success/failure models in a 'LiveDataStatus' class
        val username = _username.value
        val password = _password.value
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            _loginFailure.postValue(R.string.login_fields_required)
            return@launch
        }

        _loginRequest.postValue(true)
        val res = loginRepo.login(username, password)
        _loginRequest.postValue(false)

        when {
            res is Result.Success -> {
                _loginSuccess.postValue(res.item)
            }
            res is Result.Failure && res.statusCode == StatusCodes.BAD_REQUEST -> {
                _loginFailure.postValue(R.string.login_failed)
            }
            else -> {
                _loginFailure.postValue(R.string.error_generic)
            }
        }
    }

    fun onUsernameChanged(s: CharSequence) {
        _username.value = s.toString()
    }

    fun onPasswordChanged(s: CharSequence) {
        _password.value = s.toString()
    }
}