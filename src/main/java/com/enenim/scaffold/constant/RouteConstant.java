package com.enenim.scaffold.constant;

public interface RouteConstant {
    //Modules
    String USER = "app";
    String REPORT = "report";
    String SERVICE = "service";
    String VENDOR = "vendor";
    String STAFF = "staff";
    String BENEFICIARY = "beneficiary";
    String SETTLEMENT = "settlement";
    String CONSUMER = "consumer";
    String ADMINISTRATION = "administration";


    //Actions
    String INDEX = "index";
    String LIST = "list";
    String CREATE = "create";
    String UPDATE = "update";
    String DELETE = "delete";
    String SHOW = "show";
    String RESET = "reset";
    String TOGGLE = "toggle";
    String GOLIVE = "golive";
    String DESTROY = "destroy";
    String SYNC = "sync";
    String DOT = ".";


    //Group sub-modules
    String GROUP = DOT + "group" + DOT;
    String ADMINISTRATION_GROUP_INDEX = ADMINISTRATION + GROUP + INDEX;
    String ADMINISTRATION_GROUP_CREATE = ADMINISTRATION + GROUP + CREATE;
    String ADMINISTRATION_GROUP_UPDATE= ADMINISTRATION + GROUP + UPDATE;
    String ADMINISTRATION_GROUP_TOGGLE = ADMINISTRATION + GROUP + TOGGLE;
    String ADMINISTRATION_GROUP_SHOW = ADMINISTRATION + GROUP + SHOW;
    String ADMINISTRATION_GROUP_STAFF = ADMINISTRATION + GROUP + STAFF;

    //Category sub-modules
    String CATEGORY = DOT + "category" + DOT;
    String ADMINISTRATION_CATEGORY_INDEX = ADMINISTRATION + CATEGORY + INDEX;
    String ADMINISTRATION_CATEGORY_CREATE = ADMINISTRATION + CATEGORY + CREATE;
    String ADMINISTRATION_CATEGORY_UPDATE= ADMINISTRATION + CATEGORY + UPDATE;
    String ADMINISTRATION_CATEGORY_TOGGLE = ADMINISTRATION + CATEGORY + TOGGLE;
    String ADMINISTRATION_CATEGORY_SHOW = ADMINISTRATION + CATEGORY + SHOW;
    String ADMINISTRATION_CATEGORY_SERVICES = ADMINISTRATION + CATEGORY + "services";
    String ADMINISTRATION_CATEGORY_VENDORS = ADMINISTRATION + CATEGORY + "vendors";
    String ADMINISTRATION_CATEGORY_FEATURED = ADMINISTRATION + CATEGORY + "featured";
    
    //Permission sub-modules
    String PERMISSION = DOT + "permission" + DOT;
    String ADMINISTRATION_PERMISSION_INDEX = ADMINISTRATION + PERMISSION + INDEX;
    String ADMINISTRATION_PERMISSION_UPDATE= ADMINISTRATION + PERMISSION + UPDATE;
    String ADMINISTRATION_PERMISSION_SHOW = ADMINISTRATION + PERMISSION + SHOW;

    //Authorizer sub-modules
    String AUTHORIZER = DOT + "authorizer" + DOT;
    String ADMINISTRATION_AUTHORIZER_INDEX = ADMINISTRATION + AUTHORIZER + INDEX;
    String ADMINISTRATION_AUTHORIZER_UPDATE= ADMINISTRATION + AUTHORIZER + UPDATE;
    String ADMINISTRATION_AUTHORIZER_SHOW = ADMINISTRATION + AUTHORIZER + SHOW;

