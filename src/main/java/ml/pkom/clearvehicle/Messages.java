package ml.pkom.clearvehicle;

import com.google.gson.Gson;

import java.io.*;

import static org.bukkit.Bukkit.getLogger;

public class Messages {

    public static Language lang;
    private StringBuilder jsonBuilder;
    private String json;
    private String uesingLang = "ja_jp.json";
    private Gson gson = new Gson();
    private File langFolder;

    public void main() {
        lang = new Language();
        jsonBuilder = new StringBuilder();
        if (!Plugin.pluginFolder.exists())
            Plugin.pluginFolder.mkdir();
        langFolder = new File(Plugin.pluginFolder + File.separator + "lang");
        if (!langFolder.exists())
            langFolder.mkdir();
        // リソース内の言語ファイル移動処理
        copyLangFile("ja_jp.json");
        copyLangFile("en_us.json");

        // 読み込む言語ファイル
        uesingLang = Plugin.language + ".json";
        File langFile = new File(langFolder, uesingLang);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(langFile), "UTF-8"));
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                jsonBuilder.append(string);
            }
            bufferedReader.close();
            json = jsonBuilder.toString();
            lang = gson.fromJson(json, Language.class);
            lang.logo = lang.logo.replaceAll("%mod_name%", Plugin.MOD_NAME);
            lang.cmdlist1 = lang.cmdlist1.replaceAll("%mod_name%", Plugin.MOD_NAME);
        } catch (IOException e) {
            getLogger().info("Failed to read language file.");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {
                    getLogger().info("Failed to close language file.");
                }
            }
        }
    }

    //Logger logger;

    private void copyLangFile(String filename) {
        if (!new File(langFolder, filename).exists()) {
            // logger.info("a");
            try {

                InputStream inStream = getClass().getResourceAsStream("/lang/" + filename);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream ,"UTF-8"));

                File outputFile = new File(langFolder, filename);
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8")));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    printWriter.write(line + "\n");
                }
                bufferedReader.close();
                printWriter.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}