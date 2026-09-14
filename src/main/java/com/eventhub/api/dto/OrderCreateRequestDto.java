package com.eventhub.api.dto;

import com.eventhub.api.entity.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequestDto(
        @NotNull
        UUID customerId,
        @NotEmpty
        List<@Valid OrderItemRequestDto> items,
        @NotBlank
        @Size(min = 3, max = 3)
        String currency,
        @NotNull
        @Valid
        ShippingAddressDto shippingAddress,
        @NotNull
        PaymentMethod paymentMethod

) {

}
