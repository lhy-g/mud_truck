//package com.tc.mud.applet.service.impl;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.tc.core.utils.DateUtils;
//import com.tc.core.utils.RedisUtil;
//import com.tc.core.utils.UserContextHolder;
//import com.tc.mud.applet.dto.MudTicketInfoDTO;
//import com.tc.mud.applet.dto.MudTruckInfoDTO;
//import com.tc.mud.applet.dto.dto.MudHeadTranDTO;
//import com.tc.mud.applet.dto.dto.MudTailInfoDTO;
//import com.tc.mud.applet.dto.dto.MudTailTranDTO;
//import com.tc.mud.applet.dto.dto.MudTruckApplyDTO;
//import com.tc.mud.applet.mapper.MudTicketMapper;
//import com.tc.mud.applet.service.CertService;
//import com.tc.mud.applet.service.MudTicketService;
//
//
// 
//
//@Service
//public class MudTicketServiceImpl implements MudTicketService{
//	@Autowired
//    private MudTicketMapper mudMapper;
// 
//	@Autowired
//	private CertService certService;
//	
//	@Autowired
//	private RedisUtil redisService;
//
//	@Override
//	public void saveMudHeadInfo(MudTicketInfoDTO mudHeadInfo) throws Exception {
//		// if(StringUtils.isEmpty(mudHeadInfo.getHeadId())) {
//			// mudHeadInfo.setHeadId("MH"+DateUtils.getGuid());
//		// }
//		 mudHeadInfo.setCompanyId(UserContextHolder.getCompanyId());
//		 if("1".equals(mudHeadInfo.getStatus())) {
//			 mudHeadInfo.setStatus(null);
//			 //mudHeadInfo
//			 List<String> list = new ArrayList<String>();
//			// list.add(mudHeadInfo.getHeadId());
//			 list.add(mudHeadInfo.getCompanyId());
//		//	 list.add(mudHeadInfo.getItemName());
//			 certService.sign(mudHeadInfo, UserContextHolder.getUserId());
//		 }
//		 mudMapper.saveMudHeadInfo(mudHeadInfo);
//	}
//
//	@Override
//	public void saveMudTailInfo(MudTailInfoDTO mudTailInfo) {
//		if(StringUtils.isEmpty(mudTailInfo.getTailId())) {
//			mudTailInfo.setTailId("MT"+DateUtils.getGuid());
//		 }
//		 mudMapper.saveMudTailInfo(mudTailInfo);
//		
//	}
//
//	@Override
//	public MudTicketInfoDTO getMudHeadInfoByHeadId(String headId) {
//		 
//		return mudMapper.getMudHeadInfoByHeadId(headId);
//	}
//
//	@Override
//	public MudTailInfoDTO getMudTailInfoByTailId(String tailId) {
//		return mudMapper.getMudTailInfoByTailId(tailId);
//	}
//
//	@Override
//	public IPage<MudTailInfoDTO> queryMudTail(Integer size, Integer current) {
//		Page<MudTailInfoDTO> page = new Page<MudTailInfoDTO>(current, size);
//		IPage<MudTailInfoDTO> ipage = mudMapper.queryMudTailList(page);
//		return ipage;
//	}
//
//	@Override
//	public IPage<MudTicketInfoDTO> queryMudHead(Integer current, Integer size) {
//		Page<MudTicketInfoDTO> page = new Page<MudTicketInfoDTO>(current,size);
//		IPage<MudTicketInfoDTO> ipage = mudMapper.queryMudHeadList(page);
//		return ipage;
//	}
//
//	@Override
//	public MudTailInfoDTO addMudTailTran(String tailId, String companyId) {
//		mudMapper.addMudTailTran(tailId,companyId);
//		return null;
//	}
//
//	@Override
//	public MudTicketInfoDTO addMudHeadTran(String headId, String companyId) {
//		mudMapper.addMudHeadTran(headId,companyId);
//		return null;
//	}
//
//	@Override
//	public IPage<MudTicketInfoDTO> queryCompanyMudHeadTranList(Integer size, Integer current,
//			String companyId, String status) {
//		Page<MudTicketInfoDTO> page = new Page<MudTicketInfoDTO>(current,size);
//		IPage<MudTicketInfoDTO> ipage = mudMapper.queryCompanyMudHeadTranList(page,companyId,status);
//		return ipage;
//		 
//	}
//
//	@Override
//	public IPage<MudTailInfoDTO> queryCompanyMudTailTranList(Integer size, Integer current,
//			String companyId, String status) {
//		Page<MudTailInfoDTO> page = new Page<MudTailInfoDTO>(current,size);
//		IPage<MudTailInfoDTO> ipage = mudMapper.queryCompanyMudTailTranList(page,companyId,status);
//		return ipage;
//	}
//
//	@Override
//	public IPage<MudTicketInfoDTO> queryHeadByCompanyId(Integer current, Integer size, String companyId) {
//		Page<MudTicketInfoDTO> page = new Page<MudTicketInfoDTO>(current,size);
//		IPage<MudTicketInfoDTO> ipage = mudMapper.queryMudHeadByCompanyId(page,companyId);
//		return ipage;
//	}
//
//	@Override
//	public IPage<MudTailInfoDTO> queryTailByCompanyId(Integer current, Integer size, String companyId) {
//		Page<MudTailInfoDTO> page = new Page<MudTailInfoDTO>(current,size);
//		IPage<MudTailInfoDTO> ipage = mudMapper.queryTailByCompanyId(page,companyId);
//		return ipage;
//	}
//
//	@Override
//	public IPage<MudTailTranDTO> queryMudTailTranList(Integer size, Integer current, String tailId,
//			String status) {
//		Page<MudTailTranDTO> page = new Page<MudTailTranDTO>(current,size);
//		IPage<MudTailTranDTO> ipage = mudMapper.queryMudTailTranList(page,tailId,status);
//		return ipage;
//	}
//
//	@Override
//	public IPage<MudHeadTranDTO> queryMudHeadTranList(Integer size, Integer current, String headId, String status) {
//		Page<MudHeadTranDTO> page = new Page<MudHeadTranDTO>(current,size);
//		IPage<MudHeadTranDTO> ipage = mudMapper.queryMudHeadTranList(page,headId,status);
//		return ipage;
//		 
//	}
//
//	@Override
//	public void mudHeadTranManage(String headId, String companyId, String status) {
//		mudMapper.updateMudHeadTranStatus(headId,companyId,status);
//	}
//
//	@Override
//	public void mudTailTranManage(String tailId, String companyId, String status) {
//		 
//		mudMapper.updateMudTailTranStatus(tailId,companyId,status);
//	}
//
//	@Override
//	public void updateMudTailStatus(String tailId, String status) {
//		mudMapper.updateMudTailStatus(tailId,status);
//	}
//
//	@Override
//	public void updateMudHeadStatus(String headId, String status) {
//		mudMapper.updateMudHeadStatus(headId,status);
//		
//	}
//
//	@Override
//	public void addMudTruckInfo(MudTruckInfoDTO mudTruckInfoDto) {
//		mudMapper.addMudTruckInfo(mudTruckInfoDto);
//	}
//
//	@Override
//	public void updateReleaseInfoByMudId(MudTruckInfoDTO mudTruckInfoDto) {
//		mudMapper.updateReleaseInfoByMudId(mudTruckInfoDto);
//	}
//
//	@Override
//	public void updateReceiveInfoByMudId(MudTruckInfoDTO mudTruckInfoDto) {
//		mudMapper.updateReceiveInfoByMudId(mudTruckInfoDto);
//	}
//
//	@Override
//	public void updateMudTruckStatus(String mudId, String status) {
//		mudMapper.updateMudTruckStatus(mudId,status);
//	}
//
//	@Override
//	public MudTruckInfoDTO queryMudTruckInfoByMudId(String mudId) {
//		return mudMapper.queryMudTruckInfoByMudId(mudId);
//	}
//
//	@Override
//	public MudTruckInfoDTO queryMudHeadStatusByHeadId(String headId) {
//		return mudMapper.queryMudHeadStatusByHeadId(headId);
//	}
//
//	@Override
//	public void addMudTruckApply(MudTruckApplyDTO mudTruckApplyDTO) {
//		mudMapper.addMudTruckApply(mudTruckApplyDTO);
//	}
//
//	@Override
//	public IPage<MudTruckApplyDTO> queryMudTrucKApplyStatus(Integer size, Integer current, String date, String userId) {
//		Page<MudTruckApplyDTO> page = new Page<MudTruckApplyDTO>(current,size);
//		IPage<MudTruckApplyDTO> ipage = mudMapper.queryMudTrucKApplyStatus(page,date,userId);
//		return ipage;
//	}
//
//	@Override
//	public IPage<MudTruckApplyDTO> queryFinishedDrivingLog(Integer size, Integer current, String date, String userId) {
//		Page<MudTruckApplyDTO> page = new Page<MudTruckApplyDTO>(current,size);
//		IPage<MudTruckApplyDTO> ipage = mudMapper.queryFinishedDrivingLog(page,date,userId);
//		return ipage;
//	}
//
//	@Override
//	public IPage<MudTruckApplyDTO> queryUnfinishedDrivingLog(Integer size, Integer current, String date,
//			String userId) {
//		Page<MudTruckApplyDTO> page = new Page<MudTruckApplyDTO>(current,size);
//		IPage<MudTruckApplyDTO> ipage = mudMapper.queryUnfinishedDrivingLog(page,date,userId);
//		return ipage;
//	}
//
//	 
//
//}
