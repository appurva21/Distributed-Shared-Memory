
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//DSM layer
public class DSM {
 
    HashMap<Integer,String> pageOwner = new HashMap<>();    //table mapping page to its owner
    HashMap<Integer,List<String>> pageHolders = new HashMap<>();    //table mapping list page to its list of holders
    MessageWindow window;   //object to display message in JFrame
    
    //constructor
    DSM(){
        window = new MessageWindow("DSM Layer");
        window.setVisible(true);
    }
    
    //function to service page fault
    public String findPage(String newUser, int page) throws InterruptedException{
               
        String owner = pageOwner.get(page); //getting onwer of the required page
        
        synchronized(window){
            window.set("DSM : FINDING page "+page+" for "+newUser+" from "+owner);
        }
        
        //Thread.sleep(1000);
        System.out.println("DSM : finding page "+page+" for "+newUser+" from "+owner);
        String s = Service.getPage(owner,page); //contacting owner node through service class
        List<String> list = pageHolders.get(page);
        if(list==null){
           list = new ArrayList<>();
        }
        list.add(newUser);  
        pageHolders.put(page, list); //updating page holders list
        //Thread.sleep(3000);
        synchronized(window){
            window.set("DSM : FOUND page " +page+ " for "+newUser);
        }      
        System.out.println("DSM : found page " +page+ " for "+newUser);
        return s;
    }
    
    //function to update Page holders table when
    public void updatePageHolders(String user, int rmPage){
        List<String> list = pageHolders.get(rmPage);
        list.remove(user);
        pageHolders.put(rmPage, list);
        String newOwner = pageHolders.get(rmPage).get(0);
        pageOwner.put(rmPage, newOwner);
    }

    //function to initially setup Ownership and Page holders table
    public void pageHolderesInitialize(String name,TreeMap<Integer,String> pageTable) {
        for(Map.Entry<Integer,String> entry : pageTable.entrySet()) {
            int key = entry.getKey();
            List<String> tempList = pageHolders.get(key);
            if(tempList==null){
                tempList = new ArrayList<>();
            }
            tempList.add(name);
            pageHolders.put(key, tempList);
            if(!pageOwner.containsKey(key))
                pageOwner.put(key, name);
        }
    }
}
