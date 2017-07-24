package model;

import exceptions.IllegalTitleException;

/**
 * Created by nromanen on 7/24/2017.
 */
public class Subject {

    private final String title;

    private Subject(String title) {
        this.title = title;
    }

    public static Subject compose(String title) throws IllegalTitleException {
        if(title == null){
            throw new IllegalTitleException("Title of subject can not be null");
        }else if(title.contains(" ") || title.isEmpty()){
            throw new IllegalTitleException("Title of subject can not contain spaces or be empty");
        }
        return new Subject(title);
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Subject)){
            return false;
        }
        if(obj == this){
            return true;
        }
        return ((Subject) obj).getTitle().equals(this.getTitle());
    }
}
