package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TimeInterval {
    /*时段id*/
    private Integer intervalId;
    public Integer getIntervalId(){
        return intervalId;
    }
    public void setIntervalId(Integer intervalId){
        this.intervalId = intervalId;
    }

    /*时段名称*/
    @NotEmpty(message="时段名称不能为空")
    private String intervalName;
    public String getIntervalName() {
        return intervalName;
    }
    public void setIntervalName(String intervalName) {
        this.intervalName = intervalName;
    }

    /*商品数量*/
    @NotNull(message="必须输入商品数量")
    private Float product;
    public Float getProduct() {
        return product;
    }
    public void setProduct(Float product) {
        this.product = product;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonTimeInterval=new JSONObject(); 
		jsonTimeInterval.accumulate("intervalId", this.getIntervalId());
		jsonTimeInterval.accumulate("intervalName", this.getIntervalName());
		jsonTimeInterval.accumulate("product", this.getProduct());
		return jsonTimeInterval;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}