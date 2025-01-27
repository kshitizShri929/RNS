package com.company.rns;

import org.apache.camel.builder.RouteBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DBToValkeyRoute extends RouteBuilder {

    @Inject
    NumberRepository numberRepository;

    @Override
    public void configure() throws Exception {
        from("timer:fetchDB?period=500000000000") // Run every 5 seconds
            .routeId("db-to-valkey")
            .log("Fetching numbers from DB")
            .process(exchange -> {
                var numbers = numberRepository.listAllNumbers();
                for (var number : numbers) {
                    numberRepository.insertIntoValkeyDirectly(
                        "number:" + number.value,
                        String.valueOf(number.value)
                    );
                }
            })
            .log("Numbers successfully transferred to Valkey.");
    }
}
