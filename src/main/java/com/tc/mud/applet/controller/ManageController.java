package com.tc.mud.applet.controller;

 
 
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletResponse;

 
 
 
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.tc.core.utils.MyCodeUtil;
import com.tc.core.utils.MyKeyPariUtils;
import com.tc.core.web.responce.R;
import com.tc.mud.applet.dto.CompanyInfoDTO;
import com.tc.mud.applet.service.AppletUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@RestController
@RequestMapping("/manage")
@Api(tags = "6.0后台管理")
@SuppressWarnings("unchecked")
public class ManageController {
	
	@Autowired
	private AppletUserService userService;
	
	 
	
	
	@ApiOperation("自定义促销劵")
	@RequestMapping(value = "/test",method = RequestMethod.GET)
	public R<String> hello(HttpServletResponse response) throws Exception {
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("2", "rCluoaq4");
		map.put("2", "rCluoaq4");
		map.put("2", "rCluoaq4");
		map.put("2", "1596783772");
		map.put("2", "TL7QhaQmeHSA6mWXCmu-eXyMXCYXK316B7eNPrNGwPv7DQLpI9gFuYxVYqJEqmjU8atVgOvcfuP4AcHZOLG6Swu5l0xgDu1PtAF9/bnP4TmcyH-nrCMCL21okxtGJIIWCG/pJp1vDMpSJqqxqA/-Y5BCS7KgSfmQJ9O6i/t/GT8=");
		String url =map.toString();
		byte[] fimalByte = MyCodeUtil.generateQRCodeToByte(url, "00520");
		response.getOutputStream().write(fimalByte);
		return R.success().setData("hello !!!");
	}
	
	@ApiOperation("公司列表")
	@ApiOperationSupport(order = 1)
	@RequestMapping(value="/query_company_manage_list",method = RequestMethod.POST)
	public R<IPage<CompanyInfoDTO>> queryBusinessInfoList(
			@ApiParam(name="pageNum",value="页数",required=true)@RequestParam Integer pageNum,
			@ApiParam(name="pageSize",value="行数",required=true)@RequestParam Integer pageSize){
		IPage<CompanyInfoDTO> iPage = userService.queryManageCompanyList(pageNum, pageSize);
		return R.success().setData(iPage);
	}
//	
//	@ApiOperation("查看企业信息(审核员查看)")
//	@ApiOperationSupport(order = 2)
//	@RequestMapping(value="/manage/queryBusinessInfoByBusinessId",method = RequestMethod.POST)
//	public R<BusinessInfoDTO> queryBusinessInfo(
//			@ApiParam(name="businessId",value="企业Id",required=true)@RequestParam String businessId){
//		BusinessInfoDTO businessInfoDTO=userService.queryBusinessInfo(businessId);
//		return R.success().setData(businessInfoDTO);
//	}
//	
//	@ApiOperation("企业审核管理")
//	@ApiOperationSupport(order = 3)
//	@RequestMapping(value="/manage/businessManage",method = RequestMethod.POST)
//	public R<Void> businessManage(
//			@ApiParam(name="businessId",value="商家ID",required=true)@RequestParam String businessId,
//			@ApiParam(name="status",value="1:通过,2拒绝",required=true)@RequestParam String status){
//		userService.updateBusinessStatus(businessId,status);
//		return R.success();
//	}
//	
 
 
	
	
	
}
