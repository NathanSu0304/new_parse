import java.util.ArrayList;
import java.util.List;

public class BaseStarMovie {
    private String Mvid;
    private String Starid;

    public BaseStarMovie(){

    }

    public BaseStarMovie(String Mvid, String Starid){
        this.Mvid = Mvid;
        this.Starid = Starid;
    }

    public String getMVid() {
        return Mvid;
    }

    public void setMVid(String Mvid) {
        this.Mvid = Mvid;
    }

    public String getStarid() {
        return Starid;
    }

    public void setStarid(String Starid) {
        this.Starid = Starid;
    }

    @Override
    public String toString() {
        return "BaseMv{" +
                "Mvid='" + Mvid + '\'' +
                ", Starid='" + Starid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object right) {

        if (this == right) return true;

        if (!(right instanceof BaseStarMovie)) return false;

        BaseStarMovie person = (BaseStarMovie) right;

        return person.Starid.equals(Starid) &&
                person.Mvid.equals(Mvid);
    }
}
