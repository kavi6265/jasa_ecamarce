package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    // Add Address
    public Address addAddress(Long userId, Address address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }

    // Get All Addresses
    public List<Address> getAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    // Delete Address
    public String deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        addressRepository.delete(address);

        return "Address deleted successfully";
    }
}