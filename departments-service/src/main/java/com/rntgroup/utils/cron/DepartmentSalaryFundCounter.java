package com.rntgroup.utils.cron;

import com.rntgroup.db.entity.DepartmentSalary;
import com.rntgroup.service.DepartmentFundService;
import com.rntgroup.service.DepartmentService;
import com.rntgroup.mappers.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DepartmentSalaryFundCounter {
    private final DepartmentService departmentService;
    private final DepartmentFundService departmentFundService;
    private final DepartmentMapper departmentMapper;

    @Scheduled(cron = "0 */5 * * * ?")
    public void getFundStat() {
        List<String> allDepartments = departmentService.getAllDepartmentsNames();
        List<String> allDepartmentsSalaryFund = departmentFundService.getAllDepartmentsSalaryNames();
        allDepartments.removeAll(allDepartmentsSalaryFund);
        if (!allDepartments.isEmpty()) {
            for (String allDepartment : allDepartments) {
                DepartmentSalary departmentSalary = DepartmentSalary
                        .builder()
                        .name(allDepartment)
                        .build();
                departmentFundService.saveDepartmentFund(departmentSalary);
            }
        }
        List<DepartmentSalary> allDepartmentsSalary = departmentFundService.findAllDepartmentFund();
        for (DepartmentSalary salary : allDepartmentsSalary) {
            DepartmentSalary departmentSalary = departmentFundService.findByName(salary.getName());
            Double departmentSalaryFund = departmentService.getAllEmployeesSalaryAtDepartmentByName(salary.getName());
            if (!Objects.equals(departmentSalary.getDepartmentSummary(), departmentSalaryFund)) {
                departmentSalary.setDepartmentSummary(departmentSalaryFund);
                departmentFundService.saveDepartmentFund(departmentSalary);
            }
        }
    }
}
