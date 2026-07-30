package com.dmm.bootcamp.yatter.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data class UpdateKey(val username: String) : YatterNavKey{
}
