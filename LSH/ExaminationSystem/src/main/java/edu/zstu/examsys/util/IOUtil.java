package edu.zstu.examsys.util;

import java.io.*;

public class IOUtil {
    public static String readAll(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        return readAll(reader);
    }

    public static String readAll(BufferedReader reader) {
        try {
            StringBuilder sbf = new StringBuilder();

            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr).append("\n");
            }
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeAll(BufferedWriter writer, String content) {
        try {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
