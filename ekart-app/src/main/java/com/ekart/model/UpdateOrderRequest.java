package com.ekart.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateOrderRequest(

    @NotBlank(message = "Customer name is required")
    String customerName,

    @Email(message = "Email must be a valid address")
    @NotBlank(message = "Email is required")
    String email
) {}
