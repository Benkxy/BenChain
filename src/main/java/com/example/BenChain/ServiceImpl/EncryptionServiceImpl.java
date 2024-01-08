package com.example.BenChain.ServiceImpl;

import com.example.BenChain.Service.EncryptionService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String calculateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte hashedBytes [] = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b: hashedBytes){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error occurred during hashing!", e);
        }
    }
}
