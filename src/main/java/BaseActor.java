import java.util.ArrayList;
import java.util.Objects;

public class BaseActor {
    private String name;
    private String dob;

    public BaseActor(){

    }
    public BaseActor(String name, String dob){
        this.name = name;
        this.dob = dob;
    }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "BaseActor{" +
                "name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object right) {

        if (this == right) return true;

        if (!(right instanceof BaseActor)) return false;

        BaseActor person = (BaseActor) right;

        if (dob != null && person.dob != null)
            return person.name.equals(name) && dob.equals(person.dob);
        else if (dob == null && person == null)
            return person.name.equals(name);

        return false;
    }


}
