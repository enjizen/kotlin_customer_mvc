package com.wanchalerm.tua.customer.controller


import com.jayway.jsonpath.JsonPath
import com.wanchalerm.tua.customer.service.oauth.OauthProfileService
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VerificationControllerTest {

    @MockBean
    private lateinit var oauthProfileService: OauthProfileService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    @Test
    fun `authentication with email`() {

        val resource = resourceLoader.classLoader!!.getResourceAsStream("verify_pass.json")
        val content = JsonPath.parse(resource).jsonString()

        whenever(oauthProfileService.authentication(any(), any())).thenReturn("b7be0453-c799-48cb-9ba8-61fce4c203a2")

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/verify/pass").content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status.code", CoreMatchers.equalTo("2000")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status.message", CoreMatchers.equalTo("Success")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.customer_code", CoreMatchers.equalTo("b7be0453-c799-48cb-9ba8-61fce4c203a2")))
        verify(oauthProfileService, times(1)).authentication(any(), any())

    }
}