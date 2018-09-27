package org.unitedata.model.example;

import org.junit.Test;
import org.unitedata.modelcalc.CalcOptions;
import org.unitedata.modelcalc.CalcResult;
import org.unitedata.modelcalc.DataTableContext;
import org.unitedata.modelcalc.HttpOptions;
import org.unitedata.modelcalc.data.DataColumn;
import org.unitedata.modelcalc.data.DataSchema;
import org.unitedata.modelcalc.data.DataTable;

import java.util.Arrays;

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
        DataSchema owner = content.getSchema("blacklist");

        //创建数据结构
        DataColumn[] columns = new DataColumn[2];
        columns[1] = DataColumn.newInstance("INT", "inBlacklist", 0);
        columns[0] = DataColumn.newInstance("VARCHAR", "risk", 1);

        DataTable table = owner.createTable(0, "BlackListDict", columns);

        //创建数据对象
        ModelExample.Data data1 = new ModelExample.Data();
        data1.put("inBlacklist",1);
        data1.put("risk","high");

        //将行数据加入表
        table.createRow().setValue(data1);

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
