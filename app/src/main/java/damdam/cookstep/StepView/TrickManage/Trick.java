package damdam.cookstep.StepView.TrickManage;

/**
 * Created by Poste on 16/07/2016.
 */
public class Trick {

    private String trick;

    public Trick(String trick) {
        this.trick = trick;
    }

    public String getTrick() {
        return trick;
    }

    public void setTrick(String trick) {
        this.trick = trick;
    }

    @Override
    public String toString() {
        return "Trick{" +
                "trick='" + trick + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trick)) return false;

        Trick trick1 = (Trick) o;

        return trick != null ? trick.equals(trick1.trick) : trick1.trick == null;

    }

    @Override
    public int hashCode() {
        return trick != null ? trick.hashCode() : 0;
    }
}
