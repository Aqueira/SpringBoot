package org.example.configurations.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class CacheInvalidationListener {
    private final Logger logger = LoggerFactory.getLogger(CacheInvalidationListener.class);

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
    @Caching(evict = {
            @CacheEvict(value = "userCache", key = "#id"),
            @CacheEvict(value = "customerCache", key = "#id")
    })
    public void handleInvalidation(Long id) {
        logger.warn("Cache #{} -> invalidated successfully!", id);
    }
}
