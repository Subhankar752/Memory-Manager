package controller;

import entity.MemoryBlock;
import entity.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemoryManager {
    //sri.kunal@super.money, 9572380219
    private int totalBlocks;
    private List<MemoryBlock> freeMemory;
    private List<Process> processes;

    public MemoryManager(int totalBlocks) {
        this.totalBlocks = totalBlocks;
        this.freeMemory = new ArrayList<>();
        this.processes = new ArrayList<>();

        // Initially, all memory is free
        for (int i = 0; i < totalBlocks; i++) {
            freeMemory.add(new MemoryBlock(i, i));
        }
    }

    public String allocateMemory(String processName, String variable, int blocksRequested, boolean requireContiguous) {
        Process process = getProcess(processName);

        if (process == null) {
            process = new Process(processName);
            processes.add(process);
        }

        int availableBlocks = countFreeBlocks();

        if (blocksRequested > availableBlocks) {
            return "error " + process.getAllocatedMemory().size() + " / " + countFreeBlocks();
        }

        List<MemoryBlock> allocatedBlocks = new ArrayList<>();

        if (requireContiguous) {
            allocatedBlocks = allocateContiguousMemory(blocksRequested);
        } else {
            allocatedBlocks = allocateNonContiguousMemory(blocksRequested);
        }

        if (allocatedBlocks.isEmpty()) {
            return "error " + process.getAllocatedMemory().size() + " / " + countFreeBlocks();
        }

        process.getAllocatedMemory().put(variable, allocatedBlocks);
        return "success " + process.getAllocatedMemory().size() + " / " + countFreeBlocks();
    }

    public String freeMemory(String processName, String variable) {
        Process process = getProcess(processName);

        if (process == null) {
            return "error 0 / " + countFreeBlocks();
        }

        List<MemoryBlock> allocatedBlocks = process.getAllocatedMemory().remove(variable);

        if (allocatedBlocks == null) {
            return "error " + process.getAllocatedMemory().size() + " / " + countFreeBlocks();
        }

        freeMemory.addAll(allocatedBlocks);
        freeMemory.sort((block1, block2) -> Integer.compare(block1.getStart(), block2.getStart()));

        return "success " + process.getAllocatedMemory().size() + " / " + countFreeBlocks();
    }

    public String killProcess(String processName) {
        Process process = getProcess(processName);

        if (process == null) {
            return "error 0 / " + countFreeBlocks();
        }

        for (List<MemoryBlock> allocatedBlocks : process.getAllocatedMemory().values()) {
            freeMemory.addAll(allocatedBlocks);
        }

        freeMemory.sort((block1, block2) -> Integer.compare(block1.getStart(), block2.getStart()));
        processes.remove(process);

        return "success 0 / " + countFreeBlocks();
    }

    public String inspectProcess(String processName) {
        Process process = getProcess(processName);

        if (process == null) {
            return "error 0 / " + countFreeBlocks();
        }

        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, List<MemoryBlock>> entry : process.getAllocatedMemory().entrySet()) {
            result.append(entry.getKey()).append(" ").append(memoryBlockRanges(entry.getValue())).append("\n");
        }

        return result.toString().trim();
    }

    private Process getProcess(String processName) {
        for (Process process : processes) {
            if (process.getName().equals(processName)) {
                return process;
            }
        }
        return null;
    }

    private int countFreeBlocks() {
        return freeMemory.size();
    }

    private List<MemoryBlock> allocateContiguousMemory(int blocksRequested) {
        List<MemoryBlock> result = new ArrayList<>();

        for (int i = 0; i < freeMemory.size(); i++) {
            MemoryBlock block = freeMemory.get(i);
            int blockCount = block.getEnd() - block.getStart() + 1;

            if (blockCount >= blocksRequested) {
                int endIndex = block.getStart() + blocksRequested - 1;
                result.add(new MemoryBlock(block.getStart(), endIndex));

                if (blockCount > blocksRequested) {
                    freeMemory.set(i, new MemoryBlock(endIndex + 1, block.getEnd()));
                } else {
                    freeMemory.remove(i);
                }

                return result;
            }
        }
        return result;
    }

    private List<MemoryBlock> allocateNonContiguousMemory(int blocksRequested) {
        List<MemoryBlock> result = new ArrayList<>();

        int allocatedBlocks = 0;
        for (int i = 0; i < freeMemory.size() && allocatedBlocks < blocksRequested; i++) {
            MemoryBlock block = freeMemory.get(i);
            int blockCount = block.getEnd() - block.getStart() + 1;

            int endIndex = block.getStart() + Math.min(blockCount, blocksRequested) - 1;
            result.add(new MemoryBlock(block.getStart(), endIndex));

            if (blockCount > blocksRequested) {
                freeMemory.set(i, new MemoryBlock(endIndex + 1, block.getEnd()));
            } else {
                freeMemory.remove(i);
            }

            allocatedBlocks += blockCount;
        }

        return result;
    }

    private String memoryBlockRanges(List<MemoryBlock> blocks) {
        StringBuilder result = new StringBuilder();
        for (MemoryBlock block : blocks) {
            result.append(block.getStart()).append("-").append(block.getEnd()).append(" ");
        }
        return result.toString().trim();
    }

}
