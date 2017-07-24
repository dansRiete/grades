import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import exceptions.AddingGradeException;
import model.Grade;
import model.Subject;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nromanen on 7/24/2017.
 */
public class GradesService {

    private List<Grade> grades = new ArrayList<>();
    private static final Type REVIEW_TYPE = new TypeToken<List<Grade>>() {}.getType();

    public List<Grade> readFromFile(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(fileName));
            return gson.fromJson(jsonReader, REVIEW_TYPE);
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    public void writeToFile(String fileName) throws IOException {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(grades, writer);
        }catch (IOException e){
            throw e;
        }
    }

    public void addGrade(Subject subject, LocalDate date, int grade) throws AddingGradeException {
        if(date.isBefore(LocalDate.now().withDayOfMonth(1).withDayOfYear(1))){
            throw new AddingGradeException("Date can not be before beginning of the year");
        }
        if(date.isAfter(LocalDate.now())){
            throw new AddingGradeException("Date can not be after today");
        }
        if(isGraded(subject, date)){
            throw new AddingGradeException("There can not be two grades on same subject in the same day");
        }
        grades.add(new Grade(subject, date, grade));
    }

    public List<Grade> getGrades(LocalDate date){
        List<Grade> gradesOnDate = new ArrayList<>();
        for(Grade currentGrade : grades){
            if(currentGrade.getDate().equals(date)){
                gradesOnDate.add(currentGrade);
            }
        }
        Collections.sort(gradesOnDate, (o1, o2) -> o1.getSubject().getTitle().compareTo(o2.getSubject().getTitle()));
        return gradesOnDate;
    }

    public List<Grade> getGrades(Subject subject, boolean ascendingDate){
        List<Grade> gradesOnDate = grades.stream().filter(currentGrade -> currentGrade.getSubject().equals(subject)).collect(Collectors.toList());
        if(ascendingDate){ //// TODO: 7/24/2017 sort in separate method
            Collections.sort(gradesOnDate, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        }else {
            Collections.sort(gradesOnDate, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        }
        return gradesOnDate;
    }

    public double calculateAvgGrade(Subject subject){
        double averageGrade = 0;
        int counter = 0;

        for(Grade grade : getGrades(subject, true)){
            averageGrade += grade.getGrade();
            counter++;
        }
        if(counter != 0){
            averageGrade = averageGrade / counter;
        }
        return averageGrade;
    }

    private boolean isGraded(Subject subject, LocalDate date){
        for(Grade currentGrade : getGrades(date)){
            if(currentGrade.getSubject().equals(subject)){
                return true;
            }
        }
        return false;
    }

    public List<Grade> getGrades(){
        return new ArrayList<>(grades);
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

}
