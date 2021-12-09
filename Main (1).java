import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        //Argument check
        if (args.length != 1) {
            System.out.println(Constant.ARG_ERROR);
            System.exit(1);
        } else if (!Files.isDirectory(Paths.get(args[0]))) {
            System.out.println(Constant.ARG_ERROR);
            System.exit(1);
        }

        //Find directories - init step
        String pathBase = args[0];
        List<Path> dirs = findDirectories(new File(pathBase));

        //Generate html page for each image
        System.out.println(Constant.GEN_IMG_HTML_LOG);
        generateHtmlPagesForImages(pathBase, dirs);

        System.out.println();

        //Generate index.html page for each directory
        System.out.println(Constant.GEN_IDX_HTML_LOG);
        generateIndexHtmlPages(pathBase, dirs);

        System.out.println();
        System.out.println(Constant.BUILD_COMPLETE);

        System.out.println();
        System.out.println(Constant.OPEN_RESULT);
        try {
            openResult(pathBase);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void generateHtmlPagesForImages(String pathBase, List<Path> dirs) {

        for (Path dir : dirs) {

            System.out.println(Constant.PROC_DIR + Paths.get(pathBase).relativize(dir) + "...");

            List<Path> tmpImgPaths = findImages(new File(dir.toString()));
            for (int i = 0; i < tmpImgPaths.size(); i++) {
                try {
                    if (i == 0) {
                        HtmlFileGenerator.saveImageHtml(dir.toString(),
                                tmpImgPaths.get(i).getFileName().toString(),
                                pathBase, dir + "/" +
                                        tmpImgPaths.get(i+1).getFileName().toString(),
                                dir + "/" +
                                        tmpImgPaths.get(i).getFileName().toString());
                    } else if (i == tmpImgPaths.size() - 1 ) {
                        HtmlFileGenerator.saveImageHtml(dir.toString(),
                                tmpImgPaths.get(i).getFileName().toString(),
                                pathBase, dir + "/" +
                                tmpImgPaths.get(i).getFileName().toString(),
                                dir + "/" +
                                        tmpImgPaths.get(i-1).getFileName().toString());
                    } else {
                        HtmlFileGenerator.saveImageHtml(dir.toString(),
                                tmpImgPaths.get(i).getFileName().toString(),
                                pathBase, dir + "/" +
                                        tmpImgPaths.get(i + 1).getFileName().toString(),
                                dir + "/" +
                                        tmpImgPaths.get(i-1).getFileName().toString());
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            }

        }
    }

    private static void generateIndexHtmlPages(String pathBase, List<Path> dirs) {

        for (Path dir : dirs) {

            System.out.println(Constant.PROC_DIR + Paths.get(pathBase).relativize(dir) + "...");

            File[] content = new File(dir.toString()).listFiles();

            List<String> directories = new ArrayList<>();
            List<String> images = new ArrayList<>();

            if (!dir.toString().equals(pathBase.replace('/', '\\')))
                directories.add(Constant.FOLDER_LIST_PREV_ITEM);

            for (int i = 0; i < content.length; i++) {
                if (content[i].isDirectory()) {
                    String dirListItem = HtmlFileGenerator.generateFolderListEntry(Paths.get(content[i].getPath()).getFileName().toString(),
                                                                                   Paths.get(dir.toString()).relativize(Paths.get(content[i].getPath())).toString());
                    directories.add(dirListItem);
                }
                else if (isImage(Paths.get(content[i].getPath()))) {
                    String imageListItem = HtmlFileGenerator.generateImageListEntry(Paths.get(content[i].getPath()).getFileName().toString(),
                                                                                    Paths.get(dir.toString()).relativize(Paths.get(content[i].getPath())).toString());
                    images.add(imageListItem);
                }
            }

            try {
                HtmlFileGenerator.saveIndexPage(dir.toString(), dir.getFileName().toString(), pathBase, directories, images);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

        }

    }

    private static List<Path> findImages(File from) {
        List<Path> files = new ArrayList<>();
        try {
            Files.list(Paths.get(from.getPath()))
                    .filter(Files::isRegularFile)
                    .filter(Main::isImage)
                    .collect(Collectors.toCollection(()  ->  files));

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return files;
    }

    private static List<Path> findDirectories(File from) {
        List<Path> directories = new ArrayList<>();

        try {
            Files.walk(Paths.get(from.getPath()))
                    .filter(Files::isDirectory)
                    .collect(Collectors.toCollection(()  ->  directories));

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return directories;
    }

    private static boolean isImage(Path p) {
        return (Files.isRegularFile(p) && (p.toString().toLowerCase().endsWith(Constant.JPEG) || p.toString().toLowerCase().endsWith(Constant.JPG)
                || p.toString().toLowerCase().endsWith(Constant.PNG) || p.toString().toLowerCase().endsWith(Constant.GIF)));
    }

    private static void openResult(String pathBase) throws IOException {
        Desktop.getDesktop().browse(new File(pathBase + "/" + Constant.INDEX).toURI());
    }

}
