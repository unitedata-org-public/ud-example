package org.unitedata.client.example;

import org.junit.Test;
import org.unitedata.data.consumer.DataProducer;
import org.unitedata.data.consumer.DataQueryClient;
import org.unitedata.data.consumer.transaction.TransactionMode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by sry-cpu on 2018/9/26.
 */
public class DataQueryClientTest {

    private static final String charset = "utf-8";
    // eos 账户名称
    private static final String account = "muhe1.5";
    // eos 账户私钥
    private static final String privateKey = "5KWLe3tsEHJ8JQY4h6s7rVy6sa6LZ4K8q4YfUmznTYbxkYyXfYo";
    // eos 访问地址
    private static final String contractUri = "http://eos-api1.ud-eos-api.k2.test.wacai.info/v1";
    // 合约地址
    private static final String contractId = "dr1tpf45wyv4";
    // 交易 id
    private static final String transactionId = "";
    // 交易模型
    private static final TransactionMode mode = TransactionMode.packageTime;
    // 查询参数
    private static final Map<String, Object> queryParameters
            = new HashMap<>();
    // 数据提供方筛选器
    private static final Predicate<DataProducer> producerFilter = null;

    @Test
    public void QueryTest(){

        try {
            final Object data =
                    DataQueryClient.newProtocol(account, privateKey)
                            // [必填] 设置 eos 访问地址
                            .setContractUri(contractUri)
                            // [可选] 设置 http[s] 文本编码器，默认 utf-8
                            .setHttpEncoding(charset)
                            // [可选] 设置 预处理订单数量，默认 2
                            .setMinPreviousTransactionSize(2)
                            // [可选] 设置 当前批次交易的次数，或者有效天数，默认 1
                            .setTransactionTicks(30)
                            // 基于默认的按次计费的模式的交易订单的查询方式，交易订单可选
//                    .query(contractId, transactionId, queryParameters)
                            // 基于指定交易模式的交易订单的查询方式，交易订单可选
                            .query(mode, contractId, transactionId, queryParameters)
                    // 基于指定交易模式，以及主动筛选数据提供方的交易订单的查询方式，交易订单可选
//                  .query(mode, contractId, transactionId, queryParameters, producerFilter)
                    ;
            System.out.println("[data]    --> "+ data);
        }
        catch (Exception cause){
            cause.printStackTrace();
        }
    }
}
