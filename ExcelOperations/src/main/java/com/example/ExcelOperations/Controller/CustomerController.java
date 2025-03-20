package com.example.ExcelOperations.Controller;

import com.example.ExcelOperations.Model.Customer;
import com.example.ExcelOperations.Service.CustomerService;
import com.example.ExcelOperations.Service.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file")MultipartFile file){
        customerService.saveCustomersToDatabase(file);
        return ResponseEntity.ok("Customers Saved Sucessfully");
    }

    @GetMapping()
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.FOUND);
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportCustomersToExcel() throws IOException {
        List<Customer> customers=customerService.getCustomers();

        ByteArrayInputStream in= excelService.exportCustomersToExcel(customers);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=customers.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }



}
