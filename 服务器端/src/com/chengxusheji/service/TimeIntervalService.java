package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.TimeInterval;

import com.chengxusheji.mapper.TimeIntervalMapper;
@Service
public class TimeIntervalService {

	@Resource TimeIntervalMapper timeIntervalMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加时段记录*/
    public void addTimeInterval(TimeInterval timeInterval) throws Exception {
    	timeIntervalMapper.addTimeInterval(timeInterval);
    }

    /*按照查询条件分页查询时段记录*/
    public ArrayList<TimeInterval> queryTimeInterval(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return timeIntervalMapper.queryTimeInterval(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<TimeInterval> queryTimeInterval() throws Exception  { 
     	String where = "where 1=1";
    	return timeIntervalMapper.queryTimeIntervalList(where);
    }

    /*查询所有时段记录*/
    public ArrayList<TimeInterval> queryAllTimeInterval()  throws Exception {
        return timeIntervalMapper.queryTimeIntervalList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = timeIntervalMapper.queryTimeIntervalCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取时段记录*/
    public TimeInterval getTimeInterval(int intervalId) throws Exception  {
        TimeInterval timeInterval = timeIntervalMapper.getTimeInterval(intervalId);
        return timeInterval;
    }

    /*更新时段记录*/
    public void updateTimeInterval(TimeInterval timeInterval) throws Exception {
        timeIntervalMapper.updateTimeInterval(timeInterval);
    }

    /*删除一条时段记录*/
    public void deleteTimeInterval (int intervalId) throws Exception {
        timeIntervalMapper.deleteTimeInterval(intervalId);
    }

    /*删除多条时段信息*/
    public int deleteTimeIntervals (String intervalIds) throws Exception {
    	String _intervalIds[] = intervalIds.split(",");
    	for(String _intervalId: _intervalIds) {
    		timeIntervalMapper.deleteTimeInterval(Integer.parseInt(_intervalId));
    	}
    	return _intervalIds.length;
    }
}
