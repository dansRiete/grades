package model;

import java.time.LocalDate;

/**
 * Created by nromanen on 7/24/2017.
 */
public class Grade {

    private final LocalDate date;
    private final Subject subject;
    private final int grade;

    public Grade(Subject subject, LocalDate date, int grade) {
        this.subject = subject;
        this.date = date;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Subject: " + subject + ", Date: " + date + ", Grade: " + grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grade grade1 = (Grade) o;

        if (grade != grade1.grade) return false;
        if (date != null ? !date.equals(grade1.date) : grade1.date != null) return false;
        return subject != null ? subject.equals(grade1.subject) : grade1.subject == null;
    }
}
