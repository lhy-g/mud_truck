package com.tc.mud.applet.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tc.core.web.responce.SynInfoFormDTO;
import com.tc.core.web.responce.SynInfoVO;
import com.tc.mud.applet.dto.CompanyInfoDTO;
import com.tc.mud.applet.dto.MudYardHeadDTO;
import com.tc.mud.applet.dto.MudYardTailDTO;
import com.tc.mud.applet.dto.UserInfo;
 
 
 
 

public interface AppletUserService {

	/***/
	UserInfo getUserInfoByUnionId(String unionId);
	
	
	/** 查询用户私钥 */
	String getUserPrivateKey(String userId) throws Exception;

	/** 查询用户公钥 */
	String getUserPublicKey(String userId) throws Exception;
	 
	/**注册公司*/
	void saveCompanyInfo(CompanyInfoDTO companyInfo);

	/** 成员申请*/
	void addMemberApply(String companyId,String type ,String userId);

	/** 搜索公司*/
	IPage<Map<String, String>> searchCompany(Integer size, Integer current,String userId);

	/** 获取公司信息*/
	CompanyInfoDTO getCompanyInfoByCompanyId(String companyId);

	/** 查看公司成员*/
	IPage<Map<String, String>> queryCompanyMember(Integer size, Integer current,String status,String bossId);

	/** 更新成员状态 */
	void updateMemberStatusByUserId(String bossId,String memberId, String status);


	/**添加用户信息*/
	void saveUserInfo(UserInfo userInfo);

	/** 查看公司管理列表**/
	IPage<CompanyInfoDTO> queryManageCompanyList(Integer pageNum, Integer pageSize);

	/** 获取用户公司ID*/
	String getCompanyIdByUserId(String userId);

	/** 保存泥头场*/
	void saveMudYardHeadInfo(MudYardHeadDTO mudYardHeadDTO);
	
	/** 保存泥头场*/
	void saveMudYardTailInfo(MudYardTailDTO mudYardTailDTO);


	MudYardHeadDTO getMudYardHeadInfo(String yardId);

	MudYardTailDTO getMudYardTailInfo(String yardId);

	/** 查看泥头列表*/
	IPage<Map<String, String>> queryMudYardHead(Integer size, Integer current, String companyId);

	/** 查看泥尾列表*/
	IPage<Map<String, String>> queryMudYardTail(Integer size, Integer current, String companyId);

	/** 查询是否是公老板*/
	int queryCountCompanyByUserId(String userId);

	/** 查询用户身份*/
	List<Map<String, String>> queryUserTypeByUserId(String userId);


	/** 查看所有泥场*/
	IPage<Map<String, String>> queryAllMudYardHead(Integer size, Integer current, String name);

	/** 查看所有泥尾场*/
	IPage<Map<String, String>> queryAllMudYardTail(Integer size, Integer current, String name);

	
	

}
