import java.util.*;
import java.io.*;

//Node is an individual sytem in DSM architecture
public class Node {
    
    //table of available pages and their content
    private TreeMap<Integer,String> pageTable;
    
    //list of required pages at a particular node
    private ArrayList<Integer> requiredPages;
    
    //No. of pages a node can keep at a time
    private final int SIZE = 3;
    
    //object to display message in JFrame
    MessageWindow window;
    
    //constructor for node
    public Node(String name, TreeMap page, ArrayList required) throws InterruptedException{
        window = new MessageWindow(name);
        window.setVisible(true);
        synchronized(window){
            window.set("Node: "+name+" Created");
        }
        System.out.println("Node "+name+" Created");
        this.name = name;
        this.pageTable = page;
        this.requiredPages = required;
        
        initalize(name, pageTable);
        Thread.sleep(4000);
    }

  
    // Function to intially setup Ownership and Page Holders table at DSM layer
    public void initalize(String name,TreeMap<Integer,String> pageTable){
        Service.serviceIntialize(name,pageTable);
    }
    
    //Memory Manager for each node to manage pagem demand at each node
    public void memoryManager() throws InterruptedException{
        while(requiredPages.size()!=0){
            Integer rPage = requiredPages.remove(0);
            boolean flag = pageTable.containsKey(rPage);
            if(flag){   //If node has page in its page table
                Thread.sleep(4000);
                synchronized(window){
                    
                    window.set(name +": Page FOUND-> Page Number: "+ rPage +" Content: " + pageTable.get(rPage));
                }
                Thread.sleep(4000);
                System.out.println(name +": Page FOUND-> Page Number: "+ rPage +" Content: " + pageTable.get(rPage));
            }
            else{ //if pagefault occurs
                
                Thread.sleep(4000);
                synchronized(window){
            
                    window.set(name +": Page NOT FOUND Page Fualt -> Page Number: "+ rPage);
                }
                System.out.println(name +": Page NOT FOUND-> Page Number: "+ rPage);
                Thread.sleep(4000);
                synchronized(window){
                    
                    window.set("REQUESTING page from DSM");
                }
                
                // Memory Manager requesting new page as pagefault occured from DSM layer through service class
                String newPage = Service.servicePageFault(name, rPage);
                Thread.sleep(4000);
                synchronized(window){
                    
                    window.set("RECEIVED the required page from DSM");
                }
                //LIFO
                Thread.sleep(4000);
                if(pageTable.size()==3){//if size is full
                    
                    int temp = pageTable.firstKey();
                    pageTable.remove(temp); //replacing page according to LIFO
                    synchronized(window){
                    
                        window.set("Size FULL--> Removing Page "+temp);
                    }
                    Thread.sleep(4000);
                     
                }
                //function call to update page holders table at DSM layer
                Service.serviceUpdate(name,rPage);
                pageTable.put(rPage,newPage);  
            }
            
        }
        
        //Thread.sleep(10000);
        
    }
    //function to return content of page for which this node is owner
    String getPage(int page) throws InterruptedException {
        Thread.sleep(4000);
        synchronized(window){

            window.set("DSM REQUESTED Page "+page);
        }
        Thread.sleep(4000);
        synchronized(window){

            window.set("PROVIDING DSM the required page");
        }
        Thread.sleep(4000);
                
        return pageTable.get(page);
        
    }
    
    //getter and setters
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

    
}
