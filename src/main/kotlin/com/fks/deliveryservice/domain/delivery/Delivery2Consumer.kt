package com.fks.deliveryservice.domain.delivery

import org.springframework.messaging.MessageHeaders

interface Delivery2Consumer {

    fun consume(deliveries: List<Delivery>, messageHeaders: MessageHeaders)
}
