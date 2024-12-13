package com.nhnacademy.taskapi.customer.service.impl;

import com.nhnacademy.taskapi.customer.domain.Customer;
import com.nhnacademy.taskapi.customer.dto.CustomerRegisterDto;
import com.nhnacademy.taskapi.customer.exception.CustomerNotFoundException;
import com.nhnacademy.taskapi.customer.respository.CustomerRepository;
import com.nhnacademy.taskapi.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    // 전체 고객 조회
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // 인조키(id)로 고객 조회
    @Override
    public Customer getCustomerById(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(Objects.isNull(customer)) {
            throw new CustomerNotFoundException("Customer not found by id");
        }
        return customer;
    }

    // email로 고객 조회
    @Override
    public Customer getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        if(Objects.isNull(customer)) {
            throw new CustomerNotFoundException("Customer not found by email");
        }
        return customer;
    }

    // 고객 인조키(id)가 존재하는지 확인
    @Override
    public boolean existsCustomerById(String id) {
        return customerRepository.existsById(id);
    }

    // 고객 email이 존재하는지 확인
    public boolean existsCustomerByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    // 고객 정보 저장
    @Override
    public Customer registerCustomer(CustomerRegisterDto customerRegisterDto) {
        Customer customer = new Customer(
                customerRegisterDto.getName(),
                customerRegisterDto.getEmail()
        );
        return customerRepository.save(customer);
    }

}
