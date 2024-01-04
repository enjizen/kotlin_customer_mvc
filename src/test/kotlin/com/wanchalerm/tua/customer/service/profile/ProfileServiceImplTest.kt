package com.wanchalerm.tua.customer.service.profile


import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.request.ProfileCreateRequest
import com.wanchalerm.tua.customer.model.request.ProfileUpdateRequest
import com.wanchalerm.tua.customer.repository.ProfileEmailRepository
import com.wanchalerm.tua.customer.repository.ProfileMobileRepository
import com.wanchalerm.tua.customer.repository.ProfilePasswordRepository
import com.wanchalerm.tua.customer.repository.ProfileRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class ProfileServiceImplTest {
    @InjectMocks
    private lateinit var profileService: ProfileServiceImpl

    @Mock
    private lateinit var profileRepository: ProfileRepository

    @Mock
    private lateinit var profileEmailRepository: ProfileEmailRepository

    @Mock
    private lateinit var profileMobileRepository: ProfileMobileRepository

    @Mock
    private lateinit var profilePasswordRepository: ProfilePasswordRepository


    @Test
    fun create() {
        val profileCreateRequest = ProfileCreateRequest(
            firstName = "first_name",
            email = "www@gmail.com",
            mobileNumber = "0888773323",
            password = "123456"
        )
        val profileEntity = ProfileEntity(id = 1, firstName = "first_name")
        `when`(profileEmailRepository.existsByEmail(any())).thenReturn(false)
        `when`(profileMobileRepository.existsByMobileNumber(any())).thenReturn(false)
        `when`(profileRepository.save(any())).thenReturn(profileEntity)

        val result = profileService.create(profileCreateRequest)
        assertEquals("first_name", result.firstName)

        verify(profileEmailRepository, times(1)).existsByEmail(any())
        verify(profileMobileRepository, times(1)).existsByMobileNumber(any())
        verify(profileRepository, times(1)).save(any())
    }

    @Test
    fun update() {
        val profileUpdateRequest = ProfileUpdateRequest(firstName = "first_name", lastName = "last_name")
        val profileEntity = ProfileEntity(id = 1, firstName = "first_name", lastName = "last_name")
        `when`(profileRepository.findByIdAndCodeAndIsDeletedFalse(any(), any())).thenReturn(profileEntity)
        `when`(profileRepository.save(any())).thenReturn(profileEntity)

        val result = profileService.update(profileUpdateRequest, 1, "code")
        assertEquals("first_name", result.firstName)

        verify(profileRepository, times(1)).save(any())
        verify(profileRepository, times(1)).findByIdAndCodeAndIsDeletedFalse(any(), any())
    }

    @Test
    fun delete() {
        val profileEntity = ProfileEntity(id = 1, firstName = "first_name", lastName = "last_name")
        `when`(profileRepository.findByIdAndCodeAndIsDeletedFalse(any(), any())).thenReturn(profileEntity)

        profileService.delete(1, "code")

        verify(profileRepository, times(1)).save(any())
        verify(profileRepository, times(1)).findByIdAndCodeAndIsDeletedFalse(any(), any())
    }

    @Test
    fun updateMobileNumber() {
    }

    @Test
    fun updatePassword() {
    }

    @Test
    fun getByMobileNumber() {
    }
}