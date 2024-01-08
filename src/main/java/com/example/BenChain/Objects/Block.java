package com.example.BenChain.Objects;

import com.example.BenChain.Service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class Block {
    private int index;
    private String previousHash;
    private String hash;
    private long timeStamp;
    private String data;

    private final EncryptionService encryptionService;
    @Autowired
    public Block(EncryptionService encryptionService){
        this.encryptionService = encryptionService;
    }

    public Block (int index, String previousHash, String data, EncryptionService encryptionService){
        this.index = index;
        this.previousHash=previousHash;
        this.data=data;
        this.encryptionService = encryptionService;
        this.timeStamp= new Date().getTime();

        this.hash= calculateHash();
    }


    public String calculateHash(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.index);
        stringBuilder.append(this.previousHash);
        stringBuilder.append(this.data);
        stringBuilder.append(this.timeStamp);
        String hashedString =  encryptionService.calculateHash(stringBuilder.toString());
        return hashedString;
    }

    public void setData(String data){
       this.data= data;
    }
    public String getData(){
        return data;
    }
    public void setHash(String hash){
        this.hash=hash;
    }
    public String getHash(){
        return this.hash;
    }
    public void setPreviousHash(String previousHash){
        this.previousHash=previousHash;
    }
    public String getPreviousHash(){
        return this.previousHash;
    }

    @Override
    public String toString(){

        return "Block [index=" + index +
                ", previousHash=" + previousHash +
                ", hash=" + hash +
                ", timestamp=" + timeStamp +
                ", data=" + data +
                "]";
    }
}
