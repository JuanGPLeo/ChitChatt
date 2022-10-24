package com.jgpleo.chitchatt.logon.domain.usecase

import com.jgpleo.chitchatt.logon.domain.usecase.IsPassValidUseCase.PassStatus.*
import org.junit.Test

class IsPassValidUseCaseTest {

    private val useCase: IsPassValidUseCase = IsPassValidUseCase()

    @Test
    fun `when pass is empty, then returns Empty`() {
        val pass = ""
        val result = useCase(pass, true)
        assert(result is Empty)
    }

    @Test
    fun `when pass hasn't at least an upper case character, then returns WeakPass`() {
        val pass = "1abcde#"
        val result = useCase(pass, true)
        assert(result is WeakPass)
    }

    @Test
    fun `when pass hasn't at least a lower case character, then returns WeakPass`() {
        val pass = "1ABCDE#"
        val result = useCase(pass, true)
        assert(result is WeakPass)
    }

    @Test
    fun `when pass hasn't at least a number, then returns WeakPass`() {
        val pass = "aABCDE#"
        val result = useCase(pass, true)
        assert(result is WeakPass)
    }

    @Test
    fun `when pass hasn't at least a special character, then returns WeakPass`() {
        val pass = "1ABCDEf"
        val result = useCase(pass, true)
        assert(result is WeakPass)
    }

    @Test
    fun `when pass has less that 6 characters, then returns WeakPass`() {
        val pass = "1ABd#"
        val result = useCase(pass, true)
        assert(result is WeakPass)
    }

    @Test
    fun `when pass has a space character, then return SpacesNotAllowed`() {
        val pass = "1AB CDEf#"
        val result = useCase(pass, true)
        assert(result is WeakPass)
    }

    @Test
    fun `when pass has all requirements, then return Correct`() {
        val pass = "123Ab#"
        val result = useCase(pass, true)
        assert(result is Correct)
    }

    @Test
    fun `when pass has all requirements and is longer than minimum length, then return Correct`() {
        val pass = "123Ab#AAA"
        val result = useCase(pass, true)
        assert(result is Correct)
    }

    @Test
    fun `when pass has validate pattern disabled, then return Correct`() {
        val pass = "1"
        val result = useCase(pass, false)
        assert(result is Correct)
    }

}