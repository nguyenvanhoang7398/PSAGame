package com.thechallengers.psagame.SinglePlayer.Physics;

import java.util.*;

/**
 * Created by Hieu on 9/24/2017.
 */

public class WeightLogicGraph {
    ArrayList<Block> blockList;
    ArrayList< ArrayList< MyPair<Integer, Float> > > adjList;

    WeightLogicGraph() {
        adjList = new ArrayList< ArrayList< MyPair<Integer, Float> > >();
        blockList = new ArrayList<Block>();
    }

    public void addBlock(Block newBlock, ArrayList< MyPair<Integer, Float> > contactBlocks) {
        System.out.println("Block " + newBlock.sequenceNumber + " added with weight " + newBlock.weight + " potential " + newBlock.potential
                +" decrease " + newBlock.decreaseTimeRate);
        int addedBlockNumber = newBlock.sequenceNumber;
        if(addedBlockNumber >= blockList.size()) {
            blockList.add(newBlock);
            adjList.add(new ArrayList<MyPair<Integer, Float>>());
        }

        int childBlockNumber; Block childBlock;

        for(MyPair<Integer, Float> pair: contactBlocks) {
            childBlockNumber = pair.getFirst();
            childBlock = blockList.get(childBlockNumber);
            childBlock.upperCount++;
        }

        if(!contactBlocks.isEmpty()) {
            adjList.set(addedBlockNumber, contactBlocks);
            updateGraph(addedBlockNumber);
        }
        for(Block a: blockList) {
            System.out.println("Block " + a.sequenceNumber + " potential " + a.potential + " previous potential " +
                    a.previousPotential + " decrease " + a.decreaseTimeRate);
        }
    }

    public void updateGraph(int addedBlockNumber) {
        Block addedBlock = blockList.get(addedBlockNumber);
        Block nextBlock;
        int nextBlockNumber;
        float edgeWeight;
        for(MyPair<Integer, Float> pair: adjList.get(addedBlockNumber))  {
            nextBlockNumber = pair.getFirst();
            nextBlock = blockList.get(nextBlockNumber);
            edgeWeight = pair.getSecond();
            nextBlock.previousPotential = nextBlock.potential;
            nextBlock.potential += (edgeWeight * (addedBlock.potential - addedBlock.previousPotential));
            updateGraph(nextBlockNumber);
        }
    }

    public void deleteNode(int deletedBlockNumber) {
        System.out.println("Block " + deletedBlockNumber + " is deleted");
        Block deletedBlock = blockList.get(deletedBlockNumber);
        Block childBlock;
        float edgeWeight;
        int childBlockNumber;
        for(MyPair<Integer, Float> pair: adjList.get(deletedBlockNumber)) {
            childBlockNumber = pair.getFirst();
            edgeWeight = pair.getSecond();
            childBlock = blockList.get(childBlockNumber);
            childBlock.upperCount--;
            childBlock.previousPotential = childBlock.potential;
            childBlock.potential -= (edgeWeight * deletedBlock.potential);
            updateGraph(childBlockNumber);
        }
        for(Block a: blockList) {
            System.out.println("Block " + a.sequenceNumber + " potential " + a.potential + " previous potential " +
                    a.previousPotential + " decrease " + a.decreaseTimeRate);
        }
    }

    public void printPotential() {
        for(Block block: blockList) {
            System.out.println("Block number " + block.sequenceNumber + " with remain capacity " + block.getRemainingCapacity() +
                    " and potential " + block.potential + " and previous potential " + block.previousPotential);
        }
    }
}
