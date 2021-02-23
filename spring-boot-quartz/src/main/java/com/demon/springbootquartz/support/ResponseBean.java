package com.demon.springbootquartz.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: Demon
 * @date 2021/2/23 11:38
 * @description: 返回的JSON数据结构标准
 */
@Data
@Accessors(chain = true)
public class ResponseBean implements Serializable {

    private boolean result = false;
    private Object data;
    private String msg;
    private String errCode;
    private String errMsg;

    @JsonIgnore
    private boolean dataFormat = false;

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ResponseBeanVo responseBeanVo = new ResponseBeanVo()
                .setResult(this.isResult()).setData(this.getData()).setMsg(this.getMsg())
                .setErrCode(this.getErrCode()).setErrMsg(this.getErrMsg());
        if(this.isDataFormat()){
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBeanVo);
        }else{
            return objectMapper.writer().writeValueAsString(responseBeanVo);
        }
    }
}

@Data
@Accessors(chain = true)
class ResponseBeanVo implements Serializable{
    private boolean result;
    private Object data;
    private String msg;
    private String errCode;
    private String errMsg;

}