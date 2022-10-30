package com.jgpleo.chitchatt.logon.domain.usecase

import com.jgpleo.chitchatt.logon.domain.usecase.PassAndValidationAreEqualsAndValidUseCase.PassStatus
import org.junit.Test

class PassAndValidationAreEqualsAndValidUseCaseTest {

    private val passUseCase = IsPassValidUseCase()
    private val useCase = PassAndValidationAreEqualsAndValidUseCase(passUseCase)

    @Test
    fun `when pass and validate pass are empty, then return BothEmpty`() {
        val result = useCase("", "")
        assert(result is PassStatus.BothEmpty)
    }

    @Test
    fun `when pass is empty, then return PassEmpty`() {
        val result = useCase("", "asd")
        assert(result is PassStatus.PassEmpty)
    }

    @Test
    fun `when validate pass is empty, then return PassEmpty`() {
        val result = useCase("asd", "")
        assert(result is PassStatus.ValidatePassEmpty)
    }

    @Test
    fun `when pass and validate pass are not equals, then return NotEquals`() {
        val result = useCase("asd", "def")
        assert(result is PassStatus.NotEquals)
    }

    @Test
    fun `when pass and validate pass are equals and pass has not minimum requirements, then return WeakPass`() {
        val result = useCase("asd", "asd")
        assert(result is PassStatus.WeakPass)
    }

    @Test
    fun `when pass and validate pass are equals and pass has minimum requirements, then return WeakPass`() {
        val result = useCase("Asd123#", "Asd123#")
        assert(result is PassStatus.Correct)
    }

}