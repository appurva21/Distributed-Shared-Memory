
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DSM {
 
    ArrayList<Page> allPages;
    HashMap<Page,String> pageOwner = new HashMap<>();
    HashMap<Page,List<String>> pageHolders = new HashMap<>();
    
    public DSM(ArrayList<Page> all){
        this.allPages = all;

    }
    public Page findPage(String newUser, int page){
        Page rePage = null;
        for (int i = 0; i < allPages.size(); i++) {
            if(page==(allPages.get(i).getNumber())){
                rePage=allPages.get(i);
                break;
            }
        }
        List<String> list = pageHolders.get(rePage);
        if(list==null){
           list = new ArrayList<>();
        }
        list.add(newUser);
        pageHolders.put(rePage, list);
        return rePage;
    }
    
    public void updatePageHolders(String user, Page rmPage){
        List<String> list = pageHolders.get(rmPage);
        list.remove(user);
        pageHolders.put(rmPage, list);
    }

    public void pageHolderesInitialize(String name,ArrayList<Page> pageTable) {
        for(int i=0;i<pageTable.size();i++){
            Page temp = pageTable.get(i);
            List<String> tempList = pageHolders.get(temp);
            if(tempList==null){
                tempList = new ArrayList<>();
            }
            tempList.add(name);
            pageHolders.put(temp, tempList);
        }
    }
}
