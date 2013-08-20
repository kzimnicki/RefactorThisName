package cc.explain.server.api;

public enum LoginServiceResult {
	SUCCESS,
	
	USER_ALREADY_EXIST,
	
	BAD_LOGIN_OR_PASSWORD,

    PASSWORD_RESETED,

    PASSWORD_CHANGED,

    ACTIVATED,

    EMAIL_NOT_EXISTS,

    RESET_EMAIL_SENT,
    WRONG_RESET_KEY;
}
