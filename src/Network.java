import java.util.ArrayList;
import java.util.Scanner;

public class Network {
    public static ArrayList<Device> devicesQueue = new ArrayList<>();
    public static Router router;
    public static int queueSize;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("What is number of WI-FI Connections?");
        input = scanner.nextLine();
        router = new Router(Integer.parseInt(input));
        System.out.println("What is number of devices Clients want to connect?");
        input = scanner.nextLine();
        queueSize = Integer.parseInt(input);

        for (int i = 0; i < queueSize; i++) {
            input=scanner.nextLine();
            String[] temp =input.split(" ");
            addDevice(temp[0],temp[1]);
        }

        for (int i = 0; i < queueSize; i++) {
            devicesQueue.get(i).start();
        }
    }


    public synchronized static void addDevice(String name, String type) {
        Device temp =new Device(name,type,router);
        devicesQueue.add(temp);
    }
    public synchronized static void dequeue(Device x){
        devicesQueue.remove(x);
    }
}
