package pl.kuba565.Util;

public class WikiStringFormatter {
    public static String format(String text) {
        return text.replaceAll("\t\n", StringUtils.EMPTY)
                .replaceAll("\t", StringUtils.EMPTY)
                .replaceAll(" \n", "\n")
                .replaceAll("Done in\n''", "Done in ''")
                .trim()
                .concat("\n");
    }
}
