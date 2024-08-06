package com.lcaohoanq.constant

import com.lcaohoanq.utils.EnvUtil

object GoogleAuthentication {
    @JvmField
	val GOOGLE_CLIENT_ID: String = EnvUtil.get("GOOGLE_CLIENT_ID")
    @JvmField
	val GOOGLE_CLIENT_SECRET: String = EnvUtil.get("GOOGLE_CLIENT_SECRET")
    @JvmField
	val GOOGLE_REDIRECT_URI: String = EnvUtil.get("GOOGLE_REDIRECT_URI")
    @JvmField
	val GOOGLE_GRANT_TYPE: String = EnvUtil.get("GOOGLE_GRANT_TYPE")
    @JvmField
	val GOOGLE_LINK_GET_TOKEN: String = EnvUtil.get("GOOGLE_LINK_GET_TOKEN")
    @JvmField
	val GOOGLE_LINK_GET_USER_INFO: String = EnvUtil.get("GOOGLE_LINK_GET_USER_INFO")
}
