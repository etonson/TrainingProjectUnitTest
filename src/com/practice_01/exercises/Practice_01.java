package com.practice_01.exercises;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.practice_01.support.ejb.IBasicDataPreparation;
import com.tool.EjbClient;
import com.tool.JsoupWrapper;
import com.tool.JsoupWrapper.HTTPMethod;

public class Practice_01 {
	static JsoupWrapper jsoup;
	static IBasicDataPreparation dataprepare;

	@BeforeAll
	static void setUp() throws Exception {
		jsoup = new JsoupWrapper();
		dataprepare = (IBasicDataPreparation) EjbClient.factory("BasicDataPreparation");
		dataprepare.prepareBasicData();
	}

	@AfterAll
	static void afterAll() throws Exception {
		dataprepare.cleanData();
	}

	/**
	 * 範例1:
	 * 使用 GET 呼叫  API   
	 * @throws Exception
	 */
	@Test
	void example_GET01() throws Exception {

		JSONObject res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/example01", "",
				HTTPMethod.GET);
		assertEquals("success", res.get("status"));
	}

	/**
	 * 練習1:
	 *目的: 建立一個 使用GET的 Restful API
	 *說明: 修改 TrainingProject.src.com.practice_01.rest.Q00_HttpMethod 的   TestGet01 方法
	 * 使其可以被以下方法成功呼叫  
	 * @throws Exception
	 */
	@Test
	void exercise_GET01() throws Exception {
		JSONObject res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testget01", "",
				HTTPMethod.GET);
		assertEquals("success", res.get("status"));
	}

	/**
	 * 練習2:
	 *目的:	學習Restful GET 接收QueryParam 參數
	 *說明:	修改 TrainingProject.src.com.practice_01.rest.Q00_HttpMethod 的   TestGet02 方法
	 *使其可以接收一個 QueryParam Key為 name Value為Dog 然後將 Dog 封裝到 String res 
	 * @throws Exception
	 */
	@Test
	void exercise_GET02() throws Exception {
		JSONObject res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testget02?name=Dog", "",
				HTTPMethod.GET);
		assertEquals("Dog", res.get("msg"));
	}

	/**
	 * 練習3:
	 *目的:	學習Restful GET 接收 PathParam 參數
	 *說明:	修改 TrainingProject.src.com.practice_01.rest.Q00_HttpMethod 的   TestGet03 方法
	 *使其可以接收兩個  PathParam  並且將 兩個PathParam 組合起來變成 AB 封裝到 String res 
	 * @throws Exception
	 */
	@Test
	void exercise_GET03() throws Exception {
		JSONObject res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testget03/A/B", "",
				HTTPMethod.GET);
		System.out.println("exercise_GET03"+res.toString());
		assertEquals("AB", res.get("msg"));
	}

	/**
	 * 練習4:
	 *目的:	建立一個 使用POST的 Restful API
	 *說明:	 修改 TrainingProject.src.com.practice_01.rest.Q00_HttpMethod 的   TestPost01 方法
	 * 使其可以被以下方法成功呼叫   
	 * @throws Exception
	 */
	@Test
	void exercise_POST01() throws Exception {
		JSONObject res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testpost01", "",
				HTTPMethod.POST);
		assertEquals("success", res.get("status"));
	}

	/**
	 * 練習5:
	 *目的:	 使用POST 傳遞參數
	 *說明:	 修改 TrainingProject.src.com.practice_01.rest.Q00_HttpMethod 的   TestPost02 方法
	 * 使其可以接收參數Cat  
	 * @throws Exception
	 */
	@Test
	void exercise_POST02() throws Exception {
		JSONObject res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testpost02", "Cat",
				HTTPMethod.POST);
		assertEquals("Cat", res.get("msg"));
	}

	/**
	 * 練習6:
	 *目的:	 使用POST 傳遞參數
	 *說明:	 修改 以下 exercise_POST03  方法   使其可以傳送   物件 ResourceUsageSettingPK 的資料  id= Fly  version =  1;
	 * @throws Exception
	 */
	@Test
	void exercise_POST03() throws Exception {
		//將要傳送的資料利用 JSONObject  packageObj  建立起來 並且將其放到 packageObj 變數
		JSONObject packageObj = new JSONObject();
		/**TODO**/
		packageObj.put("id", "Fly");
		packageObj.put("version", 1);
		//將最後要傳送的資料 放入 topostdata變數進行傳送
		String topostdata = packageObj.toString();
		JSONObject res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testpost03", topostdata,
				HTTPMethod.POST);
		System.out.println("exercise_POST03"+res.toString());
		assertEquals("Fly1", res.get("msg"));
	}

	/**
	 * 練習7:
	 *目的:	 API使用  利用@GET 查詢資料 
	 *說明:	   後端已經準備好一個API 請修改 以下 exercise_DataControl01  方法  
	 *                     利用API [http://localhost:8080/TrainingProject/httpmethod/testdatacontrol01]  
	 *                     傳入QueryParam 參數  key => [machinexname]  value => [machine1]    使其查詢 對應的 MachineX 物件並且回傳此物件訊息
	 * @throws Exception
	 */
	@Test
	void exercise_DataControl01() throws Exception {
		JSONObject res = null;
		res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testdatacontrol01?machinexname=machine1", "",
				HTTPMethod.GET);
		System.out.println("exercise_DataControl01"+res.toString());
		assertEquals("AAT", res.get("msg"));
	}

	/**
	 * 練習8:
	 *目的:	 API使用  利
	 *說明:	   後端已經準備好一個API 請修改 以下 exercise_DataControl02  方法  
	 *                     利用API [http://localhost:80用@POST 查詢資料 80/TrainingProject/httpmethod/testdatacontrol02]  
	 *                     查詢 對應的 ProcessStage 物件    name =>  [STE] sequence => [1] 
	 * @throws Exception
	 */
	@Test
	void exercise_DataControl02() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("name", "STE");
		obj.put("sequence", 1);
		
		JSONObject res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testdatacontrol02",
				obj.toString(), HTTPMethod.POST);
		System.out.println("exercise_DataControl02" + res.toString());
		assertEquals("PS1", res.get("msg"));
	}

	/**
	 * 練習8:
	 *目的:	 API使用  利用@POST 新增資料 
	 *說明:	   後端已經準備好一個API 請修改 以下 exercise_DataControl03  方法  
	 *                     利用API [http://localhost:8080/TrainingProject/httpmethod/testdatacontrol03]  
	 *                     新增  MachineResourceUsageSetting 物件     其 ResourceUsageSettingPK id => [MONK]  version => [5] 
	 * @throws Exception
	 */
	@Test
	void exercise_DataControl03() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("id", "MONK");
		obj.put("version", 5);
		/** TODO **/
		JSONObject res = new JSONObject();
		res  = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testdatacontrol03", obj.toString(),
				HTTPMethod.POST);
		System.out.println("exercise_DataControl03"+res);
		assertEquals(true, Boolean.valueOf(res.get("msg").toString()));
	}

	/**
	 * 練習9:
	 *目的:	 API使用  利用@PUT 更新資料 
	 *說明:	   後端已經準備好一個API 請修改 以下 exercise_DataControl04  方法  
	 *                     利用API [http://localhost:8080/TrainingProject/httpmethod/testdatacontrol04]  
	 *                     更新  Resource 物件     其  id => [resource2]       其  name 變成 => [update成功]
	 * @throws Exception
	 */
	@Test
	void exercise_DataControl04() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("id", "resource2");
		obj.put("name", "update成功");
		JSONObject res = new JSONObject();
		res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testdatacontrol04", obj.toString(),
				HTTPMethod.PUT);
		System.out.println("exercise_DataControl04="+res.toString());
		assertEquals("update成功", res.get("msg"));
	}

	/**
	 * 練習10:
	 *目的:	 API使用  利用@DELETE 刪除資料 
	 *說明:	   後端已經準備好一個API 請修改 以下 exercise_DataControl05  方法  
	 *                     利用API [http://localhost:8080/TrainingProject/httpmethod/testdatacontrol05]  
	 *                     刪除  MachineX 物件     其  name => [machine3]     利用 QueryParam 放置參數進行刪除
	 * @throws Exception
	 */
	@Test
	void exercise_DataControl05() throws Exception {
		JSONObject res = null;
		/**TODO**/
		//	jsoup.request();

		res = jsoup.request("http://localhost:8080/TrainingProject/httpmethod/testdatacontrol05?name=machine3", "",
						HTTPMethod.DELETE);
		assertEquals(true, Boolean.valueOf(res.get("msg").toString()));
		System.out.println("exercise_DataControl05="+res.toString());
	}

}