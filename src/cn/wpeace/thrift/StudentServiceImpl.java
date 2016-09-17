package cn.wpeace.thrift;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;

import cn.wpeace.thrift.StudentService.Iface;
import cn.wpeace.thrift.StudentService.Processor;

public class StudentServiceImpl implements Iface {// 实现的是StudentService类下面的接口

	@Override
	public List<Student> getAllStudent(Request request) throws HelloException, TException {
		System.out.println(request.getUserName());
		System.out.println(request.getPassword());
		List<Student> students = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Student student = new Student();
			student.setNaem("peace" + i);
			student.setAge(22 + i);
			students.add(student);
		}
		return students;
	}
	public static void main(String[] args) {
		try {
			System.out.println("服务启动");
			// 非阻塞式
			TNonblockingServerSocket serverSocket=new TNonblockingServerSocket(8081);
			// 服务执行控制器,类似于rmi中的bind
			Processor<Iface> processor = new StudentService.Processor<Iface>(new StudentServiceImpl());
			// 为服务器设置对应的IO网络模型
			TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverSocket);
			// 设置控制器
			tArgs.processor(processor);
			//设置传输方式
			// tArgs.transportFactory(new TFramedTransport.Factory());
			// 设置消息封装格式
			tArgs.protocolFactory(new TBinaryProtocol.Factory());//Thrift特有的一种二进制描述格式
			// 启动Thrift服务
			TNonblockingServer server = new TNonblockingServer(tArgs);
			server.serve();//启动后,程序就停在这里了。
			System.out.println("服务结束");
		} catch (TTransportException e) {
			e.printStackTrace();
		}

	}
  @Test
  public  void test(String[] args) {
		try {
			System.out.println("服务启动");
			// 阻塞式同步socket
			TServerSocket serverSocket = new TServerSocket(8081);
			// 服务执行控制器,类似于rmi中的bind
			Processor<Iface> processor = new StudentService.Processor<Iface>(new StudentServiceImpl());
			// 为服务器设置对应的IO网络模型
			Args tArgs = new Args(serverSocket);
			// 设置控制器
			tArgs.processor(processor);
			// 设置消息封装格式
			tArgs.protocolFactory(new TBinaryProtocol.Factory());//Thrift特有的一种二进制描述格式
			// 设置线程池参数
			tArgs.executorService(Executors.newFixedThreadPool(10));//线程池调度器,由于是阻塞模式需要设置线程池。
			// 启动Thrift服务
			TThreadPoolServer server = new TThreadPoolServer(tArgs);
			server.serve();//启动后,程序就停在这里了。
			System.out.println("服务结束");
		} catch (TTransportException e) {
			e.printStackTrace();
		}

	}
}
