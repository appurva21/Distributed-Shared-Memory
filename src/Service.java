
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Service {
    static DSM dsm;
    static HashMap<String,Node> hashmap;
    public static void main(String args[]) throws InterruptedException{
        
        
        dsm = new DSM();
        hashmap = new HashMap<>();
        TreeMap<Integer,String> treemap1 = new TreeMap<>();
        
        TreeMap<Integer,String> treemap2 = new TreeMap<>();
        
        ArrayList<Integer> arr1 = new ArrayList<>();
        
        ArrayList<Integer> arr2 = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            arr1.add(i+1);
            arr2.add(i+1);
        }
        
        treemap1.put(1, "page 1 content");
        treemap1.put(3, "page 3 content");
        treemap1.put(5, "page 5 content");
        
        treemap2.put(2, "page 2 content");
        treemap2.put(4, "page 4 content");
        
       
        Node n1 = new Node("Node1",treemap1,arr1);
        Node n2 = new Node("Node2",treemap2,arr2);
        
        hashmap.put(n1.getName(), n1);
        hashmap.put(n2.getName(), n2);
        
        
        Thread t1 = new Thread( () -> {
            try {
                n1.memoryManager();
            } catch (InterruptedException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("shutting down "+n1.getName());
        } );
        
        
        Thread t2 = new Thread( () -> {
            try {
                n2.memoryManager();
            } catch (InterruptedException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("shutting down "+n2.getName());
        } );
        
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        
    }
    public static String servicePageFault(String name, int page) throws InterruptedException{ 
        
        System.out.println("Service : "+name+" requested service for page fault "+page);
        return dsm.findPage(name, page);
    }
    
    public static void serviceUpdate(String name, int page){       
        dsm.updatePageHolders(name, page);
    }

    static void serviceIntialize(String name,TreeMap<Integer,String> pageTable) {
        dsm.pageHolderesInitialize(name,pageTable);
    }

    static String getPage(String owner, int page) throws InterruptedException {
        
        String s = "";
        
        Node n = hashmap.get(owner);
        
        s=n.getPage(page);
        if(s==null)
            System.out.println("Service : cant");
        else
            System.out.println("Service : found page "+s);
        
        return s;
    }
}
