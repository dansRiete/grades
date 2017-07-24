import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import exceptions.AddingGradeException;
import exceptions.IllegalTitleException;
import model.Grade;
import model.Subject;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Aleks on 24.07.2017.
 */


public class GradesServiceTests {

    private GradesService gradesService = new GradesService();



    @Test
    public void populateFromJson() throws IOException {

        Subject MATH = null;
        Subject HISTORY = null;
        Subject GEOGRAPHIC = null;
        Subject WITHSPACES = null;
        try {
            MATH = Subject.compose("Math");
            HISTORY = Subject.compose("History");
            GEOGRAPHIC = Subject.compose("Geographic");
//            WITHSPACES = Subject.compose(" ");    //Exception
//            WITHSPACES = Subject.compose("Geog raphic");    //Exception
        } catch (IllegalTitleException e) {
            e.printStackTrace();
        }

        gradesService.setGrades(gradesService.readFromFile("src\\test\\resources\\grades.json"));
        assertTrue(gradesService.getGrades().size() == 9);
        assertEquals(gradesService.getGrades().toString(), "[Subject: Math, Date: 2016-05-24, Grade: 5, Subject: History, Date: 2017-03-12, Grade: 3, Subject: Geographic, Date: 2017-04-22, Grade: 9, Subject: Geographic, Date: 2017-03-22, Grade: 7, Subject: Math, Date: 2017-05-15, Grade: 3, Subject: History, Date: 2017-05-15, Grade: 4, Subject: Geographic, Date: 2017-01-15, Grade: 3, Subject: Geographic, Date: 2017-05-15, Grade: 2, Subject: History, Date: 2017-02-20, Grade: 9]");
        assertEquals(gradesService.getGrades(LocalDate.of(2017, 5, 15)).toString(), "[Subject: Geographic, Date: 2017-05-15, Grade: 2, Subject: History, Date: 2017-05-15, Grade: 4, Subject: Math, Date: 2017-05-15, Grade: 3]");
        assertEquals(gradesService.getGrades(GEOGRAPHIC, true).toString(), "[Subject: Geographic, Date: 2017-01-15, Grade: 3, Subject: Geographic, Date: 2017-03-22, Grade: 7, Subject: Geographic, Date: 2017-04-22, Grade: 9, Subject: Geographic, Date: 2017-05-15, Grade: 2]");
        assertEquals(gradesService.getGrades(GEOGRAPHIC, false).toString(), "[Subject: Geographic, Date: 2017-05-15, Grade: 2, Subject: Geographic, Date: 2017-04-22, Grade: 9, Subject: Geographic, Date: 2017-03-22, Grade: 7, Subject: Geographic, Date: 2017-01-15, Grade: 3]");
        assertEquals(gradesService.calculateAvgGrade(HISTORY),5.333333333333333, 0.0000000000001);
    }

    @Test
    public void writeRead() throws IllegalTitleException, IOException {
        List<Grade> initGrades = new ArrayList<>();
        LocalDate date = LocalDate.now();
        initGrades.add(new Grade(Subject.compose("Subject1"), date, 5));
        initGrades.add(new Grade(Subject.compose("Subject2"), date, 5));
        initGrades.add(new Grade(Subject.compose("Subject3"), date, 5));
        gradesService.writeToFile("src\\test\\resources\\tmp.json", initGrades);
        List<Grade> gotGrades = gradesService.readFromFile("src\\test\\resources\\tmp.json");
        assertEquals(initGrades.get(0), gotGrades.get(0));
        assertEquals(initGrades.get(1), gotGrades.get(1));
        assertEquals(initGrades.get(2), gotGrades.get(2));
    }

    @Test(expected = AddingGradeException.class)
    public void addGradeInPastYearThrowsException() throws IllegalTitleException, AddingGradeException {
        gradesService.addGrade(Subject.compose("subject"), LocalDate.of(2016, 1, 1), 3);
    }

    @Test(expected = AddingGradeException.class)
    public void addGradeInTomorowThrowsException() throws IllegalTitleException, AddingGradeException {
        gradesService.addGrade(Subject.compose("subject"), LocalDate.now().plusDays(1), 3);

    }

    @Test(expected = AddingGradeException.class)
    public void addGradeInAlreadyExistException() throws IllegalTitleException, AddingGradeException {
        gradesService.addGrade(Subject.compose("subject"), LocalDate.now().withDayOfMonth(5), 3);
        gradesService.addGrade(Subject.compose("subject"), LocalDate.now().withDayOfMonth(5), 3);

    }

    @Test(expected = IllegalTitleException.class)
    public void composeSubjectWithEmptyTitleThrowsException() throws IllegalTitleException {
        Subject.compose("");
    }

    @Test(expected = IllegalTitleException.class)
    public void composeSubjectWithSpacesThrowsException() throws IllegalTitleException {
        Subject.compose("Some subject");
    }

    @Test(expected = IllegalTitleException.class)
    public void composeSubjectWithNullTitleThrowsException() throws IllegalTitleException {
        Subject.compose(null);
    }
}
