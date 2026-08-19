package com.ekart.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(

    @NotBlank(message = "Customer name is required")
    String customerName,

    @Email(message = "Email must be a valid address")
    @NotBlank(message = "Email is required")
    String email,

    @NotNull(message = "Order date is required")
    LocalDate orderDate
) {}
