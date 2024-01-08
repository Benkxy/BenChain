package com.example.BenChain.Objects;

import com.example.BenChain.Service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class Blockchain implements Iterable<Block> {



    private EncryptionService encryptionService;

    private final List<Block> blocks;

    @Autowired
    public Blockchain(EncryptionService encryptionService){
        this.encryptionService=encryptionService;
        blocks = new ArrayList<>();
        createGenesisBlock();
    }




    private void createGenesisBlock(){
        addBlock(new Block(0,"0","Genesis Block",encryptionService));
    }

    private List<Block> getBlocks(){
        return this.blocks;
    }

    public Block getLatestBlock(){
        return this.blocks.get(blocks.size()-1);
    }
        public void addBlock(Block newBlock) {
            if (this.blocks.size() > 0) {
                Block latestBlock = getLatestBlock();
                // Set the previous hash for the new block
                newBlock.setPreviousHash(latestBlock.getHash());
                // Calculate and set the hash for the new block
                String hash = newBlock.calculateHash();
                newBlock.setHash(hash);
            }

            System.out.println("adding block ... : " + newBlock.toString());
            // Add the new block to the blockchain
            this.blocks.add(newBlock);
        }

    public int getBlockchainSize(){
        return blocks.size();
    }
    public boolean isChainValid(){

        if (blocks.size() == 1) {
            Block currentBlock = blocks.get(0);
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current hash fail for block #0 \nCurrent Hash: " + currentBlock.getHash() + "\nCalculated hash: " + currentBlock.calculateHash());
                return false;
            }
            else return true;
        }

        for (int i = 1; i<blocks.size();i++){
            Block previousBlock = blocks.get(i-1);
            Block currentBlock = blocks.get(i);

            if (!currentBlock.getPreviousHash().equals(previousBlock.calculateHash())) {
                System.out.println("Previous hash fail comparison failed for block # " + i);
                return false;
            }
            System.out.println("current Block "+currentBlock.toString());
            // Check if the current block's hash is valid
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current hash fail for block #"+i + "\nCurrent Hash: " + currentBlock.getHash() + "\nCalculated hash: " + currentBlock.calculateHash());
                return false;
            }

    }
        return true;
    }

    @Override
    public Iterator<Block> iterator() {
        return blocks.iterator();
    }

}
