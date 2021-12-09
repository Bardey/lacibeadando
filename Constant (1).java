public class Constant {

    //HTML templates
    public static final String IMG_HTML_BASE = """
        <!DOCTYPE html>
        <html lang="hu">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>$file_name</title>
        </head>
        <body>
            <a href="$base_path"><h2>Start Page</h2></a>
            <hr>
            <a href="index.html">^^</a><br>
            <a href="$prev_img_path"><<</a> <strong>$file_name</strong> <a href="$next_img_path">>></a>
            <hr>
            <a href="$next_img_path"><img src="$img_path" style="width: 20%; height: 50%;"></a>
        </body>
        </html>""";

    public static final String INDEX_HTML_BASE = """
        <!DOCTYPE html>
        <html lang="hu">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>$act_dir_name</title>
        </head>
        <body>
            <a href="$base_path"><h2>Start Page</h2></a>
            <hr>
            <h3>Directories:</h3>
            <ul>
                $directories_placeholder
            </ul>
            <hr>
            <h3>Images:</h3>
            <ul>
                $images_placeholder
            </ul>
        </body>
        </html>""";


    //File extensions
    public static final String JPG = ".jpg";
    public static final String JPEG = ".jpeg";
    public static final String PNG = ".png";
    public static final String GIF = ".gif";
    public static final String HTML = ".html";

    //HTML parts
    public static final String INDEX = "index" + HTML;
    public static final String FOLDER_LIST_ITEM = "<li><a href=\"$path_to_folder\\" + INDEX + "\">$folder_name</a></li>";
    public static final String FOLDER_LIST_PREV_ITEM = "<li><a href=\"..\\" + INDEX + "\"><<</a></li>";
    public static final String IMAGE_LIST_ITEM = "<li><a href=\"$path_to_image\">$image_name</a></li>";

    //Log messages
    public static final String GEN_IMG_HTML_LOG = "Generating HTML files for images...";
    public static final String GEN_IDX_HTML_LOG = "Generating " + INDEX + " files for directories...";
    public static final String BUILD_COMPLETE = "Build complete.";
    public static final String PROC_DIR = "Processing directory: ";
    public static final String ARG_ERROR = "Error: provide the path of a directory";
    public static final String SAVED_FILE = "\tSaved file: ";
    public static final String OPEN_RESULT = "Opening result...";
}
