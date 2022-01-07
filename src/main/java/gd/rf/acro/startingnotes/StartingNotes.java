package gd.rf.acro.startingnotes;


import net.fabricmc.api.ModInitializer;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StartingNotes implements ModInitializer {
    public static final List<StartingNote> STARTING_NOTES;
    
    static {
        try {
            List<StartingNote> startingNotes = new ArrayList<>();
            
            List<String> booksFiles = FileUtils.readLines(new File("./config/StartingNotes/books.txt"), "utf-8");
            for (String book : booksFiles) {
                List<String> abook = FileUtils.readLines(new File("./config/StartingNotes/" + book), "utf-8");
                String author = abook.remove(0);
                String title = abook.remove(0);
                
                startingNotes.add(new StartingNote(author, title, abook));
            }
            
            STARTING_NOTES = Collections.unmodifiableList(startingNotes);
        } catch (IOException e) {
            throw new CrashException(new CrashReport("IO exception while reading starting notes", e));
        }
    }
    @Override
    public void onInitialize() {
        // TODO: 2022-01-06 
    }
}
