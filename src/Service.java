import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//service class acts like an interface between node and DSM layer
public class Service {
    
    static DSM dsm; //dsm layer object
    static HashMap<String,Node> hashmap; //hasmap to map nodes with their respective names
    
    
    public static void main(String args[]) throws InterruptedException{
               
        dsm = new DSM();
        hashmap = new HashMap<>();
        
        TreeMap<Integer,String> treemap1 = new TreeMap<>(); //table of available pages at node 1
        TreeMap<Integer,String> treemap2 = new TreeMap<>(); //table of available pages at node 2
        
        ArrayList<Integer> arr1 = new ArrayList<>();    //list of required pages at node 1   
        ArrayList<Integer> arr2 = new ArrayList<>();    //list of required pages at node 2
        
        //adding required pages to both the list
        for (int i = 0; i < 5; i++) {
            arr1.add(i+1);
            arr2.add(i+1);
        }
        
        //adding available pages at node 1
        treemap1.put(1, "page 1 content");
        treemap1.put(3, "page 3 content");
        treemap1.put(5, "page 5 content");
        
        //adding available pages at node 2
        treemap2.put(2, "page 2 content");
        treemap2.put(4, "page 4 content");
        
       //creating two nodes for test run
        Node n1 = new Node("Node1",treemap1,arr1);
        Node n2 = new Node("Node2",treemap2,arr2);
        
        //mapping each node with its name
        hashmap.put(n1.getName(), n1);
        hashmap.put(n2.getName(), n2);
        
        //created new thread for node1
        Thread t1 = new Thread( () -> {
            try {
                n1.memoryManager();
            } catch (InterruptedException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Shutting Down "+n1.getName());
        } );
        
        //created new thread for node2
        Thread t2 = new Thread( () -> {
            try {
                n2.memoryManager();
            } catch (InterruptedException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Shutting Down "+n2.getName());
        } );
        
        
        t1.start(); //starting thread t1
        t2.start(); // starting thread t2
        //joining threads
        t1.join();
        t2.join();
        
        
    }
    //function to service page fault from DSM layer
    public static String servicePageFault(String name, int page) throws InterruptedException{ 
        
        System.out.println("Service : "+name+" requested service for page fault "+page);
        return dsm.findPage(name, page);
    }
    
    //function call to update page holders table at DSM layer
    public static void serviceUpdate(String name, int page){       
        dsm.updatePageHolders(name, page);
    }

    // Function to intially setup Ownership and Page Holders table at DSM layer
    static void serviceIntialize(String name,TreeMap<Integer,String> pageTable) {
        dsm.pageHolderesInitialize(name,pageTable);
    }

    static String getPage(String owner, int page) throws InterruptedException {
        
        String s = "";
        
        Node n = hashmap.get(owner);
        
        s = n.getPage(page);
        if(s == null)
            System.out.println("Service : cant be provided");
        else
            System.out.println("Service : found page "+s);
        
        return s;
    }
}
