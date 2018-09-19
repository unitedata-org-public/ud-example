package org.unitedata.model.example;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.unitedata.modelcalc.*;
import org.unitedata.modelcalc.data.DataColumn;
import org.unitedata.modelcalc.data.DataSchema;
import org.unitedata.modelcalc.data.DataRow;
import org.unitedata.modelcalc.data.DataTable;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by yuebing on 2018/9/18.
 */
public class ModelExampleTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void calcTest()
    {
        //这里请加入sdk端传入参数构造，用于将参数在模型计算中应用
        CalcOptions options = new HttpOptions(null, null);


        DataTableContext content = new DataTableContext(options);
        DataSchema owner = content.getSchema("taobao");

        //创建数据结构
        DataColumn[] columns = new DataColumn[3];
        columns[2] = DataColumn.newInstance("INT", "id", 0);
        columns[0] = DataColumn.newInstance("VARCHAR", "name", 1);
        columns[1] = DataColumn.newInstance("INT", "age", 2);

        DataTable table = owner.createTable(0, "tbdata_info", columns);

        //创建数据对象
        ModelExample.Data data1 = new ModelExample.Data();
        data1.age = 21;
        data1.id = 1;
        data1.name = "xy";

        //将行数据加入表
        table.createRow().setValue(data1);

        ModelExample.Data data2 = new ModelExample.Data();
        data2.age = 25;
        data2.id = 2;
        data2.name = "wl";

        table.createRow().setValue(data2);

        //调用模型计算
        ModelExample model = new ModelExample();
        CalcResult result = model.calc(content);

        //输出计算结果
        Arrays.stream(result.getFields()).forEach(f -> {
            try {
                System.out.println(f.getName() + "=" + result.getDataValue(f.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
