//package com.tc.mud.applet.controller;
//
// 
// 
// 
// 
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
//import com.tc.core.annotation.OpenMapping;
//import com.tc.core.exception.BaseException;
//import com.tc.core.utils.DateUtils;
//import com.tc.core.utils.MyCodeUtil;
//import com.tc.core.utils.MyFileUtils;
//import com.tc.core.utils.UserContextHolder;
//import com.tc.core.web.responce.JsCode2Session;
//import com.tc.core.web.responce.R;
//import com.tc.core.web.responce.SynInfoFormDTO;
//import com.tc.core.web.responce.SynInfoVO;
//import com.tc.core.web.responce.UserVO;
//import com.tc.mud.applet.dto.MudTicketInfoDTO;
//import com.tc.mud.applet.dto.MudTruckApplyDTO;
//import com.tc.mud.applet.dto.MudTruckInfoDTO;
//import com.tc.mud.applet.dto.dto.MudHeadTranDTO;
//import com.tc.mud.applet.dto.dto.MudTailInfoDTO;
//import com.tc.mud.applet.dto.dto.MudTailTranDTO;
// 
//import com.tc.mud.applet.service.LoginService;
//import com.tc.mud.applet.service.MudTicketService;
// 
// 
//
//@Slf4j
//@RestController
//@Api(tags = "3.0泥票相关接口")
//@RequestMapping("/applet/mud")
//@SuppressWarnings("unchecked")
//public class MudTicketController {
//	
//	@Autowired
//    private MudTicketService mudTickService;
//	
//	
//	@OpenMapping
//	@ApiOperation("自定义促销劵")
//	@RequestMapping(value = "/test",method = RequestMethod.GET)
//	public void hello(HttpServletResponse response) throws Exception {
//		byte[] fimalByte = MyCodeUtil.generateQRCodeToByte("6666", "1629");
//		// response.addHeader("Content-Type", "text/plain;charset=UTF-8");
//		response.addHeader("Content-Type", "image/jpg");
//		response.getOutputStream().write(fimalByte);
//	}
//	
//	
///********************************************泥头票发布********************************************************************/	
//    @ApiOperation("新增泥头票")
//    @ApiOperationSupport(order = 1)
//    @RequestMapping(value="/save_mud_head",method = RequestMethod.POST)
//    public R<Void> saveMudHead(@RequestBody MudTicketInfoDTO mudHeadInfo) {
//    	try {
//    		String filePath = mudHeadInfo.getImageNum();
//    		if(filePath.startsWith("https")) {
//    			filePath = filePath.substring(filePath.indexOf("upload/") + 7);
//    			mudHeadInfo.setImageNum(filePath);
//    		}
//    		mudTickService.saveMudHeadInfo(mudHeadInfo);
//    		return R.success().setData(null);
//		} catch (Exception e) {
//			log.error("新增泥头票信息异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    
//    
//    
//    @ApiOperation("新增泥尾票")
//    @ApiOperationSupport(order = 2)
//    @RequestMapping(value="/save_mud_tail",method = RequestMethod.POST)
//    public R<Void> saveMudTail(@RequestBody MudTailInfoDTO mudTailInfo) {
//    	try {
//    		mudTickService.saveMudTailInfo(mudTailInfo);
//    		return R.success().setData(null);
//		} catch (Exception e) {
//			log.error("新增泥尾票信息异常:",e);
//			return R.fail().setMsg(e.getMessage());
//		}
//    }
//    @ApiOperation("获取泥头票信息")
//    @ApiOperationSupport(order = 3)
//    @RequestMapping(value="/get_mud_head_info",method = RequestMethod.GET)
//    public R<Void> getMudHeadInfo(
//    		@ApiParam(value = "泥头票ID",required = true) @RequestParam String headId) {
//    	try {
//    		MudTicketInfoDTO result = mudTickService.getMudHeadInfoByHeadId(headId);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			if(e instanceof BaseException) {
//			}
//			log.error("获取泥头票信息异常:",e);
//			return R.fail().setMsg(e.getMessage());
//		}
//    }
// 
//    @ApiOperation("获取泥尾票信息")
//    @ApiOperationSupport(order = 4)
//    @RequestMapping(value="/get_mud_tail_info",method = RequestMethod.GET)
//    public R<Void> getMudTailInfo(
//    		@ApiParam(value = "泥尾票ID",required = true)  String tailId) {
//    	try {
//    		MudTailInfoDTO result = mudTickService.getMudTailInfoByTailId(tailId);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("获取泥尾票信息异常:",e);
//			return R.fail().setMsg(e.getMessage());
//		}
//    }
//    
//    @ApiOperation("泥尾票管理")
//    @ApiOperationSupport(order = 5)
//    @RequestMapping(value="/mud_tail_manage",method = RequestMethod.GET)
//    public R<Void> mudTailManage(
//    		@ApiParam(value = "泥尾票ID",required = true)  String tailId,
//    		@ApiParam(value = "1:发布;2:下架;3:删除;",required = true)  String status) {
//    	try {
//    		mudTickService.updateMudTailStatus(tailId,status);
//    		return R.success().setData(null);
//		} catch (Exception e) {
//			log.error("泥尾票管理异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("泥头票管理")
//    @ApiOperationSupport(order =6)
//    @RequestMapping(value="/mud_head_manage",method = RequestMethod.GET)
//    public R<Void> mudHeadManage(
//    		@ApiParam(value = "泥头票ID",required = true)  String headId,
//    		@ApiParam(value = "1:发布;2:下架;3:删除;",required = true)  String status) {
//    	try {
//    		mudTickService.updateMudHeadStatus(headId,status);
//    		return R.success().setData(null);
//		} catch (Exception e) {
//			log.error("泥头票管理异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看我[发行]的泥头票列表")
//    @ApiOperationSupport(order =7)
//    @RequestMapping(value="/query_edit_mud_head",method = RequestMethod.GET)
//    public R<Void> queryMudHeadByCompany(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current
//			) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		IPage<MudTicketInfoDTO> ipage = mudTickService.queryHeadByCompanyId(current,size,companyId);
//    		return R.success().setData(ipage);
//		} catch (Exception e) {
//			log.error("查看我发行的泥头票列表异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看我发行的泥尾票列表")
//    @ApiOperationSupport(order =8)
//    @RequestMapping(value="/query_edit_mud_tail",method = RequestMethod.GET)
//    public R<Void> queryMudTailByCompanyId(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		IPage<MudTailInfoDTO> ipage = mudTickService.queryTailByCompanyId(current,size,companyId);
//    		return R.success().setData(ipage);
//		} catch (Exception e) {
//			log.error("查看我发行的泥尾票列表异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看泥尾票交易列表")
//    @ApiOperationSupport(order =9)
//    @RequestMapping(value="/query_tran_mud_tail",method = RequestMethod.GET)
//    public R<Void> queryMudTailTranList(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
//    		@ApiParam(value="泥尾ID",required=true) @RequestParam String tailId,
//    		@ApiParam(value="状态(0:申请中,1:通过,2:拒绝,3:结束)",required=true) @RequestParam String status) {
//    	try {
//    		IPage<MudTailTranDTO> result = mudTickService.queryMudTailTranList(size,current,tailId,status);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("泥尾票交易列表异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看泥头票交易列表")
//    @ApiOperationSupport(order =10)
//    @RequestMapping(value="/query_tran_mud_head",method = RequestMethod.GET)
//    public R<Void> queryMudHeadTranList(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
//    		@ApiParam(value="泥尾ID",required=true) @RequestParam String headId,
//    		@ApiParam(value="状态(0:申请中,1:通过,2:拒绝,3:结束)",required=true) @RequestParam String status) {
//    	try {
//    		IPage<MudHeadTranDTO> result = mudTickService.queryMudHeadTranList(size,current,headId,status);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("查看泥头票交易列表异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("泥头票交易管理")
//    @ApiOperationSupport(order =11)
//    @RequestMapping(value="/mud_head_tran_manage",method = RequestMethod.GET)
//    public R<Void> mudHeadManage(
//    		@ApiParam(value="泥头票ID",required=true) @RequestParam String headId,
//    		@ApiParam(value="运输公司Id",required=true) @RequestParam String companyId,
//    		@ApiParam(value="状态(1:通过,2:拒绝,3:结束)",required=true) @RequestParam String status) {
//    	try {
//    		mudTickService.mudHeadTranManage(headId,companyId,status);
//    		return R.success();
//		} catch (Exception e) {
//			log.error("泥头票交易管理异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("泥尾票交易管理")
//    @ApiOperationSupport(order =12)
//    @RequestMapping(value="/mud_tail_tran_manage",method = RequestMethod.GET)
//    public R<Void> mudTailTranManage(
//    		@ApiParam(value="泥头票ID",required=true) @RequestParam String tailId,
//    		@ApiParam(value="运输公司Id",required=true) @RequestParam String companyId,
//    		@ApiParam(value="状态(1:通过,2:拒绝,3:结束)",required=true) @RequestParam String status) {
//    	try {
//    		mudTickService.mudTailTranManage(tailId,companyId,status);
//    		return R.success();
//		} catch (Exception e) {
//			log.error("泥尾票交易管理异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看所有泥尾票[发布]列表")
//    @ApiOperationSupport(order =21)
//    @RequestMapping(value="/query_all_mud_tail",method = RequestMethod.GET)
//    public R<Void> queryMudTail(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current
//			) {
//    	try {
//    		IPage<MudTailInfoDTO> ipage = mudTickService.queryMudTail(size,current);
//    		return R.success().setData(ipage);
//		} catch (Exception e) {
//			log.error("查看所有泥尾票发布列表异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看所有泥头票[发布]列表")
//    @ApiOperationSupport(order =22)
//    @RequestMapping(value="/query_all_mud_head",method = RequestMethod.GET)
//    public R<Void> queryMudHead(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current
//			) {
//    	try {
//    		IPage<MudTicketInfoDTO> ipage = mudTickService.queryMudHead(current,size);
//    		return R.success().setData(ipage);
//		} catch (Exception e) {
//			log.error("查看所有泥头票发布列表异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    
// /********************************************运输公司********************************************************************/	
//    @ApiOperation("预购泥尾票申请")
//    @RequestMapping(value="/add_mud_tail_tran",method = RequestMethod.GET)
//    public R<Void> addMudTailTran(
//    		@ApiParam(value = "泥尾票ID",required = true)  String tailId) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		MudTailInfoDTO result = mudTickService.addMudTailTran(tailId,companyId);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("预购泥尾票申请异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("预购泥头票申请")
//    @RequestMapping(value="/add_mud_head_tran",method = RequestMethod.GET)
//    public R<Void> addMudHeadTran(
//    		@ApiParam(value = "泥头票ID",required = true)  String headId) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		MudTicketInfoDTO result = mudTickService.addMudHeadTran(headId,companyId);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("预购泥头票申请异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看公司的泥头票")
//    @RequestMapping(value="/query_my_mud_head_tran",method = RequestMethod.GET)
//    public R<Void> queryCompanyMudHeadTranList(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
//    		@ApiParam(value="状态(0:申请中,1:通过,2:拒绝,3:结束)",required=true) @RequestParam String status
//    		) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		IPage<MudTicketInfoDTO> result = mudTickService.queryCompanyMudHeadTranList(size,current,companyId,status);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("查看公司的泥头票异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看公司的泥尾票")
//    @RequestMapping(value="/query_my_mud_tail_tran",method = RequestMethod.GET)
//    public R<Void> queryCompanyMudTailTranList(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
//    		@ApiParam(value="状态(0:申请中,1:通过,2:拒绝,3:结束)",required=true) @RequestParam String status
//    		) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		IPage<MudTailInfoDTO> result = mudTickService.queryCompanyMudTailTranList(size,current,companyId,status);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("查看公司的泥尾票异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("运输记录(已完成的运输信息)")
//    @RequestMapping(value="/query_drive_mud_truck_record",method = RequestMethod.GET)
//    public R<Void> queryDriveMudTruckRecord(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current) {
//    	try {
//    	    
//    	//	IPage<MudTruckInfoDTO> ipage = mudTickService.queryDriveMudTruckRecord(size,current,UserContextHolder.getUserId());
//    		return R.success();
//		} catch (Exception e) {
//			log.error("新增泥头票信息异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("司机添加泥漂预约申请")
//    @RequestMapping(value="/add_mud_truck_apply",method = RequestMethod.POST)
//    public R<Void> addMudTruckApply(@RequestBody MudTruckApplyDTO mudTruckApplyDTO) {
//    	try {
//    		mudTruckApplyDTO.setUserId(UserContextHolder.getUserId());
//    		//mudTickService.addMudTruckApply(mudTruckApplyDTO);
//    		return R.success();
//		} catch (Exception e) {
//			log.error("添加泥漂预约申请异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("查看泥票预约申请的列表")
//    @RequestMapping(value="/add_mud_truck_apply",method = RequestMethod.GET)
//    public R<Void> getMudTruckApplyStatus(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
//    		@ApiParam(value="日期",required=true) @RequestParam String date) {
//    	try {
//    		//IPage<MudTruckApplyDTO> data  = mudTickService.queryMudTrucKApplyStatus(size,current,date,UserContextHolder.getUserId());
//    		return R.success().setData(null);
//		} catch (Exception e) {
//			log.error("查看泥票预约申请的列表异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("驾驶员日志-已完成的运输预约")
//    @RequestMapping(value="/query_driving_log",method = RequestMethod.GET)
//    public R<Void> queryFinishedDrivingLog(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
//    		@ApiParam(value="日期",required=true) @RequestParam String date) {
//    	try {
//    		//IPage<MudTruckApplyDTO> data  = mudTickService.queryFinishedDrivingLog(size,current,date,UserContextHolder.getUserId());
//    		return R.success().setData(null);
//		} catch (Exception e) {
//			log.error("驾驶员日志-已完成的运输预约异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("驾驶员日志--未完成的运输预约")
//    @RequestMapping(value="/query_unfinished_driving_log",method = RequestMethod.GET)
//    public R<Void> queryUnfinishedDrivingLog(
//    		@ApiParam(value="行数",required=true) @RequestParam Integer size,
//    		@ApiParam(value="页数",required=true) @RequestParam Integer current,
//    		@ApiParam(value="日期",required=true) @RequestParam String date) {
//    	try {
//    		//IPage<MudTruckApplyDTO> data  = mudTickService.queryUnfinishedDrivingLog(size,current,date,UserContextHolder.getUserId());
//    		return R.success().setData(null);
//		} catch (Exception e) {
//			log.error("驾驶员日志--未完成的运输预约异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    
//    //开始运输  放行验证   收纳验证
//    @ApiOperation("运输--开始")
//    @RequestMapping(value="/add_mud_truck_info",method = RequestMethod.GET)
//    public R<Void> addMudTruckInfo(
//    		@ApiParam(value = "泥头票ID",required = true)  String headId,
//    		@ApiParam(value = "泥尾票ID")  String tailId) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		MudTruckInfoDTO mudTruckInfoDto = new MudTruckInfoDTO();
//    		mudTruckInfoDto.setDriverId(UserContextHolder.getUserId());
//    		mudTruckInfoDto.setHeadId(headId);
//    		mudTruckInfoDto.setTailId(tailId);
//    		mudTruckInfoDto.setMudId("MT"+DateUtils.getGuid());
//    		mudTruckInfoDto.setStatus("0");
//    		mudTickService.addMudTruckInfo(mudTruckInfoDto);
//    		return R.success();
//		} catch (Exception e) {
//			log.error("新增泥头票信息异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    //开始运输  放行验证   收纳验证
//    @ApiOperation("运输--查看运输信息")
//    @RequestMapping(value="/query_mud_truck_info",method = RequestMethod.GET)
//    public R<Void> queryMudTruckInfo(
//    		@ApiParam(value = "泥票ID",required = true)  String mudId) {
//    	try {
//    		MudTruckInfoDTO result = mudTickService.queryMudTruckInfoByMudId(mudId);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("新增泥头票信息异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("运输--查看泥头场状况")
//    @RequestMapping(value="/get_mud_head_status",method = RequestMethod.GET)
//    public R<Void> queryMudHeadInfo(
//    		@ApiParam(value = "泥票ID",required = true)  String headId) {
//    	try {
//    		MudTruckInfoDTO result = mudTickService.queryMudHeadStatusByHeadId(headId);
//    		return R.success().setData(result);
//		} catch (Exception e) {
//			log.error("新增泥头票信息异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    
//    
//    @ApiOperation("运输--状态更新")
//    @RequestMapping(value="/update_mud_truck_info",method = RequestMethod.GET)
//    public R<Void> updateMudTruckStatus(
//    		@ApiParam(value = "泥票ID",required = true)  String mudId,
//    		@ApiParam(value = "状态[0:等待装车,1:运输中,2:等待卸车,3:结束]",required = true)  String status) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		mudTickService.updateMudTruckStatus(mudId,status);
//    		return R.success();
//		} catch (Exception e) {
//			log.error("新增泥头票信息异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    
//    @ApiOperation("运输--放行验证")
//    @RequestMapping(value="/add_release_info",method = RequestMethod.GET)
//    public R<Void> addReleaseInfo(
//    		@ApiParam(value = "泥头票ID",required = true)  String headId,
//    		@ApiParam(value = "泥票ID",required = true)  String mudId) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		MudTruckInfoDTO mudTruckInfoDto = new MudTruckInfoDTO();
//    		mudTruckInfoDto.setMudId(mudId);
//    		mudTruckInfoDto.setHeadId(headId);
//    		mudTruckInfoDto.setReleaseId(UserContextHolder.getUserId());
//    		mudTruckInfoDto.setStatus("1");
//    		mudTickService.updateReleaseInfoByMudId(mudTruckInfoDto);
//    		return R.success();
//		} catch (Exception e) {
//			log.error("新增泥头票信息异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//    
//    @ApiOperation("运输--收纳验证")
//    @RequestMapping(value="/add_mud_receiver_info",method = RequestMethod.GET)
//    public R<Void> addMudReceiverInfo(
//    		@ApiParam(value = "泥头票ID",required = true)  String tailId,
//    		@ApiParam(value = "泥票ID",required = true)  String mudId) {
//    	try {
//    		String companyId = UserContextHolder.getCompanyId();
//    		MudTruckInfoDTO mudTruckInfoDto = new MudTruckInfoDTO();
//    		mudTruckInfoDto.setMudId(mudId);
//    		mudTruckInfoDto.setTailId(tailId);
//    		mudTruckInfoDto.setReceiverId(UserContextHolder.getUserId());
//    		mudTruckInfoDto.setStatus("2");
//    		mudTickService.updateReceiveInfoByMudId(mudTruckInfoDto);
//    		return R.success();
//		} catch (Exception e) {
//			log.error("新增泥头票信息异常:",e);
//			return R.fail().setMsg("系统异常!");
//		}
//    }
//}