    //Authorize sub-modules
    String AUTHORIZE = DOT + "authorize" + DOT;
    String ADMINISTRATION_AUTHORIZE_INDEX = ADMINISTRATION + AUTHORIZE + INDEX;
    String ADMINISTRATION_AUTHORIZE_ME = ADMINISTRATION + AUTHORIZE + "me";
    String ADMINISTRATION_AUTHORIZE_ALL = ADMINISTRATION + AUTHORIZE + "all";
    String ADMINISTRATION_AUTHORIZE_CHECKED = ADMINISTRATION + AUTHORIZE + "checked";
    String ADMINISTRATION_AUTHORIZE_SHOW = ADMINISTRATION + AUTHORIZE + SHOW;
    String ADMINISTRATION_AUTHORIZE_DISCARD = ADMINISTRATION + AUTHORIZE + "discard";
    String ADMINISTRATION_AUTHORIZE_FORWARD = ADMINISTRATION + AUTHORIZE + "forward";
    String ADMINISTRATION_AUTHORIZE_MULTIPLE = ADMINISTRATION + AUTHORIZE + "forward" + DOT +"multiple";
    String ADMINISTRATION_AUTHORIZE_APPROVE = ADMINISTRATION + AUTHORIZE + "approve";
    String ADMINISTRATION_AUTHORIZE_REJECT = ADMINISTRATION + AUTHORIZE + "reject";

    //Dispute Message sub-modules
    String DISPUTE_MESSAGE = DOT + "dispute-message" + DOT;
    String ADMINISTRATION_DISPUTE_MESSAGE_INDEX = ADMINISTRATION + DISPUTE_MESSAGE + INDEX;
    String ADMINISTRATION_DISPUTE_MESSAGE_CREATE = ADMINISTRATION + DISPUTE_MESSAGE + CREATE;
    String ADMINISTRATION_DISPUTE_MESSAGE_UPDATE = ADMINISTRATION + DISPUTE_MESSAGE + UPDATE;
    String ADMINISTRATION_DISPUTE_MESSAGE_TOGGLE = ADMINISTRATION + DISPUTE_MESSAGE + TOGGLE;

    //Service sub-modules
    String SERVIZE = DOT + "service" + DOT;
    String ADMINISTRATION_SERVICE_INDEX = ADMINISTRATION + SERVIZE + INDEX;
    String ADMINISTRATION_SERVICE_CREATE = ADMINISTRATION + SERVIZE + CREATE;
    String ADMINISTRATION_SERVICE_UPDATE = ADMINISTRATION + SERVIZE + UPDATE;
    String ADMINISTRATION_SERVICE_TOGGLE = ADMINISTRATION + SERVIZE + TOGGLE;
    String ADMINISTRATION_SERVICE_SHOW = ADMINISTRATION + SERVIZE  + SHOW;

    //Currency sub-modules
    String CURRENCY = DOT + "currency" + DOT;
    String ADMINISTRATION_CURRENCY_INDEX = ADMINISTRATION + CURRENCY + INDEX;
    String ADMINISTRATION_CURRENCY_CREATE = ADMINISTRATION + CURRENCY + CREATE;
    String ADMINISTRATION_CURRENCY_UPDATE= ADMINISTRATION + CURRENCY + UPDATE;
    String ADMINISTRATION_CURRENCY_TOGGLE = ADMINISTRATION + CURRENCY + TOGGLE;

    //Task sub-modules
    String TASK = DOT + "task" + DOT;
    String ADMINISTRATION_TASK_INDEX = ADMINISTRATION + TASK + INDEX;
    String ADMINISTRATION_TASK_CREATE = ADMINISTRATION + TASK + CREATE;
    String ADMINISTRATION_TASK_UPDATE = ADMINISTRATION + TASK + UPDATE;
    String ADMINISTRATION_TASK_SYNC = ADMINISTRATION + TASK + SYNC;
    String ADMINISTRATION_TASK_DESTROY = ADMINISTRATION + TASK + DESTROY;
    String ADMINISTRATION_TASK_SHOW = ADMINISTRATION + TASK + SHOW;

