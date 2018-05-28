package boss.portal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.util.StringUtils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.region.Region;

public class QCloudUtil {
	private static final String APPID = "";
	private static final String SECRETID = "";
	private static final String SECRETKEY = "";
	private static final String REGION = "";
	private static final String BUCKET_NAME = "";
	private static final String URL = "";

	public static COSClient initCOSClint() {
		// 1 初始化用户身份信息(appid, secretId, secretKey)
		COSCredentials cred = new BasicCOSCredentials(SECRETID, SECRETKEY);
		// 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
		ClientConfig clientConfig = new ClientConfig(new Region(REGION));
		// 3 生成cos客户端
		COSClient cosclient = new COSClient(cred, clientConfig);

		// 关闭客户端
		// cosclient.shutdown();

		return cosclient;
	}

	/**
	 * createBuckt
	 * @param bucketName
	 */
	public static void createBuckt(String bucketName) {
		if (StringUtils.isEmpty(bucketName))
			return;

		// bucket名称, 需包含appid
		CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
		// 设置bucket的权限为PublicRead(公有读私有写), 其他可选有私有读写, 公有读私有写
		createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
	}

	/**
	 * listBuckets
	 */
	public static Map<String,List<String>> listBuckets() {
		COSClient cosclient = initCOSClint();
		
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
		// 设置bucket名称
		listObjectsRequest.setBucketName(BUCKET_NAME);
		// prefix表示列出的object的key以prefix开始
		listObjectsRequest.setPrefix("");
		// 设置最大遍历出多少个对象, 一次listobject最大支持1000
		listObjectsRequest.setMaxKeys(1000);
		// listObjectsRequest.setDelimiter("/");
		ObjectListing objectListing = null;
		Map<String,List<String>> radios = new HashMap();
		List<String> urls = null;
		
		try {
			objectListing = cosclient.listObjects(listObjectsRequest);
		} catch (CosServiceException e) {
			e.printStackTrace();
		} catch (CosClientException e) {
			e.printStackTrace();
		}
		
		// common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
		List<String> commonPrefixs = objectListing.getCommonPrefixes();
		String dirName = "";
		// object summary表示所有列出的object列表
		List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
		for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
			// 文件的路径key
			String key = cosObjectSummary.getKey();
		/*	// 文件的etag
			String etag = cosObjectSummary.getETag();
			// 文件的长度
			long fileSize = cosObjectSummary.getSize();
			// 文件的存储类型
			String storageClasses = cosObjectSummary.getStorageClass();*/
			
			dirName = getDir(key);
			if(null == radios.get(dirName)) {
				urls = new ArrayList();
				urls.add(URL+key);
				radios.put(dirName, urls);
			}else {
				List<String> list = radios.get(dirName);
				list.add(URL+key);
				radios.put(dirName, list);
			}
				
//			System.out.printf("\n\rkey: %s, \retag：%s, \rsize:%d, \rstorage: %s",URL+key, etag, fileSize, storageClasses);
		}
		
		/*for(String key_ : radios.keySet()) {
			System.out.println(key_+"-"+radios.get(key_)+"\r\n");
			System.out.println();
		}*/
		
		shutdown(cosclient);
		
		return radios;
	}
	
	private static String getDir(String str) {
		return str.substring(0,str.indexOf("/"));
	}
	
	public static void shutdown(COSClient cosclient) {
		cosclient.shutdown();
	}

	public static void main(String[] args) {
		listBuckets();
	}

}
