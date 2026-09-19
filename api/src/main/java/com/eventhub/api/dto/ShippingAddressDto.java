package com.eventhub.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ShippingAddressDto(
        @NotBlank
        @Size(max = 100)
        String recipientName,
        @NotBlank
        @Size(max = 255)
        String addressLine,
        @NotBlank
        @Size(max = 100)
        String city,
        @NotBlank
        @Size(max = 20)
        String postalCode,
        @NotBlank
        @Size(min = 2, max = 2)
        String country
) {
}
