package web.model;

public class Sensor {
    private int sid;
    private boolean armed;
    private boolean open;
    private String name;

    public Sensor(){

    }

    public Sensor(int sid, boolean armed, boolean open, String name) {
        this.sid = sid;
        this.armed = armed;
        this.open = open;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isArmed() {
        return armed;
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
