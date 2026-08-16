package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/{userId}")
    public Address addAddress(@PathVariable Long userId,
                              @RequestBody Address address) {

        return addressService.addAddress(userId, address);
    }

    @GetMapping("/{userId}")
    public List<Address> getAddresses(@PathVariable Long userId) {

        return addressService.getAddresses(userId);
    }

    @DeleteMapping("/{addressId}")
    public String deleteAddress(@PathVariable Long addressId) {

        return addressService.deleteAddress(addressId);
    }
}