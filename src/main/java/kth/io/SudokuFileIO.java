package kth.io;

import kth.model.SudokuBoard;
import java.io.*;

/**
 * The {@code SudokuFileIO} class provides methods for serializing and deserializing
 * {@code SudokuBoard} objects to and from files. This allows saving the current state
 * of the game and loading it back from a file.
 *
 * @author Majd & Marvin
 * @version 1.0
 */
public class SudokuFileIO {

    /**
     * Serializes the given {@code SudokuBoard} object and saves it to the specified file.
     * This allows saving the current game state to be loaded later.
     *
     * @param board the {@code SudokuBoard} object to be serialized.
     * @param file the {@code File} where the serialized board will be saved.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public static void serializeToFile(SudokuBoard board, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(board);
        }
    }

    /**
     * Deserializes a {@code SudokuBoard} object from the specified file.
     * This allows loading a saved game state from a file.
     *
     * @param file the {@code File} from which the {@code SudokuBoard} object will be deserialized.
     * @return the deserialized {@code SudokuBoard} object.
     * @throws IOException if an I/O error occurs while reading from the file.
     * @throws ClassNotFoundException if the {@code SudokuBoard} class cannot be found during deserialization.
     */
    public static SudokuBoard deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (SudokuBoard) ois.readObject();
        }
    }
}
