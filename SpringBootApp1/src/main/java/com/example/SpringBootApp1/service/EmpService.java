package com.example.SpringBootApp1.service;

import com.example.SpringBootApp1.exception.RecordNotFoundException;
import com.example.SpringBootApp1.model.Emp;
import com.example.SpringBootApp1.repository.IEmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpService
{
    @Autowired
    IEmpRepository iEmpRepository;

    public List<Emp> findAllEmployees()
    {
        List<Emp> empList = new ArrayList<>();
        iEmpRepository.findAll().<Emp> forEach(emp -> {
            empList.add(emp);
        });
        return empList;
    }

    public Emp findEmpById(int empId)
    {
        return iEmpRepository.findById(empId).orElseThrow(() ->
        {
            return new RecordNotFoundException("No Employee Record found with Emp Id : " + empId);
        });
    }

    public Emp saveOrUpdateEmp(Emp emp)
    {
        return iEmpRepository.save(emp);
    }

    public void deleteEmp(Emp emp)
    {
         iEmpRepository.delete(emp);
    }

    public void deleteEmpByID(int empId)
    {
        iEmpRepository.deleteById(empId);
    }

    public List<Emp> getEmployeesByFName(String fname)
    {
        return iEmpRepository.getByFname(fname);
    }

    public List<Emp> getEmployeesByLName(String fname)
    {
        return iEmpRepository.getByLname(fname);
    }

    public List<Emp> getEmployeesByName(String fname, String lname)
    {
        return iEmpRepository.fetchByName(fname, lname);
    }

    public int updateEmployeesByName(int empId, String fname)
    {
        return iEmpRepository.updateEmpFirstName(empId, fname);
    }

    public Long getEmpCount()
    {
        return iEmpRepository.getEmpCount();
    }

    @Transactional( rollbackFor = {Exception.class, NullPointerException.class},
                    propagation = Propagation.REQUIRED,
                    isolation = Isolation.DEFAULT
                    )
    public List<Emp> createAndSaveEmps1(String status)
    {
        int empIdIncreamentBy = status.equalsIgnoreCase("FAIL")?200:100;
        List<Emp> empList = new ArrayList<>();
        Long empCount = getEmpCount();
        long nextEmpId = empCount + empIdIncreamentBy;
        Emp e = new Emp();
        e.setFname(String.format("ABC%s", nextEmpId));
        e.setLname(String.format("XYZ%s", nextEmpId));
        e.setEmailId(e.getFname() + "@gmail.com");
        e.setSalary((double) Math.round(Math.random()*10000));
        saveOrUpdateEmp(e);

        empList.add(e);
        empList.add(createAndSaveEmp2(status));
        return empList;
    }

    @Transactional( rollbackFor = {Exception.class, NullPointerException.class},
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT
    )
    Emp createAndSaveEmp2(String status)
    {
        int empIdIncreamentBy = status.equalsIgnoreCase("FAIL")?200:100;
        Long empCount = getEmpCount();
        long nextEmpId = empCount + empIdIncreamentBy;
        Emp e = new Emp();
        e.setFname(String.format("ABC%s", nextEmpId));
        e.setLname(String.format("XYZ%s", nextEmpId));
        e.setEmailId(e.getFname() + "@gmail.com");
        e.setSalary((double) Math.round(Math.random()*10000));

        if(status.equalsIgnoreCase("FAIL"))
        {
            throw new NullPointerException();
        }

        return saveOrUpdateEmp(e);
    }
}