    //Setting sub-modules
    String SETTING = DOT + "setting" + DOT;
    String ADMINISTRATION_SETTING_INDEX = ADMINISTRATION + SETTING + INDEX;
    String ADMINISTRATION_SETTING_ADD = ADMINISTRATION + SETTING + "add";
    String ADMINISTRATION_SETTING_UPDATE = ADMINISTRATION + SETTING + UPDATE;
    String ADMINISTRATION_SETTING_RESET = ADMINISTRATION + SETTING + RESET;
    String ADMINISTRATION_SETTING_SYNC = ADMINISTRATION + SETTING + SYNC;
    String ADMINISTRATION_SETTING_CATEGORY = ADMINISTRATION + SETTING + "category";
    String ADMINISTRATION_SETTING_CATEGORIES = ADMINISTRATION + SETTING + "categories";
    String ADMINISTRATION_SETTING_SHOW = ADMINISTRATION + SETTING + SHOW;

    String ADMINISTRATION_FEEDBACK_SHOW = ADMINISTRATION + "feedback" + SHOW;

    //Audit modules
    String AUDIT_MODULE = "audit" + DOT;
    String AUDIT_INDEX = AUDIT_MODULE + INDEX;
    String AUDIT_DESTROY = AUDIT_MODULE + DESTROY;
    String AUDIT_SHOW = AUDIT_MODULE + SHOW;

    //Audit sub-modules
    String AUDIT = DOT + "audit" + DOT;
    String REPORT_AUDIT_INDEX = REPORT + AUDIT + INDEX;
    String REPORT_AUDIT_FILTER = REPORT + AUDIT + "filter";
    String REPORT_AUDIT_SHOW = REPORT + AUDIT + SHOW;

    //Account sub-modules
    String ACCOUNT = DOT + "account" + DOT;
    String USER_ACCOUNT_LOGIN = USER + ACCOUNT + "login";
    String USER_ACCOUNT_MD5LOGIN = USER + ACCOUNT + "md5-login";
    String USER_ACCOUNT_SECRET_CHALLENGE = USER + ACCOUNT + "secret" + DOT + "challenge";
    String USER_ACCOUNT_SECRET_CREATE = USER + ACCOUNT + "secret" + DOT + "create";
    String USER_ACCOUNT_SECRET_UPDATE = USER + ACCOUNT + "secret" + DOT + "update";
    String USER_ACCOUNT_SECRET_GET = USER + ACCOUNT + "secret" + DOT + "get";
    String USER_ACCOUNT_VERIFY = USER + ACCOUNT + "verify";
    String USER_ACCOUNT_ENABLE = USER + ACCOUNT + "enable";
    String USER_ACCOUNT_DISABLE = USER + ACCOUNT + "disable";
    String USER_ACCOUNT_STAFF_TASK = USER + ACCOUNT + STAFF + DOT + "task";
    String USER_ACCOUNT_STAFF_RESETPASSWORD = USER + ACCOUNT + STAFF + DOT + "reset-password";
    String USER_ACCOUNT_UPDATELOGO = USER + ACCOUNT + "updateLogo";
    String USER_ACCOUNT_RESETPASSWORD_HASH = USER + ACCOUNT + "reset-password";
    String USER_ACCOUNT_LOGOUT = USER + ACCOUNT + "logout";
    String USER_ACCOUNT_CHANGEPASSWORD = USER + ACCOUNT + "change-password";
    String USER_ACCOUNT_CHANGEPASSWORDPLAIN = USER + ACCOUNT + "change-password-plain";


    //Staff sub-modules
    String STAFF_SUBMODULE = DOT + "staff" + DOT;
    String USER_STAFF_INDEX = USER + STAFF_SUBMODULE + INDEX;
    String USER_STAFF_ACCOUNT_LOOKUP = USER + STAFF_SUBMODULE + "account" + DOT + "lookup";
    String USER_STAFF_CREATE = USER + STAFF_SUBMODULE + CREATE;
    String USER_STAFF_SHOW = USER + STAFF_SUBMODULE + SHOW;
    String USER_STAFF_UPDATE = USER + STAFF_SUBMODULE + UPDATE;
    String USER_STAFF_ACTIONS = USER + STAFF_SUBMODULE + "actions";
    String USER_STAFF_AUDITS = USER + STAFF_SUBMODULE + "audits";
    String USER_STAFF_AUTHORIZATIONS = USER + STAFF_SUBMODULE + "authorizations";
    String USER_STAFF_TOGGLE = USER + STAFF_SUBMODULE + TOGGLE;

