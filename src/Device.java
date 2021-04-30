import java.io.IOException;
import java.util.Random;

public class Device extends Thread {
    private String name;
    private String type;
    private final Router router;
    private static int ids=0;
    private int id;

    public int getPort() {
        return port;
    }

    private int port;

    public Device(String name,String type,Router router){
        this.name=name;
        this.type=type;
        this.router=router;
        this.id=ids++;
    }

    public String getDeviceName() {
        return name;
    }

    public String getType() {
        return type;
    }


    public String toString(){
        return  "("+getDeviceName()+")("+getType()+")arrived";
    }



    @Override
    public void run(){
        try {
            router.connect(this);
            router.doOnlineActivity(this);
            router.logout(this);
        } catch (IOException e) {

        }

    }


    public int getIds() {
        return id;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
