import java.util.ArrayList;

public class BaseCast {
    private String Director;
    private String title;
    private String Actor;

    public BaseCast(){

    }
    public BaseCast(String Director, String title, String Actor){
        this.Director = Director;
        this.title = title;
        this.Actor = Actor;
    }
    public String getDirector() { return Director; }

    public void setDirector(String Director) { this.Director = Director; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return Actor;
    }

    public void setActor(String Actor) {
        this.Actor = Actor;
    }

    @Override
    public String toString() {
        return "BaseActor{" +
                "name='" + Director + '\'' +
                ", dob='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object right) {

        if (this == right) return true;

        if (!(right instanceof BaseCast)) return false;

        BaseCast person = (BaseCast) right;

        return person.Director.equals(Director) &&
                person.title.equals(title) &&
                person.Actor.equals(Actor);
    }

}
