package org.example;

import org.example.authentication.enums.Role;
import org.example.customer.application.CustomerService;
import org.example.user.application.UserService;
import org.example.user.dto.RequestUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;



import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testUpdateCustomerWithCachePut() {

        userService.read(3L);
        customerService.read(3L);


        // Получение кэша
        Cache cache = cacheManager.getCache("userCache");
        Cache cache1 = cacheManager.getCache("customerCache");
        System.out.println(cache1.get(3L));
        assertNotNull(cache1, "Кэш userCache не инициализирован");
        userService.delete(3L);
        Cache.ValueWrapper cachedValue = cache.get(3L);
        Cache.ValueWrapper cache1Value = cache1.get(3L);
        assertNull(cachedValue, "Значение для ключа 13L отсутствует в кэше");
        assertNull(cache1Value, "penis");

        // Если есть значение, вывести его
        System.out.println("Значение в кэше: " + cachedValue);
        System.out.println("Значение в кэше: " + cache1Value);
    }
}
