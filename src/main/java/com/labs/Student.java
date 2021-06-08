package com.labs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
    private String name;
    private String surname;
    private int age;
    private int student_id;
    private String mark;

    public Student() {
    }
    public Student(String name, String surname, int age, int student_id, String mark) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.student_id = student_id;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getAge() {
        return age;
    }
    public int getStudent_id() {
        return student_id;
    }
    public String getMark() {
        return mark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", surname=" + surname + ", age=" + age + ", student_id=" + student_id + ", mark=" + mark + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Student guest = (Student) obj;
        return student_id == guest.student_id && age == guest.age
                && (name == guest.name || (name != null &&name.equals(guest.getName())))
                && (surname == guest.surname || (surname != null && surname .equals(guest.getSurname())))
                && (mark == guest.mark || (mark != null && mark .equals(guest.getMark())));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + student_id;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((mark == null) ? 0 : mark.hashCode());

        return result;
    }
}