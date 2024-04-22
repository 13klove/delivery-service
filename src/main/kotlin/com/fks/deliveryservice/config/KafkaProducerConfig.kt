package com.fks.deliveryservice.config

import com.fks.deliveryservice.domain.delivery.Delivery
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig(
    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    private val hosts: String
) {

    @Bean
    fun deliveryKafkaTemplate(): KafkaTemplate<String, Delivery> {
        return KafkaTemplate(deliveryProducerFactory())
    }

    private fun deliveryProducerFactory(): DefaultKafkaProducerFactory<String, Delivery> {
        return DefaultKafkaProducerFactory<String, Delivery>(props())
    }

    private fun props(): Map<String, Any> {
        return mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to hosts,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
            ProducerConfig.ACKS_CONFIG to "all",
            ProducerConfig.RETRY_BACKOFF_MS_CONFIG to 500,
            ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG to 3000,
            ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG to 12400,
            ProducerConfig.LINGER_MS_CONFIG to 100,
            ProducerConfig.BATCH_SIZE_CONFIG to 1000000
        )
    }

}
