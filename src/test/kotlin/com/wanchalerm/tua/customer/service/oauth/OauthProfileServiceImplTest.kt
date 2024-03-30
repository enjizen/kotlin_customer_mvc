package com.wanchalerm.tua.customer.service.oauth

import com.wanchalerm.tua.common.exception.BusinessException
import com.wanchalerm.tua.common.exception.InputValidationException
import com.wanchalerm.tua.common.exception.NoContentException
import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesEmailEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesMobileEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesPasswordEntity
import com.wanchalerm.tua.customer.service.profileemail.ProfileEmailService
import com.wanchalerm.tua.customer.service.profilemobile.ProfileMobileService
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class OauthProfileServiceImplTest {

    @InjectMocks
    private lateinit var oauthService: OauthProfileServiceImpl

    @Mock
    private lateinit var profileEmailService: ProfileEmailService

    @Mock
    private lateinit var profileMobileService: ProfileMobileService

    private lateinit var profilesPasswordEntity: ProfilesPasswordEntity
    private lateinit var profileEntity: ProfileEntity
    private lateinit var profilesEmailEntity: ProfilesEmailEntity

    private lateinit var profilesMobileEntity: ProfilesMobileEntity

    @BeforeEach
    fun setup() {
        profilesPasswordEntity = ProfilesPasswordEntity(
            1,
            "8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414",
            1,
            isDeleted = false
        )

        profileEntity = ProfileEntity(
            1,
            code = "b7be0453-c799-48cb-9ba8-61fce4c203a2",
            profilesPasswords = mutableSetOf(profilesPasswordEntity)
        )

        profilesEmailEntity = ProfilesEmailEntity(
            1,
            "email@gmail.com",
            LocalDateTime.now(),
            LocalDateTime.now(),
            isDeleted = false,
            profile = profileEntity
        )

       profilesMobileEntity = ProfilesMobileEntity(1, "0923388994", isDeleted = false, profile = profileEntity)

    }

    @Test
    fun `authentication with email should success`() {

        whenever(profileEmailService.getEmail(any())).thenReturn(profilesEmailEntity)

        val result = oauthService.authenticateWithEmail("email@gmail.com", "123456")

        assertEquals("b7be0453-c799-48cb-9ba8-61fce4c203a2", result)

        verify(profileEmailService, times(1)).getEmail(any())
        verify(profileMobileService, never()).getMobileNumber(any())

    }

    @Test
    fun `authentication with mobile should success`() {

        whenever(profileMobileService.getMobileNumber(any())).thenReturn(profilesMobileEntity)

        val result = oauthService.authenticateWithMobileNumber("0923388994", "123456")

        assertEquals("b7be0453-c799-48cb-9ba8-61fce4c203a2", result)

        verify(profileEmailService, never()).getEmail(any())
        verify(profileMobileService, times(1)).getMobileNumber(any())

    }



}