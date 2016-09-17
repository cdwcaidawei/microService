package cn.wpeace.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class RemoteClient {
	public static void main(String[] args) {
		try {
			//建立socket连接
			TSocket tSocket = new TSocket("192.168.1.112",8081);
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
			
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (HelloException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		
	}
}
