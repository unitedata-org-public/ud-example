package org.unitedata.model.example;

import org.unitedata.modelcalc.CalcResolver;
import org.unitedata.modelcalc.CalcResult;
import org.unitedata.modelcalc.DataContext;
import org.unitedata.modelcalc.DataObject;

/**
 * 主要服务
 * @author baimao
 *
 * 一个查询指定类型的人员信息统计查询。统计信息包含人员总数量，平均年龄，最大和最小年龄
 */
public final class ModelExample implements CalcResolver {

    private static final String defaultSchemaName = "taobao";
    private static final String defaultTableName = "tbdata_info";

    @Override
    public CalcResult calc(DataContext context) {
        Integer[] arr
                = context.stream(defaultSchemaName, defaultTableName, Data.class)
                .map(d -> ((Data)d).age)
                .toArray(Integer[]::new);

        int min, max, sum = 0;
        max = min = arr[0];
        for(int v : arr){
            if(min > v){
                min = v;
            }

            if(v > max){
                max = v;
            }

            sum += v;
        }
        return new Result(arr.length, (double)sum/arr.length, max, min);
    }

    private final class Result extends CalcResult {

        // 数量
        private long count;
        // 平均年龄
        private double avgAge;
        // 最大年龄
        private int maxAge;
        // 最小年龄
        private int minAge;

        Result(long count, double avgAge, int maxAge, int minAge) {
            this.count = count;
            this.avgAge = avgAge;
            this.maxAge = maxAge;
            this.minAge = minAge;
        }

        public long getCount() {
            return count;
        }

        public double getAvgAge() {
            return avgAge;
        }

        public int getMaxAge() {
            return maxAge;
        }

        public int getMinAge() {
            return minAge;
        }

        /**
         * 获取匿踪查询结果
         * @return
         */
        @Override
        public String[] resolveAnonymousResult() {
            return super.resolveAnonymousResult();
        }
    }

    /**
     * 数据类型
     * {\"id\":1,\"name\":\"zhangsan\",\"age\":20}
     */
    public static final class Data implements DataObject {

        public long id;
        public String name;
        public int age;
    }
}