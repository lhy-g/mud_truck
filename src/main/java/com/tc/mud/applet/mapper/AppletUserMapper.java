package com.tc.mud.applet.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc.mud.applet.dto.CompanyInfoDTO;
import com.tc.mud.applet.dto.MudTruckInfoDTO;
import com.tc.mud.applet.dto.MudYardHeadDTO;
import com.tc.mud.applet.dto.MudYardTailDTO;
import com.tc.mud.applet.dto.UserInfo;

public interface AppletUserMapper {

	/** 保存公司信息*/
	void saveCompanyInfo(CompanyInfoDTO companyInfo);

	/** 成员申请*/
	void addMemberApply(String companyId, String type,String userId);

	/** 公司搜索 */
	IPage<Map<String, String>> searchCompany(Page<Map<String, String>> page,String userId);

	/** 根据公司ID获取公司信息*/
	CompanyInfoDTO getCompanyInfoByCompanyId(String companyId);

	/** 查看公司成员 **/
	IPage<Map<String, String>> queryCompanyMember(Page<Map<String, String>> page, String status,String bossId);

	/** 更新成员状态 */
	void updateMemberStatusByUserId(String bossId, String memberId, String status);

	/** 获取用户私钥*/
	String getUserPrivateKey(String userId);
	
	/** 获取用户公钥*/
	String getUserPublicKey(String unionId);

	/** 添加密钥对*/
	void addUserKeyPari(String publicKey, String privateKey, String userId);

	/** 获取用户信息*/
	UserInfo getUserInfoByUnionId(String unionId);

	/**同步用户信息*/
	int saveUserInfo(UserInfo userInfo);

	/** 获取用户公司ID*/
	String getCompanyIdByUserId(String userId);

	/** 保存泥头场*/
	void saveMudYardHeadInfo(MudYardHeadDTO mudYardHeadDTO);
	
	/** 保存泥尾场*/
	void saveMudYardTailInfo(MudYardTailDTO mudYardTailDTO);

	/** 查看泥头票列表*/
	IPage<Map<String, String>> queryMudYardHead(Page<Map<String, String>> page,String companyId);
	
	/** 查看泥尾票列表*/
	IPage<Map<String, String>> queryMudYardTail(Page<Map<String, String>> page,String companyId);

	/** 获取泥头场信息  */
	MudYardHeadDTO getMudYardHeadInfo(String yardId);
	
	/** 获取泥尾场信息  */
	MudYardTailDTO getMudYardTailInfo(String yardId);
	 
	/** 获取老板ID */
	String getBossIdByMemberId(String memberId);

	/**  */
	Integer queryCountCompanyByUserId(String userId);

	/** 查询用户身份*/
	List<Map<String, String>> queryUserTypeByUserId(String userId);

	
	List<Map<String, String>> queryBaseDataUserType(String type);

	/** 查看所有泥场*/
	IPage<Map<String, String>> queryAllMudYardHead(Page<Map<String, String>> page, String name);

	/** 查询所有泥尾场*/
	IPage<Map<String, String>> queryAllMudYardTail(Page<Map<String, String>> page, String name);

	/** 公司成员添加老板*/
	void addCompanyBoss(String type, String userId);

	 

}
