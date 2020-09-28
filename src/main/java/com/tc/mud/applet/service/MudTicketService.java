package com.tc.mud.applet.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tc.mud.applet.dto.MudTicketInfoDTO;
import com.tc.mud.applet.dto.MudTruckInfoDTO;
import com.tc.mud.applet.dto.dto.MudHeadTranDTO;
import com.tc.mud.applet.dto.dto.MudTailInfoDTO;
import com.tc.mud.applet.dto.dto.MudTailTranDTO;

public interface MudTicketService {

	/** 保存泥头票信息*/
	void saveMudHeadInfo(MudTicketInfoDTO mudHeadInfo)throws Exception;

	/** 保存泥尾票信息*/
	void saveMudTailInfo(MudTailInfoDTO mudTailInfo);

	/** 获取泥头票信息*/
	MudTicketInfoDTO getMudHeadInfoByHeadId(String headId);

	/** 获取泥尾票信息*/
	MudTailInfoDTO getMudTailInfoByTailId(String tailId);

	/** 查看发布泥尾票列表*/
	IPage<MudTailInfoDTO> queryMudTail(Integer size, Integer current);

	/** 查看发布泥尾票列表*/
	IPage<MudTicketInfoDTO> queryMudHead(Integer current, Integer size);

	/** 添加泥尾票交易预约*/
	MudTailInfoDTO addMudTailTran(String tailId, String companyId);

	/** 添加泥头票交易预约*/
	MudTicketInfoDTO addMudHeadTran(String headId, String companyId);

	/** 查看公司的泥头票*/
	IPage<MudTicketInfoDTO> queryCompanyMudHeadTranList(Integer size, Integer current, String companyId,String status);

	/** 查看公司的泥尾票*/
	IPage<MudTailInfoDTO> queryCompanyMudTailTranList(Integer size, Integer current, String companyId,String status);

	/** 查看公司发行的泥头票*/
	IPage<MudTicketInfoDTO> queryHeadByCompanyId(Integer current, Integer size, String companyId);

	/** 查看公司发行的泥尾票*/
	IPage<MudTailInfoDTO> queryTailByCompanyId(Integer current, Integer size, String companyId);

	/** 查看泥尾票交易列表*/
	IPage<MudTailTranDTO> queryMudTailTranList(Integer size, Integer current, String tailId, String status);
	
	/** 查看泥尾票交易列表*/
	IPage<MudHeadTranDTO> queryMudHeadTranList(Integer size, Integer current, String headId, String status);

	/** 泥头票交易管理*/
	void mudHeadTranManage(String headId, String companyId, String status);

	/** 泥尾票交易管理*/
	void mudTailTranManage(String tailId, String companyId, String status);

	/** 更新泥尾票状态*/
	void updateMudTailStatus(String tailId, String status);

	/** 更新泥头票状态*/
	void updateMudHeadStatus(String headId, String status);

	/** 添加运泥车信息*/
	void addMudTruckInfo(MudTruckInfoDTO mudTruckInfoDto);

	/** 更新运泥车放行信息*/
	void updateReleaseInfoByMudId(MudTruckInfoDTO mudTruckInfoDto);

	/** 更新运泥车收纳信息*/
	void updateReceiveInfoByMudId(MudTruckInfoDTO mudTruckInfoDto);

	/** 更新运输状态*/
	void updateMudTruckStatus(String mudId, String status);

	/** 司机查看运输信息*/
	MudTruckInfoDTO queryMudTruckInfoByMudId(String mudId);

	/** 查看泥头场状态*/
	MudTruckInfoDTO queryMudHeadStatusByHeadId(String headId);

//	/** 司机添加泥票预约申请*/
//	void addMudTruckApply(MudTruckApplyDTO mudTruckApplyDTO);
//
//	/** 查看预约列表 */
//	IPage<MudTruckApplyDTO> queryMudTrucKApplyStatus(Integer size, Integer current, String date, String userId);
//
//	/** 驾驶员日志-已完成的运输预约*/
//	IPage<MudTruckApplyDTO> queryFinishedDrivingLog(Integer size, Integer current, String date, String userId);
//
//	/** 驾驶员日志--未完成的运输预约*/
//	IPage<MudTruckApplyDTO> queryUnfinishedDrivingLog(Integer size, Integer current, String date, String userId);

}
