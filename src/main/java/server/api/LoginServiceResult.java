package server.api;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum LoginServiceResult {
	SUCCESS,
	
	USER_ALREADY_EXIST,
	
	BAD_LOGIN_OR_PASSWORD;	
}
