package constants;

import utils.PropertyUtils;

public class Environment {

    public static final String BASE_URL = PropertyUtils.getEnv("base_url");
    public static final String USERNAME = PropertyUtils.getEnv("username");
    public static final String USERNAME_LOCKED = PropertyUtils.getEnv("locked_out_user");
    public static final  String PASSWORD = PropertyUtils.getEnv("password");


}
