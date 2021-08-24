package com.example.SpringBootApp1.controller;

import com.example.SpringBootApp1.exception.RecordNotFoundException;
import com.example.SpringBootApp1.model.Emp;
import com.example.SpringBootApp1.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class EmpController
{
    @Autowired
    EmpService empService;

    /* To display all employees */
    @RequestMapping(path = "/employees", method = RequestMethod.GET)
    List<Emp> findAllEmployees()
    {
        return empService.findAllEmployees();
    }

    /* To display all employees */
    @RequestMapping(path = "/employees/fname/{FNAME}", method = RequestMethod.GET)
    List<Emp> getAllEmployeesByFName(@PathVariable("FNAME") String fname)
    {
        return empService.getEmployeesByFName(fname);
    }

    @RequestMapping(path = "/employees/lname/{LNAME}", method = RequestMethod.GET)
    List<Emp> getAllEmployeesByLName(@PathVariable("LNAME") String lname)
    {
        return empService.getEmployeesByLName(lname);
    }

    @RequestMapping(path = "/employees/name/{FNAME}/{LNAME}", method = RequestMethod.GET)
    List<Emp> getAllEmployeesByName(@PathVariable("FNAME") String fname, @PathVariable("LNAME") String lname)
    {
        return empService.getEmployeesByName(fname, lname);
    }

    @RequestMapping(path = "/employees/update/{empid}/{FNAME}", method = RequestMethod.GET)
    List<Emp> updateAndGetAllEmployee(@PathVariable("empid") int empId, @PathVariable("FNAME") String fname)
    {
        int result = empService.updateEmployeesByName(empId, fname);
        log.info("Number of Records got updated is :" + result);
        return empService.findAllEmployees();
    }

    /* To display Employee By Id */
    @RequestMapping(path = "/employees/{EMPID}", method = RequestMethod.GET)
    ResponseEntity<Emp> findEmpById(@PathVariable(name = "EMPID") Integer empId)
    {
        log.info("Finding Employee By Id :" + empId);
        Emp emp =  empService.findEmpById(empId);
        return ResponseEntity.ok(emp);
        /* return new ResponseEntity<Emp>(emp, HttpStatus.OK);*/
    }

    /* To delete Employee By Id */
    @RequestMapping(path = "/employees/delete", method = RequestMethod.GET)
    ResponseEntity<Map> deleteEmp(@RequestParam(name = "empid") Integer empId)
    {
        empService.deleteEmpByID(empId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    /* To update the emp */
    @RequestMapping(path = "/employees/updateEmp", method = RequestMethod.PUT)
    ResponseEntity<Emp> updateEmp(@Valid @RequestBody Emp empBody)
    {
        log.info("Finding Employee By Id :" + empBody.getEmpId());
        Emp emp =  empService.findEmpById(empBody.getEmpId());
        log.info(String.format("Employee with emp id %s found.",emp.getEmpId()));
        emp.setEmailId(empBody.getEmailId());
        emp.setSalary(empBody.getSalary());
        emp.setFname(empBody.getFname());
        emp.setLname(empBody.getLname());

        return ResponseEntity.ok(empService.saveOrUpdateEmp(emp));
    }

    /* To save the emp */
    /* By default, it consumes JSON Values only. */
    @RequestMapping(path = "/employees/saveEmp", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Emp> saveEmp(@Valid @RequestBody Emp empBody)
    {
        return ResponseEntity.ok(empService.saveOrUpdateEmp(empBody));
    }

    /* Transaction Management */
    @RequestMapping(path = "/employees/transaction/{status}", method = RequestMethod.GET)
    ResponseEntity<List<Emp>> saveEmp(@PathVariable("status") String status )
    {
        return ResponseEntity.ok(empService.createAndSaveEmps1(status));
    }

}
