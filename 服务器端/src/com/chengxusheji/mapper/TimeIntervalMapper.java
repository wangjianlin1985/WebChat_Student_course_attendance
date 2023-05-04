package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.TimeInterval;

public interface TimeIntervalMapper {
	/*添加时段信息*/
	public void addTimeInterval(TimeInterval timeInterval) throws Exception;

	/*按照查询条件分页查询时段记录*/
	public ArrayList<TimeInterval> queryTimeInterval(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有时段记录*/
	public ArrayList<TimeInterval> queryTimeIntervalList(@Param("where") String where) throws Exception;

	/*按照查询条件的时段记录数*/
	public int queryTimeIntervalCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条时段记录*/
	public TimeInterval getTimeInterval(int intervalId) throws Exception;

	/*更新时段记录*/
	public void updateTimeInterval(TimeInterval timeInterval) throws Exception;

	/*删除时段记录*/
	public void deleteTimeInterval(int intervalId) throws Exception;

}
