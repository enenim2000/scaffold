package com.enenim.scaffold.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorCategoryUtil {

    private static final String RENTING_SERVICE_FORM = "{ \"1\": { \"title\": \"Audience Details\", \"fields\": [ { \"type\": \"number\", \"name\": \"no_of_audience\", \"value\": \"\", \"options\": null, \"label\": \"Audience Population\", \"placeholder\": \"Enter audience population\" }, { \"type\": \"select\", \"name\": \"age_group\", \"value\": \"\", \"options\": [ { \"settingKey\": \"children\", \"value\": \"Children\" }, { \"settingKey\": \"adults\", \"value\": \"Adults\" } ], \"label\": \"Age Category\", \"placeholder\": \"Choose age category\" } ] }, \"2\": { \"title\": \"Chairs Details\", \"fields\": [ { \"type\": \"textarea\", \"name\": \"description\", \"value\": \"\", \"options\": null, \"label\": \"Description\", \"placeholder\": \"Enter description\" }, { \"type\": \"select\", \"name\": \"chair_color\", \"value\": \"white\", \"options\": [ { \"settingKey\": \"white\", \"value\": \"White\" }, { \"settingKey\": \"blue\", \"value\": \"Blue\" }, { \"settingKey\": \"grey\", \"value\": \"Grey\" } ], \"label\": \"Chair Color\", \"placeholder\": \"Choose color of chair\" } ] }, \"3\": { \"title\": \"Chairs Coverings\", \"fields\": [ { \"type\": \"radio\", \"name\": \"need_coverings\", \"value\": \"yes\", \"options\": [ { \"settingKey\": \"no\", \"value\": \"No\" }, { \"settingKey\": \"yes\", \"value\": \"Yes\" } ], \"label\": \"Do you need chair coverings?\", \"placeholder\": \"\" }, { \"type\": \"select\", \"name\": \"coverings_color\", \"value\": \"white\", \"options\": [ { \"settingKey\": \"pink\", \"value\": \"Pink\" }, { \"settingKey\": \"blue\", \"value\": \"Blue\" }, { \"settingKey\": \"yellow\", \"value\": \"Yellow\" } ], \"label\": \"Coverings Color\", \"placeholder\": \"Pick Coverings Color\" } ] } }";
    private static final String PHOTOGRAPHY_SERVICE_FORM = "{}";
    private static final String CAKES_AND_CREAMS_SERVICE_FORM = "{}";

    private static Map<String, VendorCategory> VENDOR_CATEGORIES = new HashMap<String, VendorCategory>() {{
        put("renting_service", new VendorCategory("Renting", RENTING_SERVICE_FORM));
        put("photography_service", new VendorCategory("Photography", PHOTOGRAPHY_SERVICE_FORM));
        put("cakes_and_creams_service", new VendorCategory("Cakes & Creams", CAKES_AND_CREAMS_SERVICE_FORM));
    }};

    public static List<VendorCategory> getVendorCategories(){
        List<VendorCategory> vendorCategories = new ArrayList<>();
        for(Map.Entry<String, VendorCategory> entry : VENDOR_CATEGORIES.entrySet()){
            VendorCategory vendorCategory = entry.getValue();
            vendorCategory.setKey(entry.getKey());
            vendorCategories.add(vendorCategory);
        }
        return vendorCategories;
    }

    public static VendorCategory getVendorCategory(String key){
        return VENDOR_CATEGORIES.get(key);
    }
}