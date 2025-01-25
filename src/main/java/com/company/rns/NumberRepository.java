package com.company.rns;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class NumberRepository {
    @Inject
    RedisDataSource redisDataSource;
    private final ValueCommands<String, String> cache;

    public NumberRepository(RedisDataSource redisDataSource) {
        this.cache = redisDataSource.value(String.class);
    }

    public List<NumberEntity> listAllNumbers() {
        return NumberEntity.listAll();
    }

    @Transactional
    public NumberEntity addNumber(int value) {
        NumberEntity number = new NumberEntity();
        number.value = value;
        number.persist();
        cache.set("number:" + number.id, String.valueOf(value));
        return number;
    }

    public NumberEntity getNumber(Long id) {
        String cachedValue = cache.get("number:" + id);
        if (cachedValue != null) {
            NumberEntity cachedNumber = new NumberEntity();
            cachedNumber.id = id;
            cachedNumber.value = Integer.parseInt(cachedValue);
            return cachedNumber;
        }
        return NumberEntity.findById(id);
    }

    @Transactional
    public NumberEntity updateNumber(Long id, int newValue) {
        NumberEntity number = NumberEntity.findById(id);
        if (number != null) {
            number.value = newValue;
            cache.set("number:" + id, String.valueOf(newValue));
        }
        return number;
    }

    // @Transactional
    // public void deleteNumber(Long id) {
    //     NumberEntity.deleteById(id);
    //     //cache.delete("number:" + id);
    //     cache.delete(key);

    // }
}