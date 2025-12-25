package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author SouvLithe
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 *  change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    // Hint: look at the `join`,function in Utils
    //CWD is current dir, at the same dir，create a .capers is a hidden dir
    static final File CAPERS_FOLDER = join(CWD,".capers");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     * try to initial the dir
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        CAPERS_FOLDER.mkdir();
        File dogFolder = join(CAPERS_FOLDER, "dogs");
        dogFolder.mkdir();
        // story is a file
        File storyFile = join(CAPERS_FOLDER, "story");
        try {
            storyFile.createNewFile();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File storyFile = join(CAPERS_FOLDER, "story");
        // if the file was created,read it to get the origin data
        String lastStory = Utils.readContentsAsString(storyFile);
        // renew a outputstream storyFile，write lastStory and text in order，note the order！！
        Utils.writeContents(storyFile,lastStory,text,"\n");
        // get the final content
        String story = readContentsAsString(storyFile);
        System.out.println(story);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog dog = new Dog(name, breed, age);
        dog.saveDog();
        System.out.println(dog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog dog = Dog.fromFile(name);
        dog.haveBirthday();
        dog.saveDog();
    }
}
