//package com.fks.deliveryservice.infra
//
//import com.fks.deliveryservice.domain.delivery.Delivery
//import com.fks.deliveryservice.domain.delivery.DeliveryConsumer
//import mu.KLogging
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.messaging.MessageHeaders
//import org.springframework.messaging.handler.annotation.Headers
//import org.springframework.messaging.handler.annotation.Payload
//import org.springframework.stereotype.Service
//import org.springframework.validation.annotation.Validated
//import javax.validation.Valid
//import javax.validation.constraints.NotEmpty
//
//@Service
//class DeliveryConsumerImpl : DeliveryConsumer {
//
//    companion object : KLogging()
//
//    @KafkaListener(
//        groupId = "group-delivery",
//        topics = ["fks.delivery"],
//        containerFactory = "deliveryConcurrentKafkaListenerContainerFactory"
//    )
//    override fun consume(@Payload @Valid delivery: Delivery, @Headers messageHeaders: MessageHeaders) {
//        logger.info { "consumer delivery item: ${delivery.orderId}" }
//    }
//
//    @KafkaListener(
//        groupId = "group-delivery-dlq",
//        topics = ["fks.delivery.dlq"],
//        containerFactory = "deliveryConcurrentKafkaListenerContainerFactory"
//    )
//    fun consumeDlQ(@Payload delivery: Delivery) {
//        logger.info { "consumer dlq delivery: ${delivery.orderId}" }
//    }
//
//}
