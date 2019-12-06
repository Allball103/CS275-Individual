package com.example.a275del3.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains handling of all matchup chart data
 */
public class MatchupChart {

    public static final List<Chart> ITEMS = new ArrayList<Chart>();
    public static final Map<String, Chart> ITEM_MAP = new HashMap<String, Chart>();

    private static void addItem(Chart item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Chart createItem(int position, String name, ArrayList<String> details) {
        return new Chart(Integer.toString(position), name, makeDetails(position,name,details));
    }

    private static String makeDetails(int position, String name, ArrayList<String> details) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append("'s Tier List: \n");
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < details.size(); i++) {
            builder.append(details.get(i));
            builder.append("\n");
        }
        return builder.toString();
    }

    public static class Chart {
        public final String id;
        public final String content;
        public final String details;

        public Chart(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
