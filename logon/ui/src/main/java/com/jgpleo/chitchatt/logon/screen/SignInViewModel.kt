package com.jgpleo.chitchatt.logon.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    fun helloWorldTest() {
        println("Hello world!")
    }

}