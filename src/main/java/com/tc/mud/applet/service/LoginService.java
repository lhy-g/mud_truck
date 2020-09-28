package com.tc.mud.applet.service;

import com.tc.core.web.responce.JsCode2Session;
import com.tc.core.web.responce.SynInfoFormDTO;
import com.tc.core.web.responce.SynInfoVO;
import com.tc.mud.applet.dto.UserInfo;

public interface LoginService {
	
	/**获取微信会话信息*/
	JsCode2Session getJscode2session(String jsCode);

	/**同步用户信息*/
	SynInfoVO synInfo(SynInfoFormDTO synInfoFormDto);
	
}
