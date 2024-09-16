package com.aalto.paycraft.mapper;

import com.aalto.paycraft.dto.EmployeeProfileDTO;
import com.aalto.paycraft.entity.EmployeeProfile;

public class EmployeeProfileMapper {
    public static EmployeeProfile mapToEmployeeProfile(EmployeeProfile employeeProfile, EmployeeProfileDTO employeeProfileDTO){
        employeeProfile.setFirstName(employeeProfileDTO.getFirstName());
        employeeProfile.setLastName(employeeProfileDTO.getLastName());
        employeeProfile.setDateOfBirth(employeeProfileDTO.getDateOfBirth());
        employeeProfile.setEmailAddress(employeeProfileDTO.getEmailAddress());
        employeeProfile.setPersonalAddress(employeeProfileDTO.getPersonalAddress());
        employeeProfile.setPhoneNumber(employeeProfileDTO.getPhoneNumber());
        employeeProfile.setStartDate(employeeProfileDTO.getStartDate());
        employeeProfile.setJobTitle(employeeProfileDTO.getJobTitle());
        employeeProfile.setDepartment(employeeProfileDTO.getDepartment());
        employeeProfile.setSalaryType(employeeProfileDTO.getSalaryType());
        employeeProfile.setBankAccountNumber(employeeProfileDTO.getBankAccountNumber());
        return employeeProfile;
    }

    public static EmployeeProfileDTO mapToEmployeeProfileDTO(EmployeeProfile employeeProfile, EmployeeProfileDTO employeeProfileDTO) {
        employeeProfileDTO.setFirstName(employeeProfile.getFirstName());
        employeeProfileDTO.setLastName(employeeProfile.getLastName());
        employeeProfileDTO.setDateOfBirth(employeeProfile.getDateOfBirth());
        employeeProfileDTO.setEmailAddress(employeeProfile.getEmailAddress());
        employeeProfileDTO.setPersonalAddress(employeeProfile.getPersonalAddress());
        employeeProfileDTO.setPhoneNumber(employeeProfile.getPhoneNumber());
        employeeProfileDTO.setStartDate(employeeProfile.getStartDate());
        employeeProfileDTO.setJobTitle(employeeProfile.getJobTitle());
        employeeProfileDTO.setDepartment(employeeProfile.getDepartment());
        employeeProfileDTO.setSalaryType(employeeProfile.getSalaryType());
        employeeProfileDTO.setBankAccountNumber(employeeProfile.getBankAccountNumber());
        return employeeProfileDTO;
    }

}
