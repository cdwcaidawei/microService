package cn.wpeace.thriftService;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import cn.wpeace.thrift.PeopleService;
import cn.wpeace.thrift.StudentService;
import net.sf.json.JSONObject;
public class ServiceSatrt implements Watcher{
	//初始化log4j
	static{
		BasicConfigurator.configure();
	}
	private static final Log LOGGER=LogFactory.getLog(ServiceSatrt.class);
	private static final Integer[] PORTS={8081,8082};
	public static final String serviceNames[]={"studentService","peopleService"}; 
	private static final String SERVICE_IP="192.168.1.118";
	private CountDownLatch connectedSignal=new CountDownLatch(1);//用于建立连接
	private ZooKeeper zk ;
	/**
	 * thrift服务启动标记
	 */
	private Integer isThriftStart=0;
	
	/**
	 * 启动所有服务
	 */
	private void startServer(){
		ServiceSatrt.LOGGER.info("启动Thrift线程");
		// 创建启动线程：
		StartServerThread studenThread = new StartServerThread(PORTS[0],
				new StudentService.Processor<StudentService.Iface>(new StudentServiceImpl()));
		StartServerThread peopleThread = new StartServerThread(PORTS[1],
				new PeopleService.Processor<PeopleService.Iface>(new PeopleServiceImpl()));
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		pool.submit(studenThread);
		pool.submit(peopleThread);
		//关闭线程池：线程仍然在运行
		pool.shutdown();
	}
	private class StartServerThread implements Runnable{
		private Integer port;
		private TProcessor processor;
		public StartServerThread(Integer port,TProcessor processor) {
			this.port=port;
			this.processor=processor;
		}
		@Override
		public void run() {
			ServiceSatrt.LOGGER.info("thrift服务正在准备启动");
			try {
				// 非阻塞式
				TNonblockingServerSocket serverSocket=new TNonblockingServerSocket(port);
				// 为服务器设置对应的IO网络模型
				TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverSocket);
				// 设置控制器
				tArgs.processor(processor);
				// 设置消息封装格式
				tArgs.protocolFactory(new TBinaryProtocol.Factory());//Thrift特有的一种二进制描述格式
				// 启动Thrift服务
				TNonblockingServer server = new TNonblockingServer(tArgs);
				server.setServerEventHandler(new StartServerEventHander());
				server.serve();//启动后,程序就停在这里了。
			} catch (TTransportException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	private class StartServerEventHander implements TServerEventHandler{
		
		@Override
		public void preServe() {
			synchronized (isThriftStart) {
				isThriftStart++;//当全部服务启动成功才连接zk
				if(isThriftStart==2){
					synchronized (ServiceSatrt.this) {
						ServiceSatrt.LOGGER.info("thrift服务启动完成");
						ServiceSatrt.this.notify();
					}
				}
			}
		}

		@Override
		public ServerContext createContext(TProtocol arg0, TProtocol arg1) {
			return null;
		}

		@Override
		public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
		}

		@Override
		public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
		}

	}
	private void connectZk() throws KeeperException, InterruptedException, IOException{
		// 连接到zk服务器集群，添加默认的watcher监听
		zk= new ZooKeeper("192.168.1.127:2181", 120000, this);
		connectedSignal.await();
		// 创建一个父级节点Service
		Stat pathStat = null;
		try {
			pathStat = zk.exists("/Service", false);
			// 如果条件成立，说明节点不存在（只需要判断一个节点的存在性即可）
			// 创建的这个节点是一个“永久状态”的节点
			if (pathStat == null) {
				zk.create("/Service", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (Exception e) {
			System.exit(-1);
		}

		// 开始添加子级节点，每一个子级节点都表示一个这个服务提供者提供的业务服务
		for (int i = 0; i < 2; i++) {
			JSONObject nodeData = new JSONObject();
			nodeData.put("ip", SERVICE_IP);
			nodeData.put("port", PORTS[i]);
			zk.create("/Service/" + serviceNames[i], nodeData.toString().getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
		}

		// 执行到这里，说明所有的service都启动完成了
		ServiceSatrt.LOGGER.info("===================所有service都启动完成了，主线程开始启动===================");
	}
	@Override
	public void process(WatchedEvent event) {
		//建立连接用
    	if(event.getState()==KeeperState.SyncConnected){
    		connectedSignal.countDown();
    		return;
    	}
		//暂在这里不做处理，正常情况下需要处理。
		
	}
	public static void main(String[] args) {
		//启动服务
		ServiceSatrt serviceSatrt=new ServiceSatrt();
		serviceSatrt.startServer();
		//等待服务启动完成
		synchronized (serviceSatrt) {
			try {
				while (serviceSatrt.isThriftStart<2) {
					serviceSatrt.wait();
				}
			} catch (Exception e) {
				ServiceSatrt.LOGGER.error(e);
				System.out.println(-1);
			}
		}
	    //启动连接
		try {
			serviceSatrt.connectZk();
		} catch (Exception e) {
			ServiceSatrt.LOGGER.error(e);
			System.out.println(-1);
		}	
	}
}

