package com.practice_02.junit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.practice_01.support.ejb.IBasicDataPreparation;
import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.remote.DeleteObjectRemote;
import com.tool.EjbClient;

class U07_DeleteObject {

	static IBasicDataPreparation dataprepare;
	static DeleteObjectRemote<Area> areaDAO;
	Area a = new Area();
	
	@BeforeEach
	void setUp() throws Exception {
		dataprepare = (IBasicDataPreparation) EjbClient.factory("BasicDataPreparation");
		dataprepare.practice_02_initinalData();
		areaDAO = EjbClient.factory("Q07_DeleteObject");
	}
	
	
	/**
	 * 7-1
	 * 完成 TranningProject.com.practice_02.ejb.Q07_DeleteObject 方法 deleteByEntityManager
	 * 使用 EntityManager 刪除資料庫中指定Area資料
	 * 指定目標: id為zone1 的Area
	 */
	@Test
	void testDeleteByEntityManager() throws Exception {
		Area a = new Area("zone1");
		Integer data = areaDAO.deleteByEntityManager(a);
		Assert.assertEquals(0, data, 0.001);
	}

	
	/**
	 * 7-2
	 * 完成 TranningProject.com.practice_02.ejb.Q07_DeleteObject 方法 deleteWithNativeSQL
	 * 使用 NativeSQL 刪除資料庫中指定Area資料
	 * 指定目標: category為WMS 的Area
	 * Note: 可能有多筆
	 */
	@Test
	void testDeleteWithNativeSQL() throws Exception {
		Area a = new Area();
		a.setCategory(AreaType.WMS);
		
		Integer data = areaDAO.deleteWithNativeSQL(a);
		Assert.assertEquals(0, data, 0.001);
		
	}
	
	/**
	 * 7-3
	 * 完成 TranningProject.com.practice_02.ejb.Q07_DeleteObject 方法 deleteWithCriteria
	 * 使用 Criteria 刪除資料庫中指定Area資料
	 * 指定目標: name為AAA 的Area
	 * Note: 可能有多筆
	 */
	@Test
	void testDeleteWithCriteria() throws Exception {
		Area a = new Area();
		a.setName("AAA");
		
		Integer data = areaDAO.deleteWithCriteria(a);
		Assert.assertEquals(0, data, 0.001);
	}

	/**
	 * 7-4
	 * 完成 TranningProject.com.practice_02.ejb.Q07_DeleteObject 方法 deleteWithJPQL
	 * 使用 JPQL 刪除資料庫中指定Area資料
	 * 指定目標: category為APS 的Area
	 * Note: 可能有多筆
	 */
	@Test
	void testDeleteWithJPQL() throws Exception {
		Area a = new Area();
		a.setCategory(AreaType.APS);
		
		Integer data = areaDAO.deleteWithJPQL(a);
		Assert.assertEquals(0, data, 0.001);
	}

	
	@AfterEach
	void reset() throws Exception {
		dataprepare.practice_02_resetData();
	}
}
