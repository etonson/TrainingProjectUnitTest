package com.practice_02.junit;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.practice_01.support.ejb.IBasicDataPreparation;
import com.practice_02.support.model.Area;
import com.practice_02.support.model.Area.AreaType;
import com.practice_02.support.remote.FindObjectFromDatabaseRemote;
import com.tool.EjbClient;

class U01_FindObject {

	static IBasicDataPreparation dataprepare;
	static FindObjectFromDatabaseRemote<Area> areaDAO;
	Map<String, Object> filter = new HashMap<String, Object>();

	@BeforeAll
	static void setUp() throws Exception {
		dataprepare = (IBasicDataPreparation) EjbClient.factory("BasicDataPreparation");
		dataprepare.practice_02_initinalData();
		areaDAO = EjbClient.factory("Q01_FindObjectFromDatabase");
	}

	/**
	 * 完成 TranningProject.com.practice_02.ejb.Q01_FindObjectFromDatabase 方法
	 * findByEntityManager 使用 EntityManager 查詢指定Area id: zone1
	 */
	@Test
	void testFindByEntityManager() throws Exception {
		Area data = areaDAO.findByEntityManager("zone1");

		Assert.assertEquals("zone1", data.getID());
		Assert.assertEquals("AAA", data.getName());
		Assert.assertEquals(AreaType.APS, data.getCategory());
	}

	/**
	 * 完成 TranningProject.com.practice_02.ejb.Q01_FindObjectFromDatabase 方法
	 * findByFilterWithNativeSQL 使用 NativeSQL 查詢指定Area name: ABC note:
	 * name不是主鍵可能會有多筆資料
	 */
	@Test
	void testFindByFilterWithNativeSQL() throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", "ABC");
		List<Area> datas = areaDAO.findByFilterWithNativeSQL(filter);

		if (datas.isEmpty())
			fail("No Result");

		for (Area data : datas) {
			Assert.assertEquals("zone2", data.getID());
		}
	}

	/**
	 * 完成 TranningProject.com.practice_02.ejb.Q01_FindObjectFromDatabase 方法
	 * findByFilterWithCriteria 使用 Criteria 查詢指定Area name: ABC category: PHYSICAL
	 * note: name, category不是主鍵可能會有多筆資料
	 */
	@Test
	void testFindByFilterWithCriteria() throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", "AAA");
		filter.put("category", "WMS");
		List<Area> datas = areaDAO.findByFilterWithCriteria(filter);
		if (datas.isEmpty())
			fail("No Result");

		for (Area data : datas) {
			Assert.assertEquals("zone3", data.getID());
		}
	}

	/**
	 * 完成 TranningProject.com.practice_02.ejb.Q01_FindObjectFromDatabase 方法
	 * findByFilterJPQL 使用 JPQL 查詢指定Area name: ABC category: PHYSICAL note: name,
	 * category不是主鍵可能會有多筆資料
	 */
	@Test
	void testFindByFilterJPQL() throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", "AAA");
		filter.put("category", "PHYSICAL");
		List<Area> datas = areaDAO.findByFilterJPQL(filter);

		if (datas.isEmpty())
			fail("No Result");

		for (Area data : datas) {
			System.out.println(data.getID());
			Assert.assertEquals("zone4", data.getID());
		}
	}

	@AfterAll
	static void reset() throws Exception {
		dataprepare.practice_02_resetData();
	}
}
