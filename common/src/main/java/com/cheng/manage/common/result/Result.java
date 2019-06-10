package com.cheng.manage.common.result;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ClassName:  Result
 * @Description:自定义相应结构
 * @author: chengfei
 * @date:   2018-05-24 10:33
 */
public class Result implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 相应编码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    public static Result build(Integer status, Integer code, String msg, Object data) {
        return new Result(status, code, msg, data);
    }

    public static Result build(ResultEnum resultEnum) {
        return new Result(resultEnum.getStatus(),
        		resultEnum.getCode(),
        		resultEnum.getMsg(), null);
    }

    public static Result succee(Object data) {
        return new Result(data);
    }

    public static Result succee() {
        return new Result(null);
    }

    public Result() {

    }

    public static Result build(Integer status, Integer code, String msg) {
        return new Result(status, code, msg, null);
    }

    public Result(Integer status,Integer code, String msg, Object data) {
		this.status = status;
    	this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Object data) {
        this.status = ResultEnum.SUCCESS.getStatus();
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.data = data;
    }


    /**
     * 将json结果集转化为Result对象
     *
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static Result formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, Result.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), 0,jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static Result format(String json) {
        try {
            return MAPPER.readValue(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static Result formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), 0, jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

	@Override
	public String toString() {
		return "Result [status=" + status + ", code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
