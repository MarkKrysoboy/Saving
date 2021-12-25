import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static String DEFAULT_FOLDER = "D://User//Games//";

    public static void saving(Object object) {
        try (FileOutputStream fos = new FileOutputStream(DEFAULT_FOLDER + "savegames//"
                + "save(" + object.hashCode() + ").dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void ziping(String zipPath, List<File> listFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (File file : listFiles) {
                try (FileInputStream fis = new FileInputStream(file.getPath())) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleting(List<File> listFiles) {
        for (File file : listFiles) {
            new File(String.valueOf(file)).delete();
        }
    }

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(1000, 500, 50, 2.5);
        GameProgress gameProgress2 = new GameProgress(500, 250, 25, 1.25);
        GameProgress gameProgress3 = new GameProgress(100, 50, 5, 0.25);
        saving(gameProgress1);
        saving(gameProgress2);
        saving(gameProgress3);

        List<File> listFiles = new ArrayList<>();
        File dir = new File(DEFAULT_FOLDER + "savegames//");
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isFile()) {
                    listFiles.add(item);
                }
            }
        }

        ziping(DEFAULT_FOLDER + "savegames//save_archive.zip", listFiles);
        deleting(listFiles);


    }


}

