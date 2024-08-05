package com.lcaohoanq.constant;

import com.lcaohoanq.utils.EnvUtil;

public class GoogleAuthentication {

	public static final String GOOGLE_CLIENT_ID = EnvUtil.get("GOOGLE_CLIENT_ID");
	public static final String GOOGLE_CLIENT_SECRET = EnvUtil.get("GOOGLE_CLIENT_SECRET");
	public static final String GOOGLE_REDIRECT_URI = EnvUtil.get("GOOGLE_REDIRECT_URI");
	public static final String GOOGLE_GRANT_TYPE = EnvUtil.get("GOOGLE_GRANT_TYPE");
	public static final String GOOGLE_LINK_GET_TOKEN = EnvUtil.get("GOOGLE_LINK_GET_TOKEN");
	public static final String GOOGLE_LINK_GET_USER_INFO = EnvUtil.get("GOOGLE_LINK_GET_USER_INFO");

}
