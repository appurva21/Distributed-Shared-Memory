
import java.util.ArrayList;


public class Service {
    static DSM dsm;
    public static void main(String args[]){
        ArrayList<Page> allPages = new ArrayList<>();
        allPages.add(new Page(1,"n1","abc"));
        allPages.add(new Page(2,"n1","def"));
        allPages.add(new Page(3,"n2","ghi"));
        allPages.add(new Page(4,"n2","jkl"));
        allPages.add(new Page(5,"n2","mno"));
        dsm = new DSM(allPages);
        ArrayList<Page> a1=new ArrayList<>();
        a1.add(new Page(1,"n1","def"));
        a1.add(new Page(3,"n2","ghi"));
        a1.add(new Page(4,"n2","jkl"));
        ArrayList<Page> a2=new ArrayList<>();
        a2.add(new Page(1,"n1","abc"));
        a2.add(new Page(5,"n2","mno"));
        ArrayList<Integer> a3=new ArrayList();
        a3.add(1);
        a3.add(2);
        a3.add(3);
        a3.add(4);
        a3.add(5);
        ArrayList<Integer> a4=new ArrayList();
        a4.add(1);
        a4.add(2);
        a4.add(3);
        a4.add(4);
        a4.add(5);
        
        new Thread( () -> {
            new Node("n1",a1,a3).memoryManager();
            
        } ).start();
        new Thread( () -> {
            new Node("n2",a2,a4).memoryManager();
            
        } ).start();
        
    }
    public static Page servicePageFault(String name, int page){       
        return dsm.findPage(name, page);
    }
    
    public static void serviceUpdate(String name, Page page){       
        dsm.updatePageHolders(name, page);
    }

    static void serviceIntialize(String name,ArrayList<Page> pageTable) {
        dsm.pageHolderesInitialize(name,pageTable);
    }
}
