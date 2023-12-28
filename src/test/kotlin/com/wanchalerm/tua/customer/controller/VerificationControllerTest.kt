package com.wanchalerm.tua.customer.controller


import com.wanchalerm.tua.customer.service.oauth.OauthServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ResourceLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VerificationControllerTest {

    @MockBean
    private lateinit var oauthService: OauthServiceImpl

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    @Test
    fun `authentication with email`() {

    }
}