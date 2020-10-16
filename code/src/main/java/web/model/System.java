package web.model;

public class System {
    int sid;
    boolean armed;
    boolean alarm;
    String name;
    String password;
    int pin;

    public System(){

    }

    public System(int sid, boolean armed, boolean alarm, String name, String password, int pin){
        this.sid = sid;
        this.armed =armed;
        this.alarm = alarm;
        this.name = name;
        this.password = password;
        this.pin = pin;
    }
}
