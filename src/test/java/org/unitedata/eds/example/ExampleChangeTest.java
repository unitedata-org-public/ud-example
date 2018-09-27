package org.unitedata.eds.example;

import org.junit.Test;
import org.unitedata.eds.domain.Column;
import org.unitedata.eds.domain.OutPutDefinition;
import org.unitedata.eds.domain.Table;
import org.unitedata.eds.enums.ColumnType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sry-cpu on 2018/9/25.
 */
public class ExampleChangeTest {

    @Test
    public void getBlackListTest()
    {
        //1.数据获取
        //合约对应的数据获取方案（包含数据库和数据获取方式）
        OutPutDefinition outPutDefinition = new OutPutDefinition();
        //key-value查询参数对
        HashMap<String,Object> mapParams = new HashMap<>();
        //黑名单数据处理类（属于java自定义类获取方式）
        ExampleChange exampleChange = new ExampleChange();

        System.out.println("黑名单获取数据"+ exampleChange.change(outPutDefinition,mapParams));

        //2.配置数据方案之前的验证操作
        //构造合约对应的元数据
        Table table =  new Table();
        List<Column> outColumns = new ArrayList();
        Column column1 = new Column();
        column1.setName("inBlacklist");
        column1.setColumnType(ColumnType.valueOf(3));
        outColumns.add(column1);
        Column column2 = new Column();
        column2.setName("risk");
        column2.setColumnType(ColumnType.valueOf(1));
        outColumns.add(column2);
        table.setOutColumns(outColumns);

        //0-测试通过，1-测试不通过，请输⼊数据源中存在的参数值，2-测试不通过，请检查数据是否与标准数据字典对应!
        System.out.println("黑名单数据校验结果"+ exampleChange.validate(table,outPutDefinition,mapParams));

    }
}
