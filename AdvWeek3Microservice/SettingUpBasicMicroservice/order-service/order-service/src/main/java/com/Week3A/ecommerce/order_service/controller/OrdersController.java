package com.Week3A.ecommerce.order_service.controller;


import com.Week3A.ecommerce.order_service.dto.OrderRequestDto;
import com.Week3A.ecommerce.order_service.service.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(HttpServletRequest httpServletRequest){
        log.info("Fetching all orders via controller");
        List<OrderRequestDto> orders= ordersService.getAllOrders();
        return  ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id){
        log.info("Fetvhing order with ID: {} via controller", id);
        OrderRequestDto order= ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
