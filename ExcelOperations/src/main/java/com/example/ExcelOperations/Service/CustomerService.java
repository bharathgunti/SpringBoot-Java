package com.example.ExcelOperations.Service;

import com.example.ExcelOperations.Model.Customer;
import com.example.ExcelOperations.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void saveCustomersToDatabase(MultipartFile file) {

        if(ExcelService.ifValidFile(file)){
            try{
                List<Customer> customers=ExcelService.getCustomersFromExcel(file.getInputStream());
                customerRepository.saveAll(customers);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }

    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
