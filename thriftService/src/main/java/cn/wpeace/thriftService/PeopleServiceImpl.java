package cn.wpeace.thriftService;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import cn.wpeace.thrift.HelloException;
import cn.wpeace.thrift.People;
import cn.wpeace.thrift.PeopleService.Iface;
import cn.wpeace.thrift.Request;
public class PeopleServiceImpl implements Iface{

	@Override
	public List<People> getAllPeople(Request request) throws HelloException, TException {
		System.out.println("调用PeopleService");
		System.out.println(request.getUserName());
		System.out.println(request.getPassword());
		List<People>peoples=new ArrayList<>();
		for(int i=0;i<5;i++)
		{
			People people=new People("wpeace", 22+i, "男");
			peoples.add(people);
		}
		return peoples;
	}

}
