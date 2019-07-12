package pl.kuba565.Util;

public class TextTrimmer {
    public static String trim(StringBuilder stringBuilder) {
        String result = stringBuilder.toString();
        result = result.replaceAll("\t\n", "");
        result = result.replaceAll("\t", "");
        result = result.replaceAll(" \n", "\n");
        result = result.replaceAll("Done in\n''", "Done in ''");
        result = result.trim();
        result = result.concat("\n");
        return result;
    }
}
