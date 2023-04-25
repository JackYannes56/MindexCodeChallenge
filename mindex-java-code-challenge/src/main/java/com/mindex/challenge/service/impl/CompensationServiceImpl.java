package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        //check if compensation provided an employee. If not, throw an error
        Employee compensationEmployee = compensation.getEmployee();
        if (compensationEmployee == null){
            throw new RuntimeException("Invalid employee: "+compensationEmployee);
        }

        //check if employee actually exists. If not, throw an error
        String employeeId = compensationEmployee.getEmployeeId();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        //check if a Compensation already exists for this employee. If not, throw an error
        else if (compensationRepository.findByEmployeeEmployeeId(employeeId) != null){
            throw new RuntimeException("Compensation with employeeId: " + employeeId + " already exists.");
        }
        
        compensation.setCompensationId(UUID.randomUUID().toString());
        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Creating compensation with id [{}]", id);

        Compensation compensation = compensationRepository.findByEmployeeEmployeeId(id);

        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + id + ". No Compensation exists for the provided employeeId.");
        }

        return compensation;
    }
}
