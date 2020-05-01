package com.earth.message.service;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class ZooKeeperService {
    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private RedisTemplate redisTemplate;


    public void distributedLock(String name){
        InterProcessSemaphoreMutex mutex = new InterProcessSemaphoreMutex(curatorFramework, "/zktest" + name);
        try {
            mutex.acquire();
            log.info("already got the lock...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void register() throws Exception {
        curatorFramework.create().forPath("/zhuo", "li".getBytes());
        curatorFramework.create().creatingParentsIfNeeded().forPath("");
        curatorFramework.watches();
    }

    public static void main(String[] args) {
    }

    static class MyZkClient{
        private final static String CONNECTION = "192.168.199.175:2181";

        public static ZkClient instance() {
            return new ZkClient(CONNECTION);
        }

        public static void main(String[] args) {
            instance().subscribeDataChanges("/enjoy", new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {
                    System.out.println("the node name is : " + s + "the changed data is: " + o);
                }

                @Override
                public void handleDataDeleted(String s) throws Exception {

                }
            });

            instance().writeData("/enjoy", "node1");
        }
    }

    static class CreateSession{
        private final static String CONNECTION = "192.168.199.175:2181";
        private static CountDownLatch countDownLatch = new CountDownLatch(1);

        public static void main(String[] args) throws IOException, InterruptedException, KeeperException, NoSuchAlgorithmException {
            ZooKeeper zooKeeper = new ZooKeeper(CONNECTION, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    Event.KeeperState state = watchedEvent.getState();
                    if (state == Event.KeeperState.SyncConnected) {
                        countDownLatch.countDown();
                    }
                    if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                        System.out.println("data has changed: " + watchedEvent.getPath());
                    }
                }
            });
            countDownLatch.await();
            System.out.println("zookeeper " + zooKeeper.getState());


            // create a node
            //zooKeeper.create("/enjoy", "enjoy".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            //zooKeeper.addAuthInfo("digest", "root:root".getBytes());

            byte[] data = zooKeeper.getData("/enjoy", true, null);
            String enjoy = new String(data);
            System.out.println("enjoy " + enjoy);

            // delete a node
            //zooKeeper.delete("/enjoy", -1);

            // set watcher
            zooKeeper.getData("/enjoy", true, null);
            zooKeeper.setData("/enjoy", "haha".getBytes(), -1);

            // get the child node
            List<String> children = zooKeeper.getChildren("/", true);
            for (String child : children) {
                System.out.println("child is: " + child);
            }

            ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("root:root")));

            List<ACL> acls = new ArrayList<>();
            acls.add(acl);
        }
    }

}
