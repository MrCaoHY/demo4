package com.example.demo;

import com.example.demo.model.Coffee;
import com.example.demo.model.CoffeeOrder;
import com.example.demo.model.OrderState;
import com.example.demo.repository.CoffeeOrderRepository;
import com.example.demo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class Demo4Application implements ApplicationRunner {
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public static void main(String[] args) {
        SpringApplication.run(Demo4Application.class, args);
    }


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initOrders();
        findOrders();
    }

    private void findOrders() {
        coffeeOrderRepository
                .findAll(Sort.by(Sort.Direction.DESC,"id"))
                .forEach(coffeeOrder -> log.info("Loding: {}", coffeeOrder));
        List<CoffeeOrder> list = coffeeOrderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}",getJoinedOrderId(list));

        list = coffeeOrderRepository.findByCustomerOrderById("Li Lei");
        log.info("findByCustomerOrderById:{}",getJoinedOrderId(list));

        list.forEach(o->{
            log.info("Order: {}", o.getId());
            o.getItems().forEach(i->log.info("Iterm:{}",i));
        });
        list  = coffeeOrderRepository.findByItems_Name("latte");
        log.info("findByItems_Name:{}",getJoinedOrderId(list));

    }

    private String getJoinedOrderId(List<CoffeeOrder> list) {
        return list.stream().map(o-> o.getId().toString()).collect(Collectors.joining(","));
    }


    private void initOrders() {
        Coffee espresso = Coffee.builder().name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"),20.0))
                .build();
        coffeeRepository.save(espresso);
        log.info("Coffee: {}",espresso);

        Coffee latte = Coffee.builder().name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"),30.0))
                .build();
        coffeeRepository.save(latte);
        log.info("Coffee: {}",latte);

        CoffeeOrder order = CoffeeOrder.builder().customer("Li Lei")
                .items(Collections.singletonList(espresso))
                .state(OrderState.INIT).build();
        coffeeOrderRepository.save(order);
        log.info("Order{}",order);

        order = CoffeeOrder.builder().customer("Li Lei")
                .items(Collections.singletonList(latte))
                .state(OrderState.INIT).build();
        coffeeOrderRepository.save(order);
        log.info("Order{}",order);

    }
}
