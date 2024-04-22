package com.fks.deliveryservice.domain.delivery

import javax.validation.constraints.NotBlank

class Delivery(
    @field:NotBlank(message = "orderId is blank")
    val orderId: String
)
