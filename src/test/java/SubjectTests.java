import exceptions.IllegalTitleException;
import model.Subject;
import org.junit.Test;

/**
 * Created by Aleks on 25.07.2017.
 */
public class SubjectTests {

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
