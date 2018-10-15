package org.unitedata.eds.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.unitedata.eds.core.change.IChange;
import org.unitedata.eds.domain.Column;
import org.unitedata.eds.domain.DataSet;
import org.unitedata.eds.domain.OutPutDefinition;
import org.unitedata.eds.domain.Table;
import org.unitedata.eds.enums.ValidateType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sry-cpu on 2018/7/19.
 */
public class ExampleChange implements IChange {

    @Override
    public DataSet change(OutPutDefinition outPutDefinition, Map<String, Object> params) {

        String data = getData(params);
        ArrayList<String> dataList = new ArrayList();
        dataList.add(data);
        DataSet dataSet = new DataSet();
        dataSet.setData(dataList);

        return dataSet;
    }

    @Override
    public int validate(Table table, OutPutDefinition outPutDefinition, Map<String, Object> params) {

        //将标准表字段名称与字段类型抽取出
        List<Column> columns = table.getOutColumns();
        Map<String, String> columnsMap = new HashMap();
        for (Column column : columns) {
            columnsMap.put(column.getName(), column.getColumnType().desc());
        }
        String data = getData(params);
        if (data.equals("")||data==null) {
            return ValidateType.valueOf("FAILURE").status();
        }
        JSONObject dataJson = JSON.parseObject(data);
        Map<String,Object> dataMap = dataJson.getInnerMap();
        for(String key:dataMap.keySet()){
            if(key.equals("inBlacklist")||key.equals("risk")){
                if(columnsMap.keySet().contains(key)){
                    continue;
                }
                return ValidateType.valueOf("DISAGREE").status();
            }
        }
        return ValidateType.valueOf("SUCCESS").status();
    }

    /**
     * Json请求
     * @param params
     * @return
     */
    public String getData(Map<String, Object> params){

        String data = "{\n" +
                "     \"inBlacklist\": true,\n" +
                "     \"risk\": \"high\",\n" +
                "     \"court\": {}\n" +
                "     }";
        return data;
    }
}
