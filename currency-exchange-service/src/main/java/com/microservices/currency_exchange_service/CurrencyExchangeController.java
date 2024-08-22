package com.microservices.currency_exchange_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
public class CurrencyExchangeController {
    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
    @Autowired
    private Environment environment;
    @Autowired
    private CurrencyExchangeRepository repository;
    CurrencyExchange currencyExchange;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        /*CurrencyExchange exchangeValue = new
                CurrencyExchange(1000L, from, to, BigDecimal.valueOf(65L),
                                Integer.parseInt(environment.getProperty("local.server.port")));*/
        logger.info("retrieveExchangeValue call with {} to {}", from, to);
        String port = environment.getProperty("local.server.port");
        currencyExchange = repository.findByFromAndTo(from, to);
        if (Objects.isNull(currencyExchange)) {
            throw new RuntimeException("Unable to Find data from " + from + " to " + to);
        }
        currencyExchange.setPort(port);
        return currencyExchange;
    }
}
