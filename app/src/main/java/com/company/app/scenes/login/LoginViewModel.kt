package com.company.app.scenes.login

import androidx.annotation.StringRes
import androidx.lifecycle.*
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
    private val _username = MutableLiveData<String>().also { it.value = prefs.username ?: "" }
    private val _password = MutableLiveData<String>().also { it.value = "" }

    private val _isLoginValid = MediatorLiveData<Boolean>().also {
        it.addSource(_username) { username -> it.value = isLoginValid(username, _password.value) }
        it.addSource(_password) { password -> it.value = isLoginValid(_username.value, password) }
    }

    private val _loginRequest = MutableLiveData<Boolean>().also { it.value = false }
    private val _loginSuccess = LiveEvent<User>()
    private val _loginFailure = LiveEvent<@StringRes Int>()

    val username: LiveData<String> = _username
    val password: LiveData<String> = _password
    val isLoginValid: LiveData<Boolean> = _isLoginValid
    val loginRequest: LiveData<Boolean> = _loginRequest
    val loginSuccess: LiveData<User> = _loginSuccess
    val loginFailure: LiveData<Int> = _loginFailure

    fun login() = viewModelScope.launch(coroutineCtx) {
        if (_loginRequest.value == true) { return@launch }

        val username = _username.value
        val password = _password.value
        if (username == null || password == null || !isLoginValid(username, password)) {
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

    private fun isLoginValid(username: String?, password: String?) : Boolean {
        return username != null && username.isNotBlank() && password != null && password.isNotBlank()
    }

    fun onUsernameChanged(s: CharSequence) {
        _username.value = s.toString()
    }

    fun onPasswordChanged(s: CharSequence) {
        _password.value = s.toString()
    }
}