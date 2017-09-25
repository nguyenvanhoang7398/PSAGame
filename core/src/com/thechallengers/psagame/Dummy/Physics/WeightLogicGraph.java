package com.thechallengers.psagame.Dummy.Physics;

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
        System.out.println("Block " + newBlock.sequenceNumber + " added");
        int addedBlockNumber = newBlock.sequenceNumber;
        blockList.add(newBlock);
        adjList.add(new ArrayList< MyPair<Integer, Float> >());
        if(!contactBlocks.isEmpty()) {
            adjList.set(addedBlockNumber, contactBlocks);
            updateGraph(addedBlockNumber);
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
        Block deletedBlock = blockList.get(deletedBlockNumber);
        Block childBlock;
        float edgeWeight;
        int childBlockNumber;
        for(MyPair<Integer, Float> pair: adjList.get(deletedBlockNumber)) {
            childBlockNumber = pair.getFirst();
            edgeWeight = pair.getSecond();
            childBlock = blockList.get(childBlockNumber);
            childBlock.previousPotential = childBlock.potential;
            childBlock.potential -= (edgeWeight * deletedBlock.potential);
            updateGraph(childBlockNumber);
        }
    }

    public void printPotential() {
        for(Block block: blockList) {
            System.out.println("Block number " + block.sequenceNumber + " with remain capacity " + block.getRemainingCapacity() +
                    " and potential " + block.potential + " and previous potential " + block.previousPotential);
        }
    }
}