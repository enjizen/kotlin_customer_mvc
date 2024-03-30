package com.wanchalerm.tua.customer.controller

import com.jayway.jsonpath.JsonPath
import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesEmailEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesMobileEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesPasswordEntity
import com.wanchalerm.tua.customer.service.profile.ProfileServiceImpl
import java.time.LocalDate
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ResourceLoader
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status




@SpringBootTest(properties = ["spring.main.lazy-initialization = true"])
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProfileControllerTest {


    @MockBean
    private lateinit var profileService: ProfileServiceImpl

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun create() {
        val resource =  resourceLoader.classLoader!!.getResourceAsStream("profile_create.json")
        val content = JsonPath.parse(resource).jsonString()

        whenever(profileService.create(any())).thenReturn(getProfile())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/profiles").content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.status.code", equalTo("2000")))
            .andExpect(jsonPath("$.status.message", equalTo("Success")))
            .andExpect(jsonPath("$.data.id", equalTo(1)))
            .andExpect(jsonPath("$.data.mobile_number", equalTo("0919845476")))
            .andExpect(jsonPath("$.data.email", equalTo("enj@gmail.com")))
        verify(profileService, times(1)).create(any())
    }

   @Test
    fun update() {
       val resource = resourceLoader.classLoader!!.getResourceAsStream("profile_update.json")
       val content = JsonPath.parse(resource).jsonString()

       whenever(profileService.update(any(), any(), any())).thenReturn(getProfile())

       mockMvc.perform(
           MockMvcRequestBuilders.put("/v1/profiles")
               .queryParam("id", "1")
               .queryParam("code", "b7be0453-c799-48cb-9ba8-61fce4c203a2")
               .content(content)
               .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk)
           .andExpect(jsonPath("$.data.id", equalTo(1)))
           .andExpect(jsonPath("$.data.mobile_number", equalTo("0919845476")))
           .andExpect(jsonPath("$.data.email", equalTo("enj@gmail.com")))
       verify(profileService, times(1)).update(any(), any(), any())
    }

   @Test
    fun delete() {
       doNothing().whenever(profileService).delete(any(), any())

       mockMvc.perform(
           MockMvcRequestBuilders.delete("/v1/profiles")
               .queryParam("id", "1")
               .queryParam("code", "b7be0453-c799-48cb-9ba8-61fce4c203a2")
               .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk)
       verify(profileService, times(1)).delete(any(), any())
    }

   @Test
    fun `update mobile number`() {
       val resource = resourceLoader.classLoader!!.getResourceAsStream("profile_update_mobile_number.json")
       val content = JsonPath.parse(resource).jsonString()

       whenever(profileService.updateMobileNumber(any(), any(), any())).thenReturn(getProfile())

       mockMvc.perform(
           MockMvcRequestBuilders.patch("/v1/profiles/mobile-number")
               .queryParam("id", "1")
               .queryParam("code", "b7be0453-c799-48cb-9ba8-61fce4c203a2")
               .content(content)
               .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk)
           .andExpect { jsonPath("$.status.code", equalTo("2000")) }
           .andExpect { jsonPath("$.status.message", equalTo("Success")) }
           .andExpect(jsonPath("$.data.id", equalTo(1)))
           .andExpect(jsonPath("$.data.mobile_number", equalTo("0919845476")))
           .andExpect(jsonPath("$.data.email", equalTo("enj@gmail.com")))
       verify(profileService, times(1)).updateMobileNumber(any(), any(), any())
    }

   @Test
    fun `update password`() {
       val resource = resourceLoader.classLoader!!.getResourceAsStream("profile_update_password.json")
       val content = JsonPath.parse(resource).jsonString()

       whenever(profileService.updatePassword(any(), any(), any())).thenReturn(getProfile())

       mockMvc.perform(
           MockMvcRequestBuilders.patch("/v1/profiles/password")
               .queryParam("id", "1")
               .queryParam("code", "b7be0453-c799-48cb-9ba8-61fce4c203a2")
               .content(content)
               .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk)
           .andExpect { jsonPath("$.status.code", equalTo("2000")) }
           .andExpect { jsonPath("$.status.message", equalTo("Success")) }
           .andExpect(jsonPath("$.data.id", equalTo(1)))
           .andExpect(jsonPath("$.data.mobile_number", equalTo("0919845476")))
           .andExpect(jsonPath("$.data.email", equalTo("enj@gmail.com")))
       verify(profileService, times(1)).updatePassword(any(), any(), any())
    }

    private fun getProfile() : ProfileEntity {
        val profilesMobileEntity = ProfilesMobileEntity(id = 1, mobileNumber = "0919845476", isDeleted = false)
        val profilesEmailEntity = ProfilesEmailEntity(id = 1, email = "enj@gmail.com", isDeleted = false)
        val profilesPasswordEntity = ProfilesPasswordEntity(id = 1, password = "123456", isDeleted = false)
        val profileEntity = ProfileEntity(id = 1, code = "code", firstName = "test", lastName = "lastname", birthDate = LocalDate.now(),
            profilesMobiles = mutableSetOf(profilesMobileEntity),
            profilesEmail = mutableSetOf(profilesEmailEntity),
            profilesPasswords = mutableSetOf(profilesPasswordEntity)
        )
        return profileEntity
    }
}