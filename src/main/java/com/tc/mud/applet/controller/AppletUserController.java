package com.tc.mud.applet.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tc.core.utils.DateUtils;
import com.tc.core.utils.JwtUtils;
import com.tc.core.utils.UserContextHolder;
import com.tc.core.web.responce.R;
import com.tc.core.web.responce.SynInfoVO;
import com.tc.core.web.responce.UserVO;
import com.tc.mud.applet.dto.CompanyInfoDTO;
import com.tc.mud.applet.dto.MudYardHeadDTO;
import com.tc.mud.applet.dto.MudYardTailDTO;
import com.tc.mud.applet.service.AppletUserService;
 

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "2.0用户相关接口")
@RequestMapping("/applet/user/")
@SuppressWarnings("unchecked")
public class AppletUserController {

	@Autowired
	private AppletUserService userService;
	
	
	@ApiOperation("用户类型基础数据")
	@ApiOperationSupport(order = 1)
    @RequestMapping(value="/query_user_type",method = RequestMethod.GET)
    public R<Map<String,String>> userType() {
    		Map<String,String> map = new LinkedHashMap<String, String>();
    		map.put("mb", "泥场老板");
    		map.put("mg", "泥场总经理");
    		map.put("mm", "泥场经理");
    		map.put("ms", "泥场员工");
    		map.put("mt", "泥场出纳");
    		map.put("tm", "运输队老板");
    		map.put("tt", "运输队出纳");
    		map.put("tm", "运输队经理");
    		map.put("td", "运输队司机");
    		return R.success().setData(map);
    }
	
