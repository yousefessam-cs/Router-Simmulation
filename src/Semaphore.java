import java.io.FileWriter;
import java.io.IOException;

public class Semaphore {
    private int size;
    public Semaphore(int size){this.size=size;}
    public synchronized void available(Device x) throws IOException {
        size--;
        FileWriter fileWriter =new FileWriter("output.txt",true );
        if (size<0){
            try {
                fileWriter.write("("+x.getDeviceName()+")("+x.getType()+")arrived and waiting \n");
                wait();
            } catch (InterruptedException  e) {

            }
        }
        fileWriter.write("("+x.getDeviceName()+")arrived and Occupied \n");
        fileWriter.close();
    }
    public synchronized void release(){
        size++;
        if (size<=0){
            notify();
        }
    }
}
