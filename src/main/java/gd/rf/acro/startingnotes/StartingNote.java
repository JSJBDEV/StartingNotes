package gd.rf.acro.startingnotes;


import java.util.List;


public class StartingNote {
    private final String author;
    
    private final String title;
    
    private final List<String> pages;
    
    public StartingNote(String author, String title, List<String> pages) {
        this.author = author;
        this.title = title;
        this.pages = pages;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getTitle() {
        return title;
    }
    
    public List<String> getPages() {
        return pages;
    }
}
