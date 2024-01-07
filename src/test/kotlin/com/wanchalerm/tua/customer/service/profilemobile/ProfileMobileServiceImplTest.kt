package com.wanchalerm.tua.customer.service.profilemobile

import com.wanchalerm.tua.common.exception.NoContentException
import com.wanchalerm.tua.customer.model.entity.ProfilesMobileEntity
import com.wanchalerm.tua.customer.repository.ProfileMobileRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class ProfileMobileServiceImplTest {

    @InjectMocks
    private lateinit var profileMobileService: ProfileMobileServiceImpl

    @Mock
    private lateinit var profileMobileRepository: ProfileMobileRepository

    @Test
    fun `get mobile number should be success`() {
        val profilesMobileEntity = ProfilesMobileEntity(mobileNumber = "00000222")
        whenever(profileMobileRepository.findFirstByMobileNumberAndIsDeletedFalse(any())).thenReturn(profilesMobileEntity)
        val result = profileMobileService.getMobileNumber("00000222")
        Assertions.assertEquals("00000222", result.mobileNumber)
        verify(profileMobileRepository, times(1)).findFirstByMobileNumberAndIsDeletedFalse(any())
    }

    @Test
    fun `get mobile number got null should be error exception`() {
        whenever(profileMobileRepository.findFirstByMobileNumberAndIsDeletedFalse(any())).thenReturn(null)
        assertThrows<NoContentException> {  profileMobileService.getMobileNumber("00000222")  }
        verify(profileMobileRepository, times(1)).findFirstByMobileNumberAndIsDeletedFalse(any())
    }
}