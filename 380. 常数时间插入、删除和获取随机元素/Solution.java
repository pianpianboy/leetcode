class RandomizedSet {

    HashMap<Integer,Integer> map1 ;
    HashMap<Integer,Integer> map2 ;
    int size;
    /** Initialize your data structure here. */
    public RandomizedSet() {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        size = 0;
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(map1.containsKey(val)) return false;
        map1.put(val,size);
        map2.put(size++,val);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(!map1.containsKey(val)) return false;
        int removeIndex = map1.get(val);
        int lastIndex = size-1;
        int lastValue = map2.get(lastIndex);
        
        map1.put(lastValue,removeIndex);
        map2.put(removeIndex,lastValue);
        
        map1.remove(val);
        map2.remove(lastIndex);
        
        size = size-1;
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        int random = (int)(Math.random()*size);
        return map2.get(random);
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */