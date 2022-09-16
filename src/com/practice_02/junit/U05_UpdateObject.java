package com.practice_02.junit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.practice_01.support.ejb.IBasicDataPreparation;
import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.remote.UpdateObjectRemote;
import com.tool.EjbClient;

@TestMethodOrder(OrderAnnotation.class) 
class U05_UpdateObject {

	static IBasicDataPreparation dataprepare;
	static UpdateObjectRemote<Area> areaDAO;
	
	
	@BeforeAll
	static void setUp() throws Exception {
		dataprepare = (IBasicDataPreparation) EjbClient.factory("BasicDataPreparation");
		dataprepare.practice_02_initinalData();
		areaDAO = EjbClient.factory("Q05_UpdateObject");
	}
	
	
	/**
	 * 5-1
	 * 完成 TranningProject.com.practice_02.ejb.Q05_UpdateObject 方法 updateByEntityManager
	 * 使用 EntityManager 更新資料庫中指定Area資料
	 * 指定目標: id為zone1 的Area
	 * 修改條件: name變更為XXX
	 */
	@Test
	@Order(1)
	void testUpdateByEntityManager() throws Exception {
		Area a = new Area("zone1");
		Area data = areaDAO.updateByEntityManager(a);

		Assert.assertEquals("zone1", data.getID());
		Assert.assertEquals("XXX", data.getName());
		Assert.assertEquals(AreaType.APS, data.getCategory());
	}

	/**
	 * 5-2
	 * 完成 TranningProject.com.practice_02.ejb.Q05_UpdateObject 方法 updateWithNativeSQL
	 * 使用 NativeSQL 更新資料庫中指定Area資料
	 * 指定目標: category為WMS 的Area
	 * 修改條件: name變更為ZZZ
	 * Note: 可能有多筆
	 */
	@Test
	@Order(2)
	void testUpdateWithNativeSQL() throws Exception {
		Area a = new Area();
		a.setCategory(AreaType.WMS);
		
		Integer data = areaDAO.updateWithNativeSQL(a);
		Assert.assertEquals(2, data, 0.001);
	}

	/**
	 * 5-3
	 * 完成 TranningProject.com.practice_02.ejb.Q05_UpdateObject 方法 updateWithCriteria
	 * 使用 Criteria 更新資料庫中指定Area資料
	 * 指定目標: name為ZZZ 的Area
	 * 修改條件: category變更為APS
	 * Note: 可能有多筆
	 */
	@Test
	@Order(3)
	void testUpdateWithCriteria() throws Exception {
		Area a = new Area();
		a.setName("ZZZ");
		
		Integer data = areaDAO.updateWithCriteria(a);
		Assert.assertEquals(3, data, 0.001);
	}

	/**
	 * 5-4
	 * 完成 TranningProject.com.practice_02.ejb.Q05_UpdateObject 方法 updateWithJPQL
	 * 使用 JPQL 更新資料庫中指定Area資料
	 * 指定目標: category為APS 的Area
	 * 修改條件: name變更為AAT
	 * Note: 可能有多筆
	 */
	@Test
	@Order(4)
	void testUpdateWithJPQL() throws Exception {
		Area a = new Area();
		a.setCategory(AreaType.APS);
		
		Integer data = areaDAO.updateWithJPQL(a);
		Assert.assertEquals(3, data, 0.00);
	}
	
	@AfterAll
	static void reset() throws Exception {
		dataprepare.practice_02_resetData();
	}
}
