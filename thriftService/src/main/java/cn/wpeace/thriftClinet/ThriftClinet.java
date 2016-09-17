package cn.wpeace.thriftClinet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.sound.midi.VoiceStatus;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import cn.wpeace.thrift.People;
import cn.wpeace.thrift.PeopleService;
import cn.wpeace.thrift.Request;
import cn.wpeace.thrift.Student;
import cn.wpeace.thrift.StudentService;
import cn.wpeace.thriftService.ServiceSatrt;
import net.sf.json.JSONObject;

public class ThriftClinet implements Watcher{
	static{
		BasicConfigurator.configure();
	}
	private static final Log LOGGER=LogFactory.getLog(ThriftClinet.class);
	private String serverIp;
	private String serverPort;
	private String servername;
	private CountDownLatch connectedSignal=new CountDownLatch(1);//用于建立连接
	private ZooKeeper zk;
	private  void init(String servername) throws IOException, KeeperException, InterruptedException{
		// 连接到zk服务器集群，添加默认的watcher监听
		this.zk = new ZooKeeper("192.168.1.127:2181", 120000, this);
		connectedSignal.await();
		this.servername=servername;
		updateServer();
		ThriftClinet.LOGGER.info("初始化完成");
	}
    /**
     * 从zk上获取Service中的节点数据：包括IP和端口
     * @throws KeeperException
     * @throws InterruptedException
     */
	private void updateServer() throws KeeperException, InterruptedException {
		this.serverIp=null;
		this.serverPort=null;
		/*
		 * 
		 * 判断服务根节点是否存在
		 */
		Stat pathStat = null;
		try {
			pathStat = this.zk.exists("/Service", false);
			// 如果条件成立，说明节点不存在
			// 创建的这个节点是一个“永久状态”的节点
			if (pathStat == null) {
				ThriftClinet.LOGGER.info("客户端创立Service");
				this.zk.create("/Service", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				return;
			}
		} catch (Exception e) {
			ThriftClinet.LOGGER.error(e);
			System.exit(-1);
		}
		// 获取服务列表
		List<String> serviceList = this.zk.getChildren("/Service", false);
		if (serviceList == null || serviceList.isEmpty()) {
			ThriftClinet.LOGGER.info("未发现相关服务，客户端退出");
			return;
		}
		// 查找所需的服务是否存在
		boolean isFound = false;
		byte[] data;// 获取节点数据
		for (String name : serviceList) {
			if (StringUtils.equals(name, this.servername)) {
				isFound = true;
				break;// 找到一个就退出
			}
		}
		// 获得数据
		if (isFound) {
			data = this.zk.getData("/Service/" + this.servername, false, null);
		} else {
			ThriftClinet.LOGGER.info("未发现相关服务，客户端退出");
			return;
		}
		if (data == null || data.length == 0) {
			ThriftClinet.LOGGER.info("没有发现有效数据，客户端退出");
			return;
		}
		JSONObject fromObject = JSONObject.fromObject(new String(data));
		this.serverIp = fromObject.getString("ip");
		this.serverPort = fromObject.getString("port");
	}
	
	@Override
	public void process(WatchedEvent event) {
		//建立连接用
    	if(event.getState()==KeeperState.SyncConnected){
    		connectedSignal.countDown();
    		return;
    	}
		//如果发生 Service下的节点变换，就更新ip和端口
		if (event.getType() == EventType.NodeChildrenChanged   
                 && "/Service".equals(event.getPath())) {  
		   try {
			updateServer();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 }
	}
    public static void main(String[] args) {
		ThriftClinet studentClinet=new ThriftClinet();
		ThriftClinet peopleClinet=new ThriftClinet();
		/**
		 * studnetService 测试
		 */
		try {
			studentClinet.init(ServiceSatrt.serviceNames[0]);
			if(studentClinet.serverIp==null||studentClinet.serverPort==null){
				ThriftClinet.LOGGER.info("没有发现有效数据，客户端退出");
			}
			//如果是非阻塞型  需要使用
			TTransport tSocket = new TFramedTransport(new TSocket(studentClinet.serverIp,
					Integer.parseInt(studentClinet.serverPort),  30000));  
			//设置封装协议
			TBinaryProtocol protocol = new TBinaryProtocol(tSocket);
			//建立调用client
			StudentService.Client client=new StudentService.Client(protocol);
			//设置调用参数:
			Request request=new Request().setUserName("peace").setPassword("123456");
			//准备传输
			tSocket.open();
			//正式调用接口
			List<Student> allStudent = client.getAllStudent(request);
			//请求结束，断开连接
			tSocket.close();
			for(Student student:allStudent)
			{
				System.out.println(student.getNaem()+":"+student.getAge());
			}
		} catch (Exception e) {
			ThriftClinet.LOGGER.info("出现异常，客户端退出");
		}
				
		/**
		 * PeopleService测试
		 */
		try {
			peopleClinet.init(ServiceSatrt.serviceNames[1]);
			if(peopleClinet.serverIp==null||peopleClinet.serverPort==null){
				ThriftClinet.LOGGER.info("没有发现有效数据，客户端退出");
			}
			//如果是非阻塞型  需要使用
			TTransport tSocket = new TFramedTransport(new TSocket(peopleClinet.serverIp,
					Integer.parseInt(peopleClinet.serverPort),  30000));  
			//设置封装协议
			TBinaryProtocol protocol = new TBinaryProtocol(tSocket);
			//建立调用client
			PeopleService.Client client=new PeopleService.Client(protocol);
			//设置调用参数:
			Request request=new Request().setUserName("peace").setPassword("123456");
			//准备传输
			tSocket.open();
			//正式调用接口
			List<People> allPeople = client.getAllPeople(request);
			//请求结束，断开连接
			tSocket.close();
			for(People people:allPeople)
			{
				System.out.println(people.getNaem()+":"+people.getAge()+"性别"+people.getSex());
			}
		} catch (Exception e) {
			ThriftClinet.LOGGER.info("出现异常，客户端退出");
		}
	}
}
