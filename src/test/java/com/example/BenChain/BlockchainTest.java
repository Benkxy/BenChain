package com.example.BenChain;

import com.example.BenChain.Objects.Block;
import com.example.BenChain.Objects.Blockchain;
import com.example.BenChain.Service.EncryptionService;
import com.example.BenChain.ServiceImpl.EncryptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlockchainTest {

    private Blockchain blockchain;

    private EncryptionService encryptionService = new EncryptionServiceImpl() ;
    @BeforeEach
    void setUp() {
        blockchain = new Blockchain(encryptionService);
    }

    @Test
    void testCreateGenesisBlock() {
        assertEquals(1, blockchain.getBlockchainSize());
        assertEquals("Genesis Block", blockchain.getLatestBlock().getData());
    }

    @Test
    void testAddBlock() {
        int initialSize = blockchain.getBlockchainSize();

        Block newBlock = new Block(1, blockchain.getLatestBlock().getHash(), "Test Block", encryptionService);
        blockchain.addBlock(newBlock);

        assertEquals(initialSize + 1, blockchain.getBlockchainSize());
        assertEquals(newBlock, blockchain.getLatestBlock());
    }

    @Test
    void testChainValidation(){
        assertTrue(blockchain.isChainValid());
        blockchain.addBlock(new Block(blockchain.getBlockchainSize(),blockchain.getLatestBlock().getHash(),"Adding a new block",encryptionService));
        assertTrue(blockchain.isChainValid());
        blockchain.addBlock(new Block(blockchain.getBlockchainSize(),blockchain.getLatestBlock().getHash(),"Adding a new block 2",encryptionService));
        assertTrue(blockchain.isChainValid());
        Block blockToManipuate = blockchain.getLatestBlock();
        blockToManipuate.setData("New Data");
        assertFalse(blockchain.isChainValid());

    }

    @Test
    void testGenesisBlockValidation() {
        Blockchain genesisBlockchain = new Blockchain(encryptionService);
        assertTrue(blockchain.isChainValid());
        // Manipulate the blockchain to make it invalid
        Block blockToManipulate = genesisBlockchain.getLatestBlock();
        blockToManipulate.setData("Manipulated Data");
        System.out.println(blockToManipulate.getData());
        assertFalse(genesisBlockchain.isChainValid());
    }


}
