package com.example.myfood.data.models;

import java.util.LinkedList;
import java.util.TreeMap;

public class MenuForResponse {

    private LinkedList<TreeMap<String, String[]>> teacherList;
    private LinkedList<String[]> studentList;
    private int code;

    public MenuForResponse(LinkedList<TreeMap<String, String[]>> teacherList, LinkedList<String[]> studentList, int code) {
        this.teacherList = teacherList;
        this.studentList = studentList;
        this.code = code;
    }

    public LinkedList<TreeMap<String, String[]>> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(LinkedList<TreeMap<String, String[]>> teacherList) {
        this.teacherList = teacherList;
    }

    public LinkedList<String[]> getStudentList() {
        return studentList;
    }

    public void setStudentList(LinkedList<String[]> studentList) {
        this.studentList = studentList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

