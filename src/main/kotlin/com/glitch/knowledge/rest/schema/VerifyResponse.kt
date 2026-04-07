package com.glitch.knowledge.rest.schema

import com.fasterxml.jackson.annotation.JsonProperty

data class VerifyResponse(
    @get:JsonProperty val status: Int,
    @get:JsonProperty val message: String?,
    @get:JsonProperty("origin_id") val originId: Int? = null,
)