package com.jgpleo.chitchatt.logon.domain.usecase

import com.jgpleo.chitchatt.logon.domain.usecase.IsEmailValidUseCase.EmailStatus.*
import org.junit.Test

class IsEmailValidUseCaseTest {

    private val useCase: IsEmailValidUseCase = IsEmailValidUseCase()

    @Test
    fun `when email is empty, then returns Empty`() {
        val email = ""
        val result = useCase(email)
        assert(result is Empty)
    }

    @Test
    fun `when email username hasn't at least 2 characters, then return InvalidFormat`() {
        val email = "a@mail.com"
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when address hasn't server, then returns InvalidFormat`() {
        val email = "Julia.abc@"
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when email hasn't top level domain, then returns InvalidFormat`() {
        val email = "Samantha@com"
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when email is wrong, then returns InvalidFormat`() {
        val email = "Samantha_21."
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when email is wrong 2, then returns InvalidFormat`() {
        val email = ".1Samantha"
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when email hasn't top level domain, then return InvalidFormat`() {
        val email = "Samantha@10_2A"
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when email is wrong 3, then returns InvalidFormat`() {
        val email = "JuliaZ007"
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when email hasn't server parter, then returns InvalidFormat`() {
        val email = "_Julia007.com"
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when email has more than one top level domain, then return InvalidFormat`() {
        val email = "_Julia007@abc.co.in"
        val result = useCase(email)
        assert(result is InvalidFormat)
    }

    @Test
    fun `when email is correct, then return Correct`() {
        val email = "Julia@007.com"
        val result = useCase(email)
        assert(result is Correct)
    }

    @Test
    fun `when email is correct 2, then return Correct`() {
        val email = "Julia.007@abc.com"
        val result = useCase(email)
        assert(result is Correct)
    }

}