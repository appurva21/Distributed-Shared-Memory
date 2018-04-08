public class Page {
    private int number;
    private String content;
    private String owner;
    public Page(int number, String owner, String content) {
            this.number = number;
            this.content = content;
            this.owner = owner;
        }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    
}