    //Beneficiary sub-modules
    String BENEFICIARY_SUBMODULE = DOT + "beneficiary" + DOT;
    String USER_BENEFICIARY_INDEX = USER + BENEFICIARY_SUBMODULE + INDEX;
    String USER_BENEFICIARY_CREATE = USER + BENEFICIARY_SUBMODULE + CREATE;
    String USER_BENEFICIARY_SHOW = USER + BENEFICIARY_SUBMODULE + SHOW;
    String USER_BENEFICIARY_UPDATE = USER + BENEFICIARY_SUBMODULE + UPDATE;
    String USER_BENEFICIARY_ACTIONS = USER + BENEFICIARY_SUBMODULE + "actions";
    String USER_BENEFICIARY_GLOBAL = USER + BENEFICIARY_SUBMODULE + "global";
    String USER_BENEFICIARY_LIST = USER + BENEFICIARY_SUBMODULE + LIST;
    String USER_BENEFICIARY_TOGGLE = USER + BENEFICIARY_SUBMODULE + TOGGLE;

    //Vendor sub-modules
    String VENDOR_SUBMODULE = DOT + "vendor" + DOT;
    String USER_VENDOR_INDEX = USER + VENDOR_SUBMODULE + INDEX;
    String USER_VENDOR_CREATE = USER + VENDOR_SUBMODULE + CREATE;
    String USER_VENDOR_SHOW = USER + VENDOR_SUBMODULE + SHOW;
    String USER_VENDOR_UPDATE = USER + VENDOR_SUBMODULE + UPDATE;
    String USER_VENDOR_AUDITS = USER + VENDOR_SUBMODULE + "audits";
    String USER_VENDOR_ACCOUNTLOOKUP = USER + VENDOR_SUBMODULE + "account-lookup";
    String USER_VENDOR_LISTS = USER + VENDOR_SUBMODULE + "lists";
    String USER_VENDOR_SIGNUPACCOUNT = USER + VENDOR_SUBMODULE + "signUpAccount";
    String USER_VENDOR_SIGNUPACCOUNTOTP = USER + VENDOR_SUBMODULE + "signUpAccountOtp";
    String USER_VENDOR_SIGNUPACCOUNTVERIFY = USER + VENDOR_SUBMODULE + "signUpAccountVerify";
    String USER_VENDOR_CATEGORIES = USER + VENDOR_SUBMODULE + "categories";
    String USER_VENDOR_AGGREGATE = USER + VENDOR_SUBMODULE + "aggregate";
    String USER_VENDOR_TOGGLE = USER + VENDOR_SUBMODULE + TOGGLE;
    String USER_VENDOR_GOLIVE = USER + VENDOR_SUBMODULE + GOLIVE;

    //Security Question sub-modules
    String SECURITYQUESTION = DOT + "security-question" + DOT;
    String ADMINISTRATION_SECURITYQUESTION_INDEX = ADMINISTRATION + SECURITYQUESTION + INDEX;
    String ADMINISTRATION_SECURITYQUESTION_CREATE = ADMINISTRATION + SECURITYQUESTION + CREATE;
    String ADMINISTRATION_SECURITYQUESTION_UPDATE= ADMINISTRATION + SECURITYQUESTION + UPDATE;
    String ADMINISTRATION_SECURITYQUESTION_TOGGLE = ADMINISTRATION + SECURITYQUESTION + TOGGLE;
    String ADMINISTRATION_SECURITYQUESTION_SHOW = ADMINISTRATION + SECURITYQUESTION + SHOW;


