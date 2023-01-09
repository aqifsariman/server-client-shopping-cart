package shoppingCart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReaderWriter {
    private Path path;
    private File file;
    private FileReader reader;
    BufferedReader bufferReader;
    FileWriter writer;

    public ReaderWriter(String path) throws FileNotFoundException {
        this.path = Paths.get(path);
        this.file = this.path.toFile();
        this.reader = new FileReader(file);
        this.bufferReader = new BufferedReader(reader);
    }

    public String bufferedFile() throws IOException {
        return this.bufferReader.readLine();
    }

    public FileWriter bufferedWriter(String path) throws IOException {
        return this.writer = new FileWriter(path);
    }

    public void closeBuffer() throws IOException {
        this.bufferReader.close();
    }

    public void closeReader() throws IOException {
        this.reader.close();
    }

}

// // FETCH FILE FROM PATH
// Path cartPath = Paths.get(path);
// File cartFile = cartPath.toFile();

// // READ FILE USING FILEREADER AND BUFFEREDREADER
// FileReader reader = new FileReader(cartFile);
// BufferedReader buffer = new BufferedReader(reader);