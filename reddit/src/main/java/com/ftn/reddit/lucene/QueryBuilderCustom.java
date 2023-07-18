package com.ftn.reddit.lucene;

public class QueryBuilderCustom {
    private static int maxEdits = 1;

    private static void validateQueryFields(String field, String value) {
        String errorMessage = "";
        if(field == null || field.equals("")){
            errorMessage += "Field not specified";
        }
        if(value == null){
            if(!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value not specified";
        }
        if(!errorMessage.equals("")){
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
