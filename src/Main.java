import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        GameProgress gameProgress1 = new GameProgress(100, 3, 5, 10.5);
        GameProgress gameProgress2 = new GameProgress(80, 2, 3, 7.8);
        GameProgress gameProgress3 = new GameProgress(90, 4, 6, 12.3);
        try {
            saveGameProgress(gameProgress1, "/Users/elkapusto/Games/savegames/save1.dat");
            saveGameProgress(gameProgress2, "/Users/elkapusto/Games/savegames/save2.dat");
            saveGameProgress(gameProgress3, "/Users/elkapusto/Games/savegames/save3.dat");
            System.out.println("Объекты успешно сохранены");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении объектов: " + e.getMessage());
        }
        List<String> filesToZip = List.of("/Users/elkapusto/Games/savegames/save1.dat",
                "/Users/elkapusto/Games/savegames/save2.dat",
                "/Users/elkapusto/Games/savegames/save3.dat");

        String zipFilePath = "/Users/elkapusto/Games/savegames/save.zip";
        try {
            zipFiles(zipFilePath, filesToZip);
            System.out.println("Файлы успешно запакованы");
        } catch (IOException e) {
            System.out.println("Ошибка при запаковке файлов: " + e.getMessage());
        }

        File folder = new File("/Users/elkapusto/Games/savegames/");
        File[] files = folder.listFiles();
        for (File file : files) {
            if (!file.getName().endsWith(".zip")) {
                file.delete();
                {
                    System.out.println("Файлы удалены");
                }
            }
        }
    }

    public static void saveGameProgress(GameProgress gameProgress, String filePath) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        }
    }

    public static void zipFiles(String zipFilePath, List<String> filesToZip) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : filesToZip) {
                try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                    ZipEntry zipEntry = new ZipEntry(filePath);
                    zipOutputStream.putNextEntry(zipEntry);

                    int bytesRead;
                    byte[] buffer = new byte[1024];
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        zipOutputStream.write(buffer, 0, bytesRead);
                    }

                    zipOutputStream.closeEntry();
                }
            }
        }
    }
}