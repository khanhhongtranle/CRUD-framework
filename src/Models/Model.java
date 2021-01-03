package Models;

public class Model implements Cloneable {

    private String ID;
    private String Name;

    public Model(){

    }

    public Model(String _id, String _name){
        this.ID = _id;
        this.Name = _name;
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    @Override
    public Model clone(){
        try {
            return (Model) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
