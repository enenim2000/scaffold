package com.enenim.scaffold.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enenim.scaffold.constant.RouteConstant.*;

public class RouteUtil {

    /**
     * Dependencies Initialization Begins
     */
    private static final List<String> USER_STAFF_CREATE_DEP = new ArrayList<String>(){{
        add(ADMINISTRATION_GROUP_INDEX); add(ADMINISTRATION_BRANCH_INDEX); add(ADMINISTRATION_ACTIVEHOUR_INDEX);
    }};




    /**
     * Parent & Dependent Routes Linking Begins
     */
    private static final Map<String, List<String>> RELATIONSHIPS = new HashMap<String, List<String>>(){{
        put(USER_STAFF_CREATE, USER_STAFF_CREATE_DEP);
    }};

    public static List<String> getDependencies(String parentRoute){
        List<String> dependencies = RELATIONSHIPS.get(parentRoute);
        if(StringUtils.isEmpty(dependencies)){
            return new ArrayList<>();
        }
        return  dependencies;
    }
}
