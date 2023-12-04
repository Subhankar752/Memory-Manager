package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Process {
    private String name;
    private Map<String, List<MemoryBlock>> allocatedMemory;

    public void setName(String name) {
        this.name = name;
    }

    public void setAllocatedMemory(Map<String, List<MemoryBlock>> allocatedMemory) {
        this.allocatedMemory = allocatedMemory;
    }

    public Process(String name) {
        this.name = name;
        this.allocatedMemory = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, List<MemoryBlock>> getAllocatedMemory() {
        return allocatedMemory;
    }
}
