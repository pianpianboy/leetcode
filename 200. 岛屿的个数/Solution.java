class Solution {
    public int numIslands(char[][] grid) {
        if(grid==null||grid.length==0){
            return 0;
        }
        int row = grid.length;
        int col = grid[0].length;
        int res = 0;
        for(int i=0;i<row; i++){
            for(int j=0;j<col;j++){                
                if(grid[i][j]=='1'){
                    res++;
                    infect(i,j,grid);
                }                
            }
        }
        return res;                       
    }
    
    public void infect(int i,int j, char[][] nums){
        int row = nums.length;
        int col = nums[0].length;
        if(i<0||j<0||i>=row||j>=col||nums[i][j]!='1')return ;//感染什么时候结束：超过边界，或者遇到为'0'或者为'2'
        //此处特别容易出错，忘记加nums[i][j]!='1'，会有栈溢出的错误       
        nums[i][j] = '2';      
        infect(i-1,j,nums);//上
        infect(i,j-1,nums);//左
        infect(i+1,j,nums);//下
        infect(i,j+1,nums);//右 
    }
}