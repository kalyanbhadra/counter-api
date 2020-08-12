package com.optus.counterapi.helpers;

import java.io.PrintWriter;
import java.util.List;

/**
 * Helps to generate csv data from list of search results
 * 
 * @author kalyan
 *
 */
public class CsvUtil {

    public static void downloadCsv(PrintWriter writer, List<TopCountResultWrapper> searches) {
        for (TopCountResultWrapper top : searches) {
            writer.write(top.getSearchText() + "|" + top.getCount() + "\n");
        }
    }
}
