package com.example.BenChain;

import com.example.BenChain.Objects.Block;
import com.example.BenChain.Objects.Blockchain;
import com.example.BenChain.Service.EncryptionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan("com.example.BenChain")
public class BenChainApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BenChainApplication.class, args);


		String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}

		EncryptionService encryptionService = context.getBean(EncryptionService.class);
		Blockchain bc = context.getBean(Blockchain.class);

		bc.addBlock(new Block(bc.getBlockchainSize(),"","This is a new block added to the chain", encryptionService));
		bc.addBlock(new Block(bc.getBlockchainSize(),"","This is the second block added to the chain", encryptionService));
		bc.addBlock(new Block(bc.getBlockchainSize(),"","This is the third added to the chain", encryptionService));


		for (Block b : bc) System.out.println(b.toString());

		System.out.println("The block chain validity is : " + bc.isChainValid());
	}

}
