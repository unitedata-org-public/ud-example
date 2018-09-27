package org.unitedata.model.example;

import org.unitedata.modelcalc.CalcResolver;
import org.unitedata.modelcalc.CalcResult;
import org.unitedata.modelcalc.DataContext;
import org.unitedata.modelcalc.DataObject;

import java.util.HashMap;
import java.util.Objects;

/**
 * 黑名单计算服务示例代码
 * @author lufeng
 */
public final class ModelExample implements CalcResolver {

    private static final String defaultSchemaName = "blacklist";
    private static final String defaultTableName = "BlackListDict";

    private static final String blackListStatField = "inBlacklist";
    private static final int blackListStatValid = 1;

    @Override
    public CalcResult calc(DataContext context) {

        boolean f
                = context.stream(defaultSchemaName, defaultTableName, Data.class)
                .map(t -> ((Data)t).inBlackList())
                .anyMatch(b -> b);

        return new Result(f);
    }

    private final class Result extends CalcResult {

        // 是否为黑名单 0-不是，1-是
        private final boolean isBlacklist;

        public Result(boolean isBlacklist) {
            this.isBlacklist = isBlacklist;
        }

        public boolean isBlacklist() {
            return isBlacklist;
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
     *{
     *"inBlacklist": true,
     *"risk": "high",
     *"court": {....}
     *}
     */
    public static final class Data extends HashMap<String, Object> implements DataObject {

        boolean inBlackList(){
            Object v = get(blackListStatField);
            if(Objects.isNull(v)){
                return false;
            }

            if(v instanceof Boolean){
                return (boolean)v;
            }

            if(v instanceof Integer){
                return blackListStatValid == (int)v;
            }

            throw new IllegalArgumentException("error format .");
        }
    }
}