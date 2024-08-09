package com.jeanneboyarsky.strings;

public class TextBlocks {

    public String getJsonOld(String search) {
        String json = "{" +
                "   \"query\": \"%s\"" +
                "   \"start\": \"1\"," +
                "   \"end\": \"10\"" +
                "}";
        return String.format(json, search);
    }

    public String getJson(String search) {
        String json = """
            {
            "query": "%s"
            "start": "1"
            "end": "10"
            }""";
        return String.format(json, search);
    }

    public void xml() {
        String textBlock = """
        <session>
            <speaker>
                "Jeanne Boyarsky"
            </speaker>
        </session>""";
    }


}
