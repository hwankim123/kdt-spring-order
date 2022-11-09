package org.prgrms.kdt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KdtApplication {

	private static DefaultListableBeanFactory bf;

	@Autowired
	public void setDefaultListableBeanFactory(DefaultListableBeanFactory bf){
		this.bf = bf;
	}

	public static void main(String[] args) {

		SpringApplication.run(KdtApplication.class, args);
		for(String n : bf.getBeanDefinitionNames()){
			System.out.println(n + " \t" + bf.getBean(n).getClass().getName());
		}
	}

}
