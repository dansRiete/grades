package model;

import java.time.LocalDate;

/**
 * Created by nromanen on 7/24/2017.
 */
public class Grade {

    private LocalDate date;
    private Subject subject;
    private int grade;

    public Grade(Subject subject, LocalDate date, int grade) {
        this.subject = subject;
        this.date = date;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "model.Subject: " + subject + ", Date: " + date + ", model.Grade: " + grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
