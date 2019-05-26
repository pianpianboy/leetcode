class Solution {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> list = new ArrayList<>();
        for(int i=1;i<10;i++){//1-9之间插入数字
            dfs(i,n,list);
        }
        return list;
    }
    
    public void dfs(int cur,int n,List<Integer> list){
        if(cur>n)//退出条件
            return;
        else{
            list.add(cur);
            for(int i=0;i<10;i++){//0-9 注意此处与前面不同十0-9
                if(10*cur+i>n)//退出条件
                    return;
                else
                    dfs(10*cur+i,n,list);
            }
        }        
    }
}