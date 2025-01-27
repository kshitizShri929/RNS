// package com.company.rns;

// import io.quarkus.redis.datasource.RedisDataSource;
// import io.quarkus.redis.datasource.value.ValueCommands;
// import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.inject.Inject;
// import jakarta.transaction.Transactional;
// import java.util.List;

// @ApplicationScoped
// public class NumberRepository {
//     @Inject
//     RedisDataSource redisDataSource;
//     private final ValueCommands<String, String> cache;

//     public NumberRepository(RedisDataSource redisDataSource) {
//         this.cache = redisDataSource.value(String.class);
//     }

//     public List<NumberEntity> listAllNumbers() {
//         return NumberEntity.listAll();
//     }

//     @Transactional
//     public NumberEntity addNumber(int value) {
//         NumberEntity number = new NumberEntity();
//         number.value = value;
//         number.persist();
//         cache.set("number:" + number.id, String.valueOf(value));
//         return number;
//     }

//     public NumberEntity getNumber(Long id) {
//         String cachedValue = cache.get("number:" + id);
//         if (cachedValue != null) {
//             NumberEntity cachedNumber = new NumberEntity();
//             cachedNumber.id = id;
//             cachedNumber.value = Integer.parseInt(cachedValue);
//             return cachedNumber;
//         }
//         return NumberEntity.findById(id);
//     }

//     @Transactional
//     public NumberEntity updateNumber(Long id, int newValue) {
//         NumberEntity number = NumberEntity.findById(id);
//         if (number != null) {
//             number.value = newValue;
//             cache.set("number:" + id, String.valueOf(newValue));
//         }
//         return number;
//     }

//     // @Transactional
//     // public void deleteNumber(Long id) {
//     //     NumberEntity.deleteById(id);
//     //     //cache.delete("number:" + id);
//     //     cache.delete(key);

//     // }
// }



// package com.company.rns;

// import io.quarkus.redis.datasource.RedisDataSource;
// import io.quarkus.redis.datasource.value.ValueCommands;
// import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.inject.Inject;
// import jakarta.transaction.Transactional;
// import java.util.List;
// import java.util.Random;

// @ApplicationScoped
// public class NumberRepository {
//     @Inject
//     RedisDataSource redisDataSource;
//     private final ValueCommands<String, String> cache;

//     public NumberRepository(RedisDataSource redisDataSource) {
//         this.cache = redisDataSource.value(String.class);
//     }

//     public List<NumberEntity> listAllNumbers() {
//         return NumberEntity.listAll();
//     }

//     @Transactional
//     public NumberEntity addNumber(long value) {
//         NumberEntity number = new NumberEntity();
//         number.value = value;
//         number.persist();
//         cache.set("number:" + number.value, String.valueOf(value)); // Store in Redis
//         return number;
//     }

//     @Transactional
//     public void insertRandomNumbers(int count) {
//         Random random = new Random();
//         for (int i = 0; i < count; i++) {
//             // Generate a random 12-digit number
//             long randomNumber = 100000000000L + Math.abs(random.nextLong() % 900000000000L);

//             // Log the generated number
//             System.out.println("Generated 12-digit number: " + randomNumber);

//             addNumber(randomNumber); // Store it in DB and Redis
//         }
//     }

//     public NumberEntity getNumber(Long id) {
//         String cachedValue = cache.get("number:" + id);
//         if (cachedValue != null) {
//             NumberEntity cachedNumber = new NumberEntity();
//             cachedNumber.id = id;
//             cachedNumber.value = Long.parseLong(cachedValue); // Use long
//             return cachedNumber;
//         }
//         return NumberEntity.findById(id);
//     }

//     @Transactional
//     public NumberEntity updateNumber(Long id, long newValue) {
//         NumberEntity number = NumberEntity.findById(id);
//         if (number != null) {
//             number.value = newValue;
//             cache.set("number:" + id, String.valueOf(newValue));
//         }
//         return number;
//     }

//     // @Transactional
//     // public void insertIntoValkeyDirectly(String key, String value) {
//     //         boolean exists = cache.get(key) != null;
//     //         if (exists) {
//     //             throw new RuntimeException("Duplicate key error: " + key + " already exists in Redis.");
//     //         } else {
//     //             cache.set(key, value);
//     //     }
//     // }

//     @Transactional
//     public String insertIntoValkeyDirectly(String key, String value) {
//         // Check if the key already exists in the cache
//         boolean exists = cache.get(key) != null;
        
//         // If the key exists, return a message
//         if (exists) {
//             return "Duplicate key: " + key + " already exists in Redis.";
//         } else {
//             // If the key does not exist, insert the new key-value pair
//             cache.set(key, value);
//             return "Key-value pair inserted successfully.";
//         }
//     }
    
// }


package com.company.rns;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.quarkus.redis.datasource.value.SetArgs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class NumberRepository {
    private final ValueCommands<String, String> cache;

    @Inject
    public NumberRepository(RedisDataSource redisDataSource) {
        this.cache = redisDataSource.value(String.class);
    }

    @Transactional
    public List<NumberEntity> listAllNumbers() {
        return NumberEntity.listAll();
    }

    @Transactional
    public NumberEntity addNumber(long value) {
        NumberEntity number = new NumberEntity(value);
        number.persist();
        cache.set("key:" + number.value, String.valueOf(value)); // Store in Valkey
        return number;
    }

    @Transactional
    public void insertRandomNumbers(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            long randomNumber = 100000000000L + Math.abs(random.nextLong() % 900000000000L);
            System.out.println("Generated 12-digit number: " + randomNumber);


            addNumber(randomNumber);
        }
    }

    @Transactional
    public String insertIntoValkeyDirectly(String key, String value) {
        try {
            // Use SetArgs().nx() to ensure the key is only set if it does not exist
            cache.set(key, value, new SetArgs().nx());
            return "Key inserted successfully.";
        } catch (Exception e) {
            return "Duplicate key: " + key + " already exists in Valkey.";
        }
    }
}