    //Active Hour sub-modules
    String ACTIVEHOUR = DOT + "active-hour" + DOT;
    String ADMINISTRATION_ACTIVEHOUR_INDEX = ADMINISTRATION + ACTIVEHOUR + INDEX;
    String ADMINISTRATION_ACTIVEHOUR_CREATE = ADMINISTRATION + ACTIVEHOUR + CREATE;
    String ADMINISTRATION_ACTIVEHOUR_UPDATE= ADMINISTRATION + ACTIVEHOUR + UPDATE;
    String ADMINISTRATION_ACTIVEHOUR_TOGGLE = ADMINISTRATION + ACTIVEHOUR + TOGGLE;
    String ADMINISTRATION_ACTIVEHOUR_SHOW = ADMINISTRATION + ACTIVEHOUR + SHOW;

    //Branch sub-modules
    String BRANCH = DOT + "branch" + DOT;
    String ADMINISTRATION_BRANCH_INDEX = ADMINISTRATION + BRANCH + INDEX;
    String ADMINISTRATION_BRANCH_CREATE = ADMINISTRATION + BRANCH + CREATE;
    String ADMINISTRATION_BRANCH_UPDATE= ADMINISTRATION + BRANCH + UPDATE;
    String ADMINISTRATION_BRANCH_TOGGLE = ADMINISTRATION + BRANCH + TOGGLE;
    String ADMINISTRATION_BRANCH_LIST = ADMINISTRATION + BRANCH + LIST;
    String ADMINISTRATION_BRANCH_SHOW = ADMINISTRATION + BRANCH + SHOW;

    //Ticket sub-modules
    String TICKET = DOT + "ticket" + DOT;
    String ADMINISTRATION_TICKET_INDEX = ADMINISTRATION + TICKET + INDEX;
    String ADMINISTRATION_TICKET_CREATE = ADMINISTRATION + TICKET + CREATE;
    String ADMINISTRATION_TICKET_SERVICE_COMMENT = ADMINISTRATION + TICKET + "service-comment";
    String ADMINISTRATION_TICKET_COMMENTS = ADMINISTRATION + TICKET + "comments";
    String ADMINISTRATION_TICKET_UPDATE= ADMINISTRATION + TICKET + UPDATE;
    String ADMINISTRATION_TICKET_SHOW = ADMINISTRATION + TICKET + SHOW;


    //Public Holiday sub-modules
    String PUBLICHOLIDAY = DOT + "public-holiday" + DOT;
    String ADMINISTRATION_PUBLICHOLIDAY_INDEX = ADMINISTRATION + PUBLICHOLIDAY + INDEX;
    String ADMINISTRATION_PUBLICHOLIDAY_CREATE = ADMINISTRATION + PUBLICHOLIDAY + CREATE;
    String ADMINISTRATION_PUBLICHOLIDAY_UPDATE= ADMINISTRATION + PUBLICHOLIDAY + UPDATE;
    String ADMINISTRATION_PUBLICHOLIDAY_TOGGLE = ADMINISTRATION + PUBLICHOLIDAY + TOGGLE;
    String ADMINISTRATION_PUBLICHOLIDAY_DESTROY = ADMINISTRATION + PUBLICHOLIDAY + DESTROY;
    String ADMINISTRATION_PUBLICHOLIDAY_SHOW = ADMINISTRATION + PUBLICHOLIDAY + SHOW;

