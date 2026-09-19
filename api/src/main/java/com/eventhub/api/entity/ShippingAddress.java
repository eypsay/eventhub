package com.eventhub.api.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShippingAddress {
    private String recipientName;
    private String addressLine;
    private String city;
    private String postalCode;
    private String country;

    protected ShippingAddress() {
    }

    public ShippingAddress(String recipientName, String addressLine, String city, String postalCode, String country) {
        this.recipientName = recipientName;
        this.addressLine = addressLine;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }
}
