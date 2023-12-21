package com.wanchalerm.tua.customer.service.profilepassword

import com.wanchalerm.tua.customer.repository.ProfilePasswordRepository
import org.springframework.stereotype.Service

@Service
class ProfilePasswordServiceImpl(private val profilePasswordRepository: ProfilePasswordRepository) : ProfilePasswordService {

}