	@ApiOperation("注册公司")
	@ApiOperationSupport(order = 1)
    @RequestMapping(value="/save_company_info",method = RequestMethod.POST)
    public R<Void> saveCompanyInfo(@RequestBody CompanyInfoDTO companyInfo) {
    	try {
    		if(StringUtils.isEmpty(companyInfo.getCompanyId())) {
    			companyInfo.setCompanyId("CI"+DateUtils.getGuid());
    		}
    		companyInfo.setStatus("1");
    		userService.saveCompanyInfo(companyInfo);
    		return R.success().setData(null);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("判断是否可以切换身份")
	@ApiOperationSupport(order = 1)
    @RequestMapping(value="/has_user_type",method = RequestMethod.GET)
    public R<Void> hasUserType() {
    	try {
    		//int count = userService.queryCountCompanyByUserId(UserContextHolder.getUserId());
    		List<Map<String,String>> list = userService.queryUserTypeByUserId(UserContextHolder.getUserId());
    		return R.success().setData(list);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	@ApiOperation("身份切换")
	@ApiOperationSupport(order = 2)
	@RequestMapping(value = "/change_user_type", method = RequestMethod.GET)
	public R<String> changeUserType(
			@ApiParam( value = "切换身份类型", required = true) @RequestParam String type) {
		try {
			UserVO userVo = UserContextHolder.get();
			if (null == userVo) {
				return null;
			}
			//userService.changeUserType(userVo.getUnionId(), type);
			userVo.setType(type);
			return R.success().setData(SynInfoVO.builder().token(JwtUtils.generateToken(userVo)).build());
		} catch (Exception e) {
			return R.fail();
		}
	}
	
	@ApiOperation("搜索公司")
	@ApiOperationSupport(order = 2)
    @RequestMapping(value="/search_company",method = RequestMethod.GET)
    public R<Void> searchCompany(
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    	try {
    		IPage<Map<String,String>> result = userService.searchCompany(size,current,UserContextHolder.getUserId());
    		return R.success().setData(result);
		} catch (Exception e) {
			log.error("搜索公司异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("获取公司信息")
	@ApiOperationSupport(order = 3)
    @RequestMapping(value="/get_company_info",method = RequestMethod.GET)
    public R<Void> getCompanyInfo(
    		@ApiParam(value="公司Id",required=true) @RequestParam String companyId) {
    	try {
    		CompanyInfoDTO  result = userService.getCompanyInfoByCompanyId(companyId);
    		return R.success().setData(result);
		} catch (Exception e) {
			log.error("获取公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("申请成为公司成员")
	@ApiOperationSupport(order = 4)
    @RequestMapping(value="/add_company_member",method = RequestMethod.GET)
    public R<Void> addMemberApply(
    		@ApiParam(value = "泥头票ID",required = true) @RequestParam String companyId,
    		@ApiParam(value = "类型(2:经理;3:员工;4:驾驶员)",required = true) @RequestParam String type) {
    	try {
    		userService.addMemberApply(companyId,type,UserContextHolder.getUserId());
    		return R.success().setData(null);
		} catch (Exception e) {
			log.error("申请成为公司成员异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("查看公司成员")
	@ApiOperationSupport(order = 5)
    @RequestMapping(value="/query_company_member",method = RequestMethod.GET)
    public R<Void> queryCompanyMember(
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
    		@ApiParam(value = "类型(0:申请人员;1:在职人员)",required = true) @RequestParam String status) {
    	try {
    		IPage<Map<String,String>> result = userService.queryCompanyMember(size,current,status,UserContextHolder.getUserId());
    		return R.success().setData(result);
		} catch (Exception e) {
			log.error("查看公司成员异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("成员管理")
	@ApiOperationSupport(order = 6)
    @RequestMapping(value="/member_manage",method = RequestMethod.GET)
    public R<Void> memberManage(
    		@ApiParam(value = "用户ID",required = true) @RequestParam String memberId,
    		@ApiParam(value = "1:同意,2:拒绝,3辞退",required = true) @RequestParam String status) {
    	try {
    		String bossId = UserContextHolder.getUserId();
    		userService.updateMemberStatusByUserId(bossId,memberId,status);
    		return R.success().setData(null);
		} catch (Exception e) {
			log.error("成员管理异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	 
	@ApiOperation("添加泥场头")
	@ApiOperationSupport(order = 11)
    @RequestMapping(value="/save_mud_yard_head_info",method = RequestMethod.POST)
    public R<Void> saveMudYardInfo(@RequestBody MudYardHeadDTO mudYardHeadDTO) {
    	try {
    		userService.saveMudYardHeadInfo(mudYardHeadDTO);
    		return R.success().setData(null);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("添加泥场尾")
	@ApiOperationSupport(order = 12)
    @RequestMapping(value="/save_mud_yard_tail_info",method = RequestMethod.POST)
    public R<Void> saveMudYardTailInfo(@RequestBody MudYardTailDTO mudYardTailDTO) {
    	try {
    		userService.saveMudYardTailInfo(mudYardTailDTO);
    		return R.success().setData(null);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("查询我的泥头场表列")
	@ApiOperationSupport(order = 13)
    @RequestMapping(value="/query_mud_yard_head",method = RequestMethod.GET)
    public R<MudYardHeadDTO> queryMudYardHeadInfo(
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    		//@ApiParam(value="状态(0:申请中,1:审核通过,2:)",required=true) @RequestParam Integer statusp
    	try {
    		String companyId = UserContextHolder.getCompanyId();
    		IPage<Map<String,String>>	mudYardHeadDTO = userService.queryMudYardHead(size,current,companyId);
    		return R.success().setData(mudYardHeadDTO);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	
	@ApiOperation("查询所有泥头场表列")
	@ApiOperationSupport(order = 14)
    @RequestMapping(value="/query_all_mud_yard_head",method = RequestMethod.GET)
    public R<MudYardHeadDTO> queryAllMudYardHead(
    		//@ApiParam(value="行数",required=true) @RequestParam String name,
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    	try {
    		IPage<Map<String,String>>	mudYardHeadDTO = userService.queryAllMudYardHead(size,current,null);
    		return R.success().setData(mudYardHeadDTO);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("查询所有泥头场表列")
	@ApiOperationSupport(order = 14)
    @RequestMapping(value="/query_all_mud_yard_tail",method = RequestMethod.GET)
    public R<MudYardHeadDTO> queryAllMudYardTail(
    	//	@ApiParam(value="行数",required=true) @RequestParam String name,
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    	try {
    		IPage<Map<String,String>>	mudYardHeadDTO = userService.queryAllMudYardTail(size,current,null);
    		return R.success().setData(mudYardHeadDTO);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	
	
	@ApiOperation("查询我的泥尾场表列")
	@ApiOperationSupport(order = 14)
    @RequestMapping(value="/query_mud_yard_tail",method = RequestMethod.GET)
    public R<MudYardHeadDTO> queryMudYardTail(
    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
    	try {
    		String companyId = UserContextHolder.getCompanyId();
    		IPage<Map<String,String>>	mudYardHeadDTO = userService.queryMudYardTail(size,current,companyId);
    		return R.success().setData(mudYardHeadDTO);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("获取泥头场信息")
	@ApiOperationSupport(order = 15)
    @RequestMapping(value="/get_mud_yard_head_info",method = RequestMethod.GET)
    public R<MudYardHeadDTO> getMudYardHeadInfo(String yardId) {
    	try {
    		MudYardHeadDTO	mudYardHeadDTO=userService.getMudYardHeadInfo(yardId);
    		return R.success().setData(mudYardHeadDTO);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
	
	@ApiOperation("获取泥尾场信息")
	@ApiOperationSupport(order = 16)
    @RequestMapping(value="/get_mud_yard_tail_info",method = RequestMethod.GET)
    public R<MudYardTailDTO> getMudYardTailInfo(String yardId) {
    	try {
    		MudYardTailDTO	mudYardTailDTO=userService.getMudYardTailInfo(yardId);
    		return R.success().setData(mudYardTailDTO);
		} catch (Exception e) {
			log.error("注册公司信息异常:",e);
			return R.fail().setMsg("系统异常!");
		}
    }
}
