class Trie {

    TrieNode root;
    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        if(word==null)return ;
        TrieNode node = root;
        int index;
        char[] chs = word.toCharArray();
        for(int i=0;i<chs.length;i++){
            index = chs[i]-'a';
            if(node.next[index]==null)
                node.next[index]=new TrieNode();
            
            node = node.next[index];
            node.path++;
        }
        node.end++;
        
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        if(word==null)return true;
        TrieNode node = root;
        int index;
        char[] chs = word.toCharArray();
        for(int i=0;i<chs.length;i++){
            index = chs[i]-'a';
            if(node.next[index]==null)
                return false;
            else node = node.next[index];
            
        }
        return node.end == 0? false: true;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        if(prefix==null) return true;
        TrieNode node = root;
        int index;
        char[] chs = prefix.toCharArray();
        for(int i=0;i<chs.length;i++){
            index = chs[i]-'a';
            if(node.next[index]==null || node.next[index].path==0)
                return false;
            else node = node.next[index];
        }
        return node.path == 0 ? false:true; 
    }
}

class TrieNode{
    public int end;//以该节点结尾的字符串的数量
    public int path;//划过该节点的字符串数量
    TrieNode[] next;
    public TrieNode(){
        this.end = 0;
        this.path = 0;
        next = new TrieNode[26];
    }
}
/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */