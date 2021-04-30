import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Router {
    private final ArrayList<Device> devices= new ArrayList<>();

    private final Semaphore semaphore;
    private final int[] connection;

    public Router(int size){
        semaphore =new Semaphore(size);
        connection=new int[size];
        for (int i = 0; i < size; i++) {
            connection[i]=-1;
        }
    }

    public void connect(Device x) throws IOException {
        semaphore.available(x);

        synchronized (devices){
            devices.add(x);
        }
        synchronized (connection){
            addConnection(x);
        }
        Network.dequeue(x);
    }
    void disconnect(Device x){

        synchronized (devices){
            devices.remove(x);
        }
        synchronized (connection){
            freeConnection(x);
        }
        semaphore.release();
    }
    public void addConnection(Device x){
        for (int i = 0; i < connection.length; i++) {
            if (connection[i]==-1){
                connection[i]= x.getIds();
                x.setPort(i+1);
                break;
            }
        }
    }
    public void freeConnection(Device x){
        for (int i = 0; i < connection.length; i++) {
            if (connection[i]== x.getIds()){
                connection[i]=-1;
                break;
            }
        }
    }

    public void doOnlineActivity(Device x) throws IOException {
        FileWriter fileWriter =new FileWriter("output.txt",true );
        fileWriter.write("Connection "+x.getPort()+": ("+x.getDeviceName()+") performs online activity \n");
        Random random = new Random();
        int timeOut= random.nextInt(10000-1000+1)+1000;
        try {
            Thread.sleep(timeOut);
        } catch (InterruptedException e) {
        }
        fileWriter.close();
    }
    public void logout(Device x) throws IOException {
        FileWriter fileWriter =new FileWriter("output.txt",true );
        fileWriter.write("Connection "+x.getPort()+": ("+x.getDeviceName()+")("+x.getType()+")logout \n");
        disconnect(x);
        fileWriter.close();
    }
}
