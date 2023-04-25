package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String employeeId){
        LOG.debug("Creating ReportingStructure with employeeId [{}]", employeeId);

        //check if employee with employeeId exists
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        
        int numberOfReports = calculateNumberOfReports(employee);

        ReportingStructure structure = new ReportingStructure(employee, numberOfReports);
        return structure;
    }
    
    /*
     * Calculates number of reports recursively.
     * Accepts an employee and initializes number of reports to 0.
     * if employee has direct reports, we add the number of direct reports to the
     * total number of reports. Then we loop through the reports and if they exist,
     * we recursively call calculateNumberOfReports on each report.
     * return the total number of reports.
     */
    private int calculateNumberOfReports(Employee employee){
        int numberOfReports = 0;

        if(employee.getDirectReports() != null){
            List<Employee> directReports = employee.getDirectReports();
            numberOfReports += directReports.size();
            for(int i=0; i<directReports.size(); i++){
                Employee directReport = employeeRepository.findByEmployeeId(directReports.get(i).getEmployeeId());
                if (directReport != null){
                    numberOfReports += calculateNumberOfReports(directReport);
                }
            }
        }
        return numberOfReports;
    }
    
}
