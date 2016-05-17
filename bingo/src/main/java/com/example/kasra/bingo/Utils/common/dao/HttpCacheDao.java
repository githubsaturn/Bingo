package com.example.kasra.bingo.Utils.common.dao;

import com.example.kasra.bingo.Utils.common.entity.HttpResponse;

import java.util.Map;

/**
 * HttpCacheDao
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-11-04
 */
public interface HttpCacheDao
{

	/**
	 * insert HttpResponse
	 *
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long insertHttpResponse(HttpResponse httpResponse);

	/**
	 * get HttpResponse by url
	 */
	public HttpResponse getHttpResponse(String url);

	/**
	 * get HttpResponses by type
	 */
	public Map<String, HttpResponse> getHttpResponsesByType(int type);

	/**
	 * delete all http response
	 */
	public int deleteAllHttpResponse();
}
