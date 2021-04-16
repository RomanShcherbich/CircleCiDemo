package constants;

import utils.PropertyUtils;

public class Environment {

    public static final String BASE_URL = PropertyUtils.getSystemProperty("base_url");
    public static final String USERNAME = PropertyUtils.getSystemProperty("username");
    public static final String USERNAME_LOCKED = PropertyUtils.getSystemProperty("locked_out_user");
    public static final  String PASSWORD = PropertyUtils.getSystemProperty("password");


}
