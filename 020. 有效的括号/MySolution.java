class Solution {
    private HashMap<Character,Character> map ;
    public Solution(){
        this.map = new HashMap<>();
        this.map.put(')','(');
        this.map.put('}','{');
        this.map.put(']','[');
    }
    
    public boolean isValid(String s) {
        if(s==null||s.length()==0){
            return true;
        }
        if(s.length()%2!=0)return false;
        Stack<Character> stack = new Stack<>();
        
        int len = s.length();
        for(int i=0; i<len; i++){
            if(this.map.containsKey(s.charAt(i))){
                if(stack.isEmpty()){
                    return false;
                }else if(stack.peek()!=map.get(s.charAt(i))){
                    return false;
                }else{
                    stack.pop();
                }
            }else{
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }
}