    //Vendor sub-modules
    /*String VENDOR_SUBMODULE = DOT + "vendor" + DOT;
    String USER_VENDOR_INDEX = USER + VENDOR_SUBMODULE + INDEX;
    String USER_VENDOR_CREATE= USER + VENDOR_SUBMODULE + CREATE;
    String USER_VENDOR_SHOW = USER + VENDOR_SUBMODULE + SHOW;
    String USER_VENDOR_UPDATE = USER + VENDOR_SUBMODULE + UPDATE;
    String USER_VENDOR_AUDITS = USER + VENDOR_SUBMODULE + "audits";
    String USER_VENDOR_ACCOUNTLOOKUP = USER + VENDOR_SUBMODULE + "account-lookup";
    String USER_VENDOR_LISTS = USER + VENDOR_SUBMODULE + "lists";
    String USER_VENDOR_SIGNUPACCOUNT = USER + VENDOR_SUBMODULE + "signUpAccount";
    String USER_VENDOR_SIGNUPACCOUNTOTP = USER + VENDOR_SUBMODULE + "signUpAccountOtp";
    String USER_VENDOR_SIGNUPACCOUNTVERIFY = USER + VENDOR_SUBMODULE + "signUpAccountVerify";
    String USER_VENDOR_CATEGORIES = USER + VENDOR_SUBMODULE + "categories";
    String USER_VENDOR_AGGREGATE = USER + VENDOR_SUBMODULE + "aggregate";
    String USER_VENDOR_TOGGLE = USER + VENDOR_SUBMODULE + TOGGLE;*/

    //Consumer sub-modules
    String CONSUMER_SUBMODULE= DOT + "consumer" + DOT;
    String USER_CONSUMER_INDEX = USER + CONSUMER_SUBMODULE + INDEX;
    String USER_CONSUMER_CREATEANONYMOUS = USER + CONSUMER_SUBMODULE+ "storeAnonymous";
    String USER_CONSUMER_SHOW = USER + CONSUMER_SUBMODULE + SHOW;
    String USER_CONSUMER_TOGGLE = USER + CONSUMER_SUBMODULE + TOGGLE;
    String USER_CONSUMER_PROFILE= USER + CONSUMER_SUBMODULE + "profile";
    String USER_CONSUMER_DELETE = USER + CONSUMER_SUBMODULE + DELETE;
    String USER_CONSUMER_SIGNUP = USER + CONSUMER_SUBMODULE + "signup";
    String USER_CONSUMER_SIGNUP_VERIFY = USER + CONSUMER_SUBMODULE + "signup" + DOT + "verify";
    
    //Session sub-modules
    String SESSION = DOT + "session" + DOT;
    String USER_SESSION_INDEX = USER + SESSION + INDEX;
    String USER_SESSION_LOGOUT = USER + SESSION + "logout";
    String USER_SESSION_SHOW = USER + SESSION + SHOW;

    //Currency sub-modules
    String USER_CURRENCY = USER + DOT + "currency";

    //Notification sub-modules
    String NOTIFICATION = DOT + "notification" + DOT;
    String USER_NOTIFICATION_INDEX = USER + NOTIFICATION + INDEX;
    String USER_NOTIFICATION_DETAILED = USER + NOTIFICATION + "detailed";
    String USER_NOTIFICATION_DESTROY = USER + NOTIFICATION + DESTROY;

    //Vendor Settings sub-modules
    String SETTINGS = DOT + "settings" + DOT;
    String VENDOR_SETTINGS_INDEX = VENDOR + SETTINGS + INDEX;
    String VENDOR_SETTINGS_SHOW = VENDOR + SETTINGS + SHOW;
    String VENDOR_SETTINGS_UPDATE = VENDOR + SETTINGS + UPDATE;
    String VENDOR_SETTINGS_RESET = VENDOR + SETTINGS + RESET;
    String VENDOR_SETTINGS_CATEGORY = VENDOR + SETTINGS + "category";
    String VENDOR_SETTINGS_CATEGORYLIST = VENDOR + SETTINGS + "category-list";
    String VENDOR_SETTINGS_CATEGORIES = VENDOR + SETTINGS + "categories";

    //Category sub-modules
    String SERVICE_CATEGORY_INDEX = SERVICE + CATEGORY + INDEX;
    String SERVICE_CATEGORY_LISTS = SERVICE + CATEGORY + "lists";
    String SERVICE_CATEGRORY_SHOW = SERVICE + CATEGORY + SHOW;
    String SERVICE_CATEGROY_DROPDOWN = SERVICE + CATEGORY + "dropDown";
    
    

}