import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.args.Parser;
import ru.shift.factory.figure.FigureFactory;
import ru.shift.factory.figure.TriangleFactory;
import ru.shift.factory.output.WriterFactory;
import ru.shift.figures.specific.figures.Circle;
import ru.shift.figures.specific.figures.Rectangle;
import ru.shift.figures.specific.figures.Triangle;
import ru.shift.output.specific.writer.ConsoleFigureWriter;
import ru.shift.output.specific.writer.UserFileFigureWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class Task2Test {

    private static final Logger log = LoggerFactory.getLogger(Task2Test.class);
    private static final String ARGS_READ_FILE_PATH = "-r";
    private static final String ARGS_SAVE_FILE_PATH = "-s";

    @Test
    void parseArgsReadFilePathAndSaveFilePath() {
        String[] args = {ARGS_READ_FILE_PATH, "readFilePath", ARGS_SAVE_FILE_PATH, "saveFilePath"};
        var applicationArgs = Parser.parseApplicationArgs(args);

        assertAll(
                () -> assertNotNull(applicationArgs.getFileReadPath()),
                () -> assertNotNull(applicationArgs.getFileWritePath())
        );
    }

    @Test
    void notReadFilePath() {
        String[] args = {ARGS_SAVE_FILE_PATH,"saveFilePath"};
        assertThrows(IllegalArgumentException.class, () -> Parser.parseApplicationArgs(args));
    }

    @Test
    void CreateFigureCircle() throws IOException {
        String fileContent = "CIRCLE\n5.25";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));

        try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            var abstractFigure = FigureFactory.create(bufferedReader);
            assertEquals(Circle.class, abstractFigure.getClass());
        }
    }

    @Test
    void CreateFigureRectangle() throws IOException {
        String fileContent = "RECTANGLE\n100.2 22.5";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));

        try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            var abstractFigure = FigureFactory.create(bufferedReader);
            assertEquals(Rectangle.class, abstractFigure.getClass());
        }
    }

    @Test
    void CreateFigureTriangle() throws IOException {
        String fileContent = "TRIANGLE\n100.2 22.5 55.8";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));

        try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            var abstractFigure = FigureFactory.create(bufferedReader);
            assertEquals(Triangle.class, abstractFigure.getClass());
        }

    }

    @Test
    void CreateConsoleFigureWriter() {
        String[] args = {ARGS_READ_FILE_PATH,"readFilePath"};
        var parseConsoleArgs = Parser.parseApplicationArgs(args);

        var abstractFigureFigureWriter = WriterFactory.create(parseConsoleArgs);

        assertEquals(ConsoleFigureWriter.class, abstractFigureFigureWriter.getClass());
    }

    @Test
    void checkCreateUserWriterFile() {
        String[] args = {ARGS_READ_FILE_PATH,"readFilePath", ARGS_SAVE_FILE_PATH, "saveFilePath"};
        var parseConsoleArgs = Parser.parseApplicationArgs(args);

        var abstractFigureFigureWriter = WriterFactory.create(parseConsoleArgs);

        assertEquals(UserFileFigureWriter.class, abstractFigureFigureWriter.getClass());

    }


    @Test
    void checkIllegalArgumentExceptionFigure() throws IOException {
        String fileContent = "RECTANGLE\n100.2 22.5 22.11";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        var triangleFactory = new TriangleFactory();
        try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            assertThrows(IllegalArgumentException.class, () -> triangleFactory.read(bufferedReader));
        }


    }

}
