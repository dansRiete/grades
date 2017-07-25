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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nromanen on 7/24/2017.
 */
public class GradesService {

    private List<Grade> grades = new ArrayList<>();
    private static final Type REVIEW_TYPE = new TypeToken<List<Grade>>() {}.getType();

    public List<Grade> readFromFile(String fileName) throws IOException {
        Gson gson = new Gson();
        try (
                FileReader fileReader = new FileReader(fileName);
                JsonReader jsonReader = new JsonReader(fileReader)
        ){
            return gson.fromJson(jsonReader, REVIEW_TYPE);
        } catch (IOException e) {
            throw e;
        }
    }

    public void writeToFile(String fileName, List<Grade> writtenGrades) throws IOException {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(writtenGrades, writer);
        }catch (IOException e){
            throw e;
        }
    }

    public void addGrade(Subject subject, LocalDate date, int grade) throws AddingGradeException {
        if(date.isBefore(LocalDate.now().withDayOfMonth(1).withDayOfYear(1))){
            throw new AddingGradeException("Grade date can not be before beginning of the year");
        }
        if(date.isAfter(LocalDate.now())){
            throw new AddingGradeException("Grade date can not be after today");
        }
        if(isAlreadyGraded(subject, date)){
            throw new AddingGradeException("There can not be two grades on the same subject on the same day");
        }
        grades.add(new Grade(subject, date, grade));
    }

    public List<Grade> getGrades(){
        return new ArrayList<>(grades);
    }

    public List<Grade> getGrades(LocalDate date){
        List<Grade> gradesOnDate = retrieveGradesByDate(date);
        gradesOnDate.sort(Comparator.comparing(o -> o.getSubject().getTitle()));
        return gradesOnDate;
    }

    public List<Grade> getGrades(Subject subject, boolean ascendingDate){
        List<Grade> gradesOnDate = retrieveGradesBySubject(subject);
        sortByDate(gradesOnDate, ascendingDate);
        return gradesOnDate;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public double calculateAvgGrade(Subject subject){
        final double[] averageGrade = {0};
        List<Grade> subjectGrades = retrieveGradesBySubject(subject);
        subjectGrades.forEach(grade -> averageGrade[0] += grade.getMark());
        return subjectGrades.isEmpty() ? 0 : averageGrade[0] / subjectGrades.size();
    }

    private boolean isAlreadyGraded(Subject subject, LocalDate date){
        return !retrieveGradesBySubject(subject).isEmpty();
    }

    private List<Grade> retrieveGradesBySubject(Subject subject){
        return this.grades.stream()
                .filter(currentGrade -> currentGrade.getSubject().equals(subject))
                .collect(Collectors.toList());
    }

    private List<Grade> retrieveGradesByDate(LocalDate date){
        return this.grades.stream()
                .filter(currentGrade -> currentGrade.getDate().equals(date))
                .collect(Collectors.toList());
    }

    private void sortByDate(List<Grade> gradesOnDate, boolean ascendingDate){
        if(ascendingDate){
            gradesOnDate.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        }else {
            gradesOnDate.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        }
    }

}
