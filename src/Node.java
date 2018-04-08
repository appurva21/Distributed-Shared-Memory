import java.util.*;
import java.io.*;

public class Node {
    
    private ArrayList<Page> pageTable;
    private ArrayList<Integer> requiredPages;
    private final int SIZE = 2;
    public Node(String name, ArrayList page, ArrayList required){
        this.name = name;
        this.pageTable = page;
        this.requiredPages = required;
        this.initalize(name,pageTable);
    }
    public void initalize(String name,ArrayList<Page> pageTable){
        Service.serviceIntialize(name,pageTable);
    }
    public void memoryManager(){
        while(requiredPages.size()!=0){
            Integer rPage = requiredPages.remove(0);
            boolean flag=false;
            Page temp=null;
            for (int i = 0; i < pageTable.size(); i++) {
                if(rPage==(pageTable.get(i).getNumber())){
                    flag=true;
                    temp=pageTable.get(i);
                    break;
                }
            }
            if(flag){
                System.out.println(name +": Page Found-> Page Number: "+ temp.getNumber() +" Content: " + temp.getContent());
            }
            else{
                //pagefault
                System.out.println(name +": Page Not Found-> Page Number: "+ rPage);
              Page newPage = Service.servicePageFault(name, rPage);
                //LIFO
                if(pageTable.size()==2){
                    Page rmPage = pageTable.remove(0);
                    Service.serviceUpdate(name,rmPage); 
                }
                pageTable.add(newPage);
                      
                
                
            }
            
        }
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Page> getPageTable() {
        return pageTable;
    }

    public void setPageTable(ArrayList<Page> pageTable) {
        this.pageTable = pageTable;
    }

    public ArrayList<Integer> getRequiredPages() {
        return requiredPages;
    }

    public void setRequiredPages(ArrayList<Integer> requiredPages) {
        this.requiredPages = requiredPages;
    }
}
