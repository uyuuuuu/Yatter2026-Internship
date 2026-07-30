package com.dmm.bootcamp.yatter.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data class RegisterProfileKey(val username: String) : YatterNavKey {
}
