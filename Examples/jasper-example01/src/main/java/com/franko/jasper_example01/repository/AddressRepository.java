package com.franko.jasper_example01.repository;

import com.franko.jasper_example01.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
