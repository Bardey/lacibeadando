import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class HtmlFileGenerator {

    private static String swapImageExtension(String fileName) {
        StringBuilder sb = new StringBuilder();

        if (fileName.toLowerCase().endsWith(Constant.JPG)) {
            int start = fileName.toLowerCase().lastIndexOf(Constant.JPG);
            sb.append(fileName, 0, start);
            sb.append(Constant.HTML);
        } else if (fileName.toLowerCase().endsWith(Constant.JPEG)) {
            int start = fileName.toLowerCase().lastIndexOf(Constant.JPEG);
            sb.append(fileName, 0, start);
            sb.append(Constant.HTML);
        } else if (fileName.toLowerCase().endsWith(Constant.PNG)) {
            int start = fileName.toLowerCase().lastIndexOf(Constant.PNG);
            sb.append(fileName, 0, start);
            sb.append(Constant.HTML);
        } else if (fileName.toLowerCase().endsWith(Constant.GIF)) {
            int start = fileName.toLowerCase().lastIndexOf(Constant.GIF);
            sb.append(fileName, 0, start);
            sb.append(Constant.HTML);
        } else {
            sb.append(fileName);
        }

        return sb.toString();
    }

    public static void saveImageHtml(String dir, String fileName, String basePath,
                                     String nextImgPath, String prevImgPath) throws IOException {

        FileWriter fw = new FileWriter(swapImageExtension(dir + "/" + fileName));

        String htmlString = Constant.IMG_HTML_BASE;
        htmlString = htmlString.replace("$base_path", Paths.get(dir).relativize(Paths.get(basePath + "/" + Constant.INDEX)).toString());
        htmlString = htmlString.replace("$next_img_path", Paths.get(dir).relativize(Paths.get(swapImageExtension(nextImgPath))).toString());
        htmlString = htmlString.replace("$img_path", Paths.get(dir).relativize(Paths.get(dir + "/" + fileName)).toString());
        htmlString = htmlString.replace("$prev_img_path", Paths.get(dir).relativize(Paths.get(swapImageExtension(prevImgPath))).toString());
        htmlString = htmlString.replace("$file_name", fileName);

        fw.write(htmlString);
        fw.close();

        System.out.println(Constant.SAVED_FILE + swapImageExtension(fileName));
    }

    public static void saveIndexPage(String path, String actDirName, String basePath,
                                     List<String> directories, List<String> images) throws IOException {

        FileWriter fw = new FileWriter(path + "/" + Constant.INDEX);

        String htmlString = Constant.INDEX_HTML_BASE;
        htmlString = htmlString.replace("$act_dir_name", actDirName);
        htmlString = htmlString.replace("$base_path", Paths.get(path).relativize(Paths.get(basePath + "/" + Constant.INDEX)).toString());

        StringBuilder directoriesStringBuilder = new StringBuilder();
        for (String dir : directories)
            directoriesStringBuilder.append(dir).append('\n');

        htmlString = htmlString.replace("$directories_placeholder", directoriesStringBuilder.toString());

        StringBuilder imagesStringBuilder = new StringBuilder();
        for (String img : images)
            imagesStringBuilder.append(img).append('\n');

        htmlString = htmlString.replace("$images_placeholder", imagesStringBuilder.toString());

        fw.write(htmlString);
        fw.close();

        System.out.println(Constant.SAVED_FILE + Constant.INDEX);
    }

    public static String generateFolderListEntry(String folderName, String pathToFolder) {
        return Constant.FOLDER_LIST_ITEM.replace("$path_to_folder", pathToFolder).replace("$folder_name", folderName);
    }

    public static String generateImageListEntry(String imageName, String pathToImage) {
        return Constant.IMAGE_LIST_ITEM.replace("$path_to_image", swapImageExtension(pathToImage)).replace("$image_name", imageName);
    }

}
