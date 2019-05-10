class Solution {
    public int numIslands(char[][] grid) {
        if(grid==null||grid.length==0){
            return 0;
        }
        int res = 0;
        
        for(int i=0; i <grid.length;i++){
            for(int j=0; j<grid[0].length;j++){
                
                if(grid[i][j]=='1'){
                    grid[i][j] = '2';
                    //if(!((i>0&&grid[i-1][j] ==2)||(j>0&&grid[i][j-1]==2))){
                        
                    //}
                    if((i>0 &&grid[i-1][j]=='0')&&(j>0 && grid[i][j-1]=='0')||(i==0&&j==0)){
                        res=res+1;
                    }else if(i==0&&(j>0 && grid[i][j-1]=='0')){
                        res++;
                    }else if(j==0&&(i>0 && grid[i-1][j]=='0')){
                        res++;
                    }
                }
            }
        }
        return res;
                       
    }
}