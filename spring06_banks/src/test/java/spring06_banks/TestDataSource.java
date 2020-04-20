package spring06_banks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.ll.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)   // 请导入    spring-test包
@ContextConfiguration(classes = {AppConfig.class})    //IOC
public class TestDataSource {

	@Autowired
	private DriverManagerDataSource dmd;
	
	@Test   //这是一个测试用例
	public void testDataSource() {
		Assert.notNull(  dmd );
		System.out.println(     dmd );
	}	
}
