import java.util.*;
import java.io.*;

public class Node {
    
    private TreeMap<Integer,String> pageTable;
    private ArrayList<Integer> requiredPages;
    private final int SIZE = 3;
    MessageWindow window;
    public Node(String name, TreeMap page, ArrayList required) throws InterruptedException{
        window = new MessageWindow(name);
        window.setVisible(true);
        synchronized(window){
            window.set("Node "+name+" Created");
        }
        System.out.println("Node "+name+" Created");
        this.name = name;
        this.pageTable = page;
        this.requiredPages = required;
        
        initalize(name, pageTable);
        Thread.sleep(4000);
    }

    
    
    public void initalize(String name,TreeMap<Integer,String> pageTable){
        Service.serviceIntialize(name,pageTable);
    }
    
    
    public void memoryManager() throws InterruptedException{
        while(requiredPages.size()!=0){
            Integer rPage = requiredPages.remove(0);
            boolean flag = pageTable.containsKey(rPage);
            if(flag){
                Thread.sleep(4000);
                synchronized(window){
                    
                    window.set(name +": Page Found-> Page Number: "+ rPage +" Content: " + pageTable.get(rPage));
                }
                Thread.sleep(4000);
                System.out.println(name +": Page Found-> Page Number: "+ rPage +" Content: " + pageTable.get(rPage));
            }
            else{
                //pagefault
                Thread.sleep(4000);
                synchronized(window){
            
                    window.set(name +": Page Not Found Page Fualt -> Page Number: "+ rPage);
                }
                System.out.println(name +": Page Not Found-> Page Number: "+ rPage);
                Thread.sleep(4000);
                synchronized(window){
                    
                    window.set("Requesting page to DSM");
                }
                
                String newPage = Service.servicePageFault(name, rPage);
                Thread.sleep(4000);
                synchronized(window){
                    
                    window.set("Got the required page from DSM");
                }
                //LIFO
                Thread.sleep(4000);
                if(pageTable.size()==3){
                    
                    int temp = pageTable.firstKey();
                    pageTable.remove(temp);
                    synchronized(window){
                    
                        window.set("Size Full Removing Page "+temp);
                    }
                    Thread.sleep(4000);
                     
                }
                Service.serviceUpdate(name,rPage);
                pageTable.put(rPage,newPage);  
            }
            
        }
        
        //Thread.sleep(10000);
        
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getRequiredPages() {
        return requiredPages;
    }

    public void setRequiredPages(ArrayList<Integer> requiredPages) {
        this.requiredPages = requiredPages;
    }

    String getPage(int page) throws InterruptedException {
        Thread.sleep(4000);
        synchronized(window){

            window.set("DSM requested Page "+page);
        }
        Thread.sleep(4000);
        synchronized(window){

            window.set("Giving DSM the required page");
        }
        Thread.sleep(4000);
                
        return pageTable.get(page);
        
    }
}
