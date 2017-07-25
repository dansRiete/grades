package model;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Created by nromanen on 7/24/2017.
 */
public class Grade {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    private LocalDate date;
    private Subject subject;
    private int mark;

    public Grade(Long id, Timestamp date, Long subject, int mark) {
        this.id = id;
//        this.date = LocalDate.ofEpochDay(date.getTime());
        this.subject = null;
        this.mark = mark;
    }

    public Grade(Subject subject, LocalDate date, int mark) {
        this.subject = subject;
        this.date = date;
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Subject: " + subject + ", Date: " + date + ", Grade: " + mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMark() {
        return mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grade grade1 = (Grade) o;

        if (mark != grade1.mark) return false;
        if (date != null ? !date.equals(grade1.date) : grade1.date != null) return false;
        return subject != null ? subject.equals(grade1.subject) : grade1.subject == null;
    }
}
