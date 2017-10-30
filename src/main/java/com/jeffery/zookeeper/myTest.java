package com.jeffery.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class myTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ZooKeeper zKeeper = new ZooKeeper("localhost:2181,localhost:2182", 600000, new Watcher(){
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println("监控所有被触发的事件：EVENT:"+event.getType());				
			}
		});
		
		System.out.println("**********************************************");
		//查看根结点的子节点
		System.out.println("查看根节点的子节点：ls /  =>"+zKeeper.getChildren("/", true));
		
		//创造一个目录节点
		if (zKeeper.exists("/firstNode0", true) == null) {
			zKeeper.create("/firstNode0", "firstNode0".getBytes(), 
					Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}	
		System.out.println("创建一个目录节点：create /firstNode0 firstNode0");
		System.out.println("查看根节点的子节点：ls /  =>"+zKeeper.getChildren("/", true));
		
		//查看firstNode0节点数据
		System.out.println("查看firstNode0节点数据:get /firstNode  =>" + new String(zKeeper.getData("/firstNode0", false, null)));	
		System.out.println("*******************************************************");
		
		//修改节点数据
		if (zKeeper.exists("/firstNode0", true) != null) {
			zKeeper.setData("/firstNode0", "changeFirstNode0".getBytes(),-1);
		}
		
		System.out.println("修改节点/firstNode0内容：set /firstNode0 changeFirstNode0  =>"
				 + new String(zKeeper.getData("/firstNode0", false, null)));
		
		
		// 创建一个子目录节点
        if (zKeeper.exists("/firstNode0/sub1", true) == null) {
        	zKeeper.create("/firstNode0/sub1", "sub1".getBytes(),
                    Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("创建一个子目录节点:create /firstNode0/sub1 sub1");
            // 查看node节点
            System.out.println("查看firstNode0子节点:ls /firstNode0 => "
                    + zKeeper.getChildren("/firstNode0", false));
        }
        System.out.println("*******************************************************");
		
		//删除节点
        if (zKeeper.exists("/firstNode0/sub1", true) != null) {
			zKeeper.delete("/firstNode0/sub1", -1);
			zKeeper.delete("/firstNode0", -1);
		}
        
      //查看根结点的子节点
      	System.out.println("查看根节点的子节点：ls /  =>"+zKeeper.getChildren("/", true));
		
	}

}
