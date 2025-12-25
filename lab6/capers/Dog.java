package capers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static capers.Utils.*;

/**
 * Represents a dog that can be serialized.
 *
 * @author SouvLithe
 */
public class Dog implements Serializable {

    /**
     * Folder that dogs live in.
     */
    // (hint: look at the `join`,function in Utils)
    // this path is as same as the problem
    static final File DOG_FOLDER = join(CapersRepository.CAPERS_FOLDER, "dogs");

    /**
     * Age of dog.
     */
    private int age;
    /**
     * Breed of dog.
     */
    private String breed;
    /**
     * Name of dog.
     */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     *
     * @param name  Name of dog
     * @param breed Breed of dog
     * @param age   Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        //  (hint: look at the Utils file)
        Dog dog = null;
        File readFile = join(DOG_FOLDER, name);
        // if not implements Serializable interface, Dog.class will not able to be used
        readObject(readFile, Dog.class);
        return dog;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        // (hint: don't forget dog names are unique)
        File saveFile = join(DOG_FOLDER, this.name);
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        // get the stream
        writeObject(saveFile, this);
    }

    @Override
    public String toString() {
        return String.format(
                "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
                name, breed, age);
    }

}
