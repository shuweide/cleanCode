package idv.code.redis.lettuce;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;

public class RedisClientDemo {

    public static void main(String[] args) throws Exception {

        RedisClusterClient client = null;
        StatefulRedisClusterConnection<String, String> connection = null;

        try {
            client = create();
            connection = client.connect();
//            RedisClusterCommands<String, String> commands = connection.sync();
            for (int i = 0; i < 10000; i++) {
                ping(connection);
            }
            System.out.println("done");

        } finally {
            if (connection != null) connection.close();
            if (client != null) client.shutdown();
        }
    }

    private static void ping(StatefulRedisClusterConnection connection) {
        try {
            connection.async().ping();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RedisClusterClient create() throws Exception {
        RedisClusterClient client = RedisClusterClient.create(createRedisUri());
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enableAllAdaptiveRefreshTriggers()
                .enablePeriodicRefresh()
                .build();
        client.setOptions(ClusterClientOptions.builder().topologyRefreshOptions(topologyRefreshOptions).build());
        return client;
    }

    private static RedisURI createRedisUri() throws Exception {
        RedisURI.Builder builder = RedisURI.builder();
        Map<String, String> envs = System.getenv();
        String hostPorts = envs.get("redis-cluster-hosts");
        if (StringUtils.isNotBlank(hostPorts)) {
            Arrays.stream(hostPorts.split("[;,]"))
                    .map(hostPort -> hostPort.split(":"))
                    .forEach(hostPort -> builder.withHost(hostPort[0]).withPort(Integer.parseInt(hostPort[1])));
        } else {
            throw new Exception("redis-cluster-hosts not found or empty");
        }

        String password = envs.get("redis-cluster-hosts-password");
        if (StringUtils.isNotBlank(password)) {
            builder.withPassword(password);
        }
        return builder.build();
    }

}
