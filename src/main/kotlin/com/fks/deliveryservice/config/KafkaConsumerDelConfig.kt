package com.fks.deliveryservice.config

import com.fks.deliveryservice.domain.delivery.Delivery
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.*
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.retry.support.RetryTemplateBuilder
import org.springframework.util.backoff.FixedBackOff


@Configuration
class KafkaConsumerDelConfig(
    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    private val hosts: String,
    @Value("\${kafka.topic.delivery}")
    private val topic: String,
) {

    @Bean
    fun delivery2ConcurrentKafkaListenerContainerFactory(
        kafkaTemplate: KafkaTemplate<String, Delivery>
    ): ConcurrentKafkaListenerContainerFactory<String, Delivery> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Delivery>()
        factory.consumerFactory = consumerFactory()
        factory.isBatchListener = true
        SeekToCurrentBatchErrorHandler()

        val recoveringBatchErrorHandler = RetryingBatchErrorHandler(
            FixedBackOff(5000L, 3L),
            DeadLetterPublishingRecoverer(kafkaTemplate) { rc, _ ->
                TopicPartition(
                    "$topic.dlq2",
                    rc.partition()
                )
            }
        )
        factory.setBatchErrorHandler(recoveringBatchErrorHandler)
        return factory
    }

    private fun deliveryRetryTemplate(): RetryTemplate {
        return RetryTemplateBuilder()
            .fixedBackoff(1000L)
            .customPolicy(
                SimpleRetryPolicy(3)
            )
            .build()
    }

    private fun consumerFactory(): DefaultKafkaConsumerFactory<String, Delivery> {
        val jsonDeserializer = JsonDeserializer(Delivery::class.java).apply {
            this.setRemoveTypeHeaders(false)
            this.setUseTypeMapperForKey(true)
            this.trustedPackages("*")
        }

        return DefaultKafkaConsumerFactory(
            consumerProps(),
            StringDeserializer(),
            jsonDeserializer
        )
    }

    private fun consumerProps(): Map<String, Any> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to hosts,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to "1000",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to true,
        )
    }

}
