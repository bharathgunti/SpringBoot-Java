package com.example.ExcelOperations.Service;

import com.example.ExcelOperations.Model.Customer;
import com.example.ExcelOperations.Repository.CustomerRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.io.ByteArrayOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletOutputStream;


import static java.lang.System.out;


@Service
public class ExcelService {

    @Autowired
    private CustomerRepository customerRepository;

        public static boolean ifValidFile(MultipartFile file){
            return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
        }

        public static List<Customer> getCustomersFromExcel(InputStream inputStream){
            List<Customer> customersList=new ArrayList<>();
            try{
                XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
                XSSFSheet sheet=workbook.getSheet("customers");

                int rowIndex=0;

                for(Row row:sheet){

                    if(rowIndex==0){
                        rowIndex++;
                        continue;
                    }

                    Iterator<Cell> cellIterator=row.iterator();
                    int cellIndex=0;

                    Customer customer=new Customer();

                    while(cellIterator.hasNext()){
                        Cell cell= cellIterator.next();

                        switch(cellIndex){
                            case 0 ->customer.setCustomerId((int)cell.getNumericCellValue());
                            case 1 ->customer.setFirstName(cell.getStringCellValue());
                            case 2 ->customer.setLastName(cell.getStringCellValue());
                            case 3 ->customer.setCountry(cell.getStringCellValue());
                            case 4 ->customer.setPhone((int) cell.getNumericCellValue());
                            default ->{

                            }
                        }
                        cellIndex++;
                    }
                    customersList.add(customer);
                }
            }
            catch (Exception ex){
                ex.getStackTrace();
                out.println("Error Message"+" "+ex.getMessage());

            }
            return customersList;

        }

        public ByteArrayInputStream exportCustomersToExcel(List<Customer> customers)throws IOException {
            try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {

//            XSSFWorkbook workbook=new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("customers");

                Row row = sheet.createRow(0);
                String rowsHeader[] = {"Customer Id", "First Name", "Last Name", "Country", "Phone"};

                for (int i = 0; i < rowsHeader.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(rowsHeader[i]);
                }

                int rowIndex = 1;

                for (Customer customer : customers) {
                    Row rows = sheet.createRow(rowIndex++);
                    rows.createCell(0).setCellValue(customer.getCustomerId());
                    rows.createCell(1).setCellValue(customer.getFirstName());
                    rows.createCell(2).setCellValue(customer.getLastName());
                    rows.createCell(3).setCellValue(customer.getCountry());
                    rows.createCell(4).setCellValue(customer.getPhone());
                }

                workbook.write(outStream);
                return new ByteArrayInputStream(outStream.toByteArray());


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error"+" "+e.getMessage());
                throw new RuntimeException(e);
            }



        }


}
