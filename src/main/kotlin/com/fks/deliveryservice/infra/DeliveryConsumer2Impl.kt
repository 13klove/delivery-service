package com.fks.deliveryservice.infra

import com.fks.deliveryservice.domain.delivery.Delivery
import com.fks.deliveryservice.domain.delivery.Delivery2Consumer
import com.fks.deliveryservice.domain.delivery.DeliveryConsumer
import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.lang.IllegalArgumentException
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@Service
class DeliveryConsumer2Impl : Delivery2Consumer {

    companion object : KLogging()

    @KafkaListener(
        groupId = "group-delivery2",
        topics = ["fks.delivery"],
        containerFactory = "delivery2ConcurrentKafkaListenerContainerFactory",
    )
    override fun consume(@Payload deliveries: List<Delivery>, @Headers messageHeaders: MessageHeaders) {
        logger.info { "consumer delivery item: ${deliveries.size}" }
        throw IllegalArgumentException("aaa")
    }

    @KafkaListener(
        groupId = "group-delivery-dlq2",
        topics = ["fks.delivery.dlq2"],
        containerFactory = "delivery2ConcurrentKafkaListenerContainerFactory"
    )
    fun consumeDlQ(@Payload delivery: Delivery) {
        logger.info { "consumer dlq delivery: ${delivery.orderId}" }
    }

}
