package com.example.appraisalform;

public class Users {

    String EmpCode, Name, Designation, College, Campus, Department, JoiningDate, AssessmentFrom, AssessmentTo;

    // Constructor for all fields
    public Users(String empCode, String name, String designation, String college, String campus, String department, String joiningDate, String assessmentFrom, String assessmentTo) {
        EmpCode = empCode;
        Name = name;
        Designation = designation;
        College = college;
        Campus = campus;
        Department = department;
        JoiningDate = joiningDate;
        AssessmentFrom = assessmentFrom;
        AssessmentTo = assessmentTo;
    }

    // Default constructor required for Firebase
    public Users() {
    }

    // Getters and setters (unchanged)
    public String getEmpCode() {
        return EmpCode;
    }

    public void setEmpCode(String empCode) {
        EmpCode = empCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getJoiningDate() {
        return JoiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        JoiningDate = joiningDate;
    }

    public String getAssessmentFrom() {
        return AssessmentFrom;
    }

    public void setAssessmentFrom(String assessmentFrom) {
        AssessmentFrom = assessmentFrom;
    }

    public String getAssessmentTo() {
        return AssessmentTo;
    }

    public void setAssessmentTo(String assessmentTo) {
        AssessmentTo = assessmentTo;
    }
}
