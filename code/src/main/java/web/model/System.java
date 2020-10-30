package web.model;

public class System {
    int sid;
    boolean armed;
    boolean alarm;
    String name;
    String password;
    String pin;

    public System(){

    }

    public System(int sid, boolean armed, boolean alarm, String name, String password, String pin){
        this.sid = sid;
        this.armed =armed;
        this.alarm = alarm;
        this.name = name;
        this.password = password;
        this.pin = pin;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public boolean isArmed(){
        return this.armed;
    }

    public boolean isAlarm(){
        return this.alarm;
    }

    public int getSid(){
        return this.sid;
    }

    public String getPin(){
        return this.pin;
    }
}
