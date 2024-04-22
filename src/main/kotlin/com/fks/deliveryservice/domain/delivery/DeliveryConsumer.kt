package com.fks.deliveryservice.domain.delivery

import org.springframework.messaging.MessageHeaders

interface DeliveryConsumer {

    fun consume(delivery: Delivery, messageHeaders: MessageHeaders)
}
