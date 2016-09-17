package cn.wpeace.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
/**
 * 接口实现类
 * 需要继承UnicastRemoteObject
 * @author peace
 *
 */
public class StudnetServiceImpl extends UnicastRemoteObject implements StudentService{

	protected StudnetServiceImpl() throws RemoteException {
		super();
	}
	@Override
	public List<Student> getAllStudent() throws RemoteException {
		/**
		 * 这里使用假数据，项目开发时再这里调用dao方法。
		 */
		List<Student> students=new ArrayList<>();
		for(int i=0;i<5;i++){
			Student student=new Student();
			student.setName("peace"+i);
			student.setAge(22+i);
			students.add(student);
		}
		return students;
	}
    public static void main(String[] args) {
    	
		try {
			//需要远程机器访问时，需要设置hostname 
			System.setProperty("java.rmi.server.hostname", "192.168.1.112");
			System.out.println("服务准备启动");
			 //加上此程序，就可以不要在控制台上开启RMI的注册程序(rmiregistry)，1099是RMI服务监视的默认端口
			LocateRegistry.createRegistry(1099);
			StudentService service=new StudnetServiceImpl();
			//通过java 名字服务技术，可以讲具体的RMI Server实现绑定一个访问路径。注册到LocateRegistry中
			Naming.rebind("rmi://192.168.1.112:1099/hello",service);
			System.out.println("服务启动成功");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}  
}
