package web.model;

public class Email {

    private int eid; //the id of the system
    private int sid; //for which system is that Email going to be.
    private String email;

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Email(int eid, int sid, String email) {
        this.eid = eid;
        this.sid = sid;
        this.email = email;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

}
