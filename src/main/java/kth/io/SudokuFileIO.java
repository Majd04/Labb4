package kth.io;

import kth.model.SudokuBoard;
import java.io.*;

public class SudokuFileIO {

    public static void serializeToFile(SudokuBoard board, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(board);
        }
    }

    public static SudokuBoard deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (SudokuBoard) ois.readObject();
        }
    }

}
