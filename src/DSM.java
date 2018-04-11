
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DSM {
 
    HashMap<Integer,String> pageOwner = new HashMap<>();
    HashMap<Integer,List<String>> pageHolders = new HashMap<>();
    MessageWindow window;
    
    DSM(){
        window = new MessageWindow("DSM");
        window.setVisible(true);
    }
    
    public String findPage(String newUser, int page) throws InterruptedException{
        
        
        String owner = pageOwner.get(page);
        
        synchronized(window){
            window.set("DSM : finding page "+page+" for "+newUser+" from "+owner);
        }
        
        //Thread.sleep(1000);
        System.out.println("DSM : finding page "+page+" for "+newUser+" from "+owner);
        String s = Service.getPage(owner,page);
        List<String> list = pageHolders.get(page);
        if(list==null){
           list = new ArrayList<>();
        }
        list.add(newUser);
        pageHolders.put(page, list);
        //Thread.sleep(3000);
        synchronized(window){
            window.set("DSM : found page " +page+ " for "+newUser);
        }
        
        System.out.println("DSM : found page " +page+ " for "+newUser);
        return s;
    }
    
    public void updatePageHolders(String user, int rmPage){
        List<String> list = pageHolders.get(rmPage);
        list.remove(user);
        pageHolders.put(rmPage, list);
        String newOwner = pageHolders.get(rmPage).get(0);
        pageOwner.put(rmPage, newOwner);
    }

